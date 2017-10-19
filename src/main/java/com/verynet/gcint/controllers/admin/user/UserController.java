package com.verynet.gcint.controllers.admin.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.EntityData;
import com.verynet.gcint.api.model.Ocupation;
import com.verynet.gcint.api.model.Role;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.JavaUtil;
import com.verynet.gcint.api.util.enums.Roles;
import com.verynet.gcint.controllers.GeneralController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 22/08/2016.
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController extends GeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public String getUserManagement(ModelMap map) {
        User loggedUser = getUserLogged();
        if (loggedUser.hasRole(Roles.ROLE_SUPER_ADMIN.toString())) {
            map.put("entities", Context.getEntityService().getAllEntitiesData());
        }
        return "/admin/user/all";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getUserData(@RequestParam(value = "username") String userName) {
        Map<String, Object> result = new HashMap<>();
        User user = Context.getUserService().getUser(userName);
        if (user == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", user.getFirstName());
        result.put("lastName", user.getLastName());
        result.put("email", user.getEmail());
        result.put("identification", user.getIdentification());
        int[] roleList = new int[user.getRoles().size()];
        for (int i = 0; i < roleList.length; i++) {
            roleList[i] = user.getRoles().get(i).getId();
        }
        result.put("roleList", roleList);
        result.put("ocupation", user.getOcupation().getId());
        result.put("pathPhoto", user.getPathPhoto());
        result.put("entity", user.getEntity().getId());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditUser(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "username") String userName,
                         @RequestParam(value = "password") String password,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "lastName") String lastName,
                         @RequestParam(value = "identification", required = false) String identification,
                         @RequestParam(value = "roles") String rolesJson,
                         @RequestParam(value = "ocupation") Integer ocupationId,
                         @RequestParam(value = "pathPhoto", required = false) String pathPhoto,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "entity", required = false) Integer entityId,
                         @RequestParam(value = "action") String action,
                         ModelMap map) throws Exception {
        Map<String, Object> result = new HashMap<>();
        User user = null;
        if (action.equals("add")) {
            user = Context.getUserService().getUser(userName);
            if (user == null) {
                user = new User();
            } else {
                result.put("success", false);
                result.put("message", "EL usuario ya existe");
                return result;
            }
        } else {
            User anotherUser = Context.getUserService().getUser(userName);
            user = Context.getUserService().getUser(id);
            if (anotherUser != null && !anotherUser.getId().equals(id)) {
                result.put("success", false);
                result.put("message", "EL usuario ya existe");
                return result;
            }
        }
        List<Role> rolesofUser = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject roles = (JsonObject) jsonParser.parse(rolesJson);
        List<Role> listAllRoles = (List<Role>) map.get("rolesUsers");
        user.setUserName(userName);
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setIdentification(identification);
        user.setOcupation(Context.getNomenclatureService().getOcupation(ocupationId));
        EntityData entityData;
        if (entityId == null) {
            entityData = getUserLogged().getEntity();
        } else {
            entityData = Context.getEntityService().getEntityData(entityId);
        }
        user.setEntity(entityData);
        if (action.equals("add")) {
            user.setEnabled(Boolean.TRUE);
            user.setAccountNonLocked(Boolean.TRUE);
        }
        if (StringUtils.isNotBlank(pathPhoto)) {
            user.setPathPhoto(pathPhoto);
        } else {
            user.setPathPhoto(null);
        }
        user.setEmail(email);

        if (StringUtils.isNotBlank(password)) {
            user.setPassword(JavaUtil.md5(password));
        }
        List<String> rolesName = new ArrayList<>();
        for (int i = 0; i < listAllRoles.size(); i++) {
            String key = "checkbox_roles";
            if (!action.equals("add")) {
                key = "edit_checkbox_roles";
            }
            JsonElement value = roles.get(key + listAllRoles.get(i).getId());
            if (value != null) {
                rolesofUser.add(listAllRoles.get(i));
                rolesName.add(listAllRoles.get(i).getName());
            }
        }
        user.setRoles(rolesofUser);
        try {
            user = Context.getUserService().saveUser(user);
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing user: %s", e.getMessage()));
            if (e instanceof ConstraintViolationException) {
                user = Context.getUserService().saveUser(user);
            }
        }
        result.put("success", true);
        result.put("id", user.getId());
        result.put("rolesName", rolesName);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteUser(@RequestParam(value = "username") String userName) {
        boolean value = false;
        try {
            value = Context.getUserService().deleteUser(userName);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting user(%s): %s", userName, e.getMessage()));
        }
        return value;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/status")
    public
    @ResponseBody
    boolean setUserStatus(@RequestParam(value = "username") String userName,
                          @RequestParam(value = "status") boolean status) {
        User user = Context.getUserService().getUser(userName);
        user.setEnabled(status);
        Context.getUserService().saveUser(user);
        return true;
    }

    @ModelAttribute("rolesUsers")
    public List<Role> getRoles() {
        return Context.getRoleService().getAllRoles(Roles.ROLE_SUPER_ADMIN.toString());
    }

    @ModelAttribute("users")
    public List<User> getUsers() {
        List<User> users = null;
        User loggedUser = getUserLogged();
        if (loggedUser != null) {
            if (loggedUser.hasRole(Roles.ROLE_SUPER_ADMIN.toString())) {
                users = Context.getUserService().getAllUsers();
            } else {
                users = Context.getUserService().getAllUsers(loggedUser.getEntity().getId());
            }
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getUserName().equals(loggedUser.getUserName()) || user.hasRole(Roles.ROLE_SUPER_ADMIN.toString())) {
                    users.remove(i);
                }
            }
        }
        return users;
    }

    @ModelAttribute("ocupations")
    public List<Ocupation> getOcupations() {
        return Context.getNomenclatureService().getAllOcupations();
    }

}
