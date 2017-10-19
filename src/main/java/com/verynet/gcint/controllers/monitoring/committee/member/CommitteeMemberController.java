package com.verynet.gcint.controllers.monitoring.committee.member;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.enums.Roles;
import com.verynet.gcint.controllers.CommonGeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 01/10/2016.
 */
@Controller
@RequestMapping("/monitoring/committee/member")
public class CommitteeMemberController extends CommonGeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public String getMembers(ModelMap model, HttpSession session) {
        model.put("members", Context.getUserService().getUsersWithRoleValues(getEntityData(session).getId(), Roles.ROLE_COMMITTEE_MEMBER.toString(), Roles.ROLE_SECRETARY_COMMITTEE.toString()));
        model.put("users", Context.getUserService().getUsersWithOutRoleValues(getEntityData(session).getId(), Roles.ROLE_COMMITTEE_MEMBER.toString(), Roles.ROLE_SECRETARY_COMMITTEE.toString()));
        return "monitoring/committee/member/all";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map setSecretary(@RequestParam(value = "id") Integer id,
                     @RequestParam(value = "secretary") Boolean secretary) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = Context.getUserService().getUser(id);
            if (secretary) {
                user.getRoles().add(Context.getRoleService().getRoleByValue(Roles.ROLE_SECRETARY_COMMITTEE.toString()));
            } else {
                user.getRoles().add(Context.getRoleService().getRoleByValue(Roles.ROLE_COMMITTEE_MEMBER.toString()));
            }
            user = Context.getUserService().saveUser(user);
            result.put("id", user.getId());
            result.put("fullName", String.format("%s %s", user.getFirstName(), user.getLastName()));
            result.put("email", user.getEmail());
            result.put("ocupation", user.getOcupation() != null ? user.getOcupation().getName() : "");
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error assigning secretary role to user(%s): %s", id, e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteMember(@RequestParam(value = "id") Integer id) {
        try {
            User user = Context.getUserService().getUser(id);
            user.deleteRole(Roles.ROLE_SECRETARY_COMMITTEE.toString());
            user.deleteRole(Roles.ROLE_COMMITTEE_MEMBER.toString());
            Context.getUserService().saveUser(user);
            return true;
        } catch (Exception e) {
            logger.warn(String.format("Error deleting control committee member(%s): %s", id, e.getMessage()));
            return false;
        }
    }
}
