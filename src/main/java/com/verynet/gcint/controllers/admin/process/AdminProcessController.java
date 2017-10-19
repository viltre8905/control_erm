package com.verynet.gcint.controllers.admin.process;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.api.model.SubProcess;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.enums.Roles;
import com.verynet.gcint.controllers.GeneralController;
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
 * Created by day on 05/09/2016.
 */
@Controller
@RequestMapping(value = "/admin/process")
public class AdminProcessController extends GeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/processes")
    public String getAllProcess() {
        return "/admin/process/processes";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getProccessData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Process process = Context.getProcessService().getProcess(id);
        if (process == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", process.getName());
        result.put("responsible", process.getResponsible().getId());
        result.put("description", process.getDescription());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditProcess(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "name") String name,
                            @RequestParam(value = "responsible") Integer responsibleId,
                            @RequestParam(value = "description") String description,
                            @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        Process process = null;
        if (action.equals("add")) {
            process = new Process();
        } else {
            process = Context.getProcessService().getProcess(id);
        }
        User loggedUser = getUserLogged();
        process.setName(name);
        process.setResponsible(Context.getUserService().getUser(responsibleId));
        process.setDescription(description);
        process.setEntity(loggedUser.getEntity());
        process = Context.getProcessService().saveProcess(process);
        result.put("success", true);
        result.put("id", process.getId());
        if (action.equals("add")) {
            List<Integer> membersId = new ArrayList<>();
            List<String> membersName = new ArrayList<>();
            List<User> members = getMemberUsers();
            for (User member : members) {
                membersId.add(member.getId());
                membersName.add(member.getFirstName() + " " + member.getLastName());
            }
            result.put("membersId", membersId);
            result.put("membersName", membersName);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteProccess(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getProcessService().deleteProcess(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting proccess with id(%s): %s", id, e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/members")
    public
    @ResponseBody
    Map setMembers(@RequestParam(value = "id") Integer id,
                   @RequestParam(value = "members") String membersJson,
                   ModelMap map) {
        Map<String, Object> result = new HashMap<>();
        List<User> members = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject membersJsonObject = (JsonObject) jsonParser.parse(membersJson);
        List<User> memberUsers = (List<User>) map.get("memberUsers");
        for (User user : memberUsers) {
            JsonElement value = membersJsonObject.get("value " + user.getId());
            if (value != null) {
                members.add(user);
            }
        }
        Process process = Context.getProcessService().getHeavyProcess(id);
        process.setMembers(members);
        process = Context.getProcessService().saveProcess(process);
        if (process == null) {
            result.put("success", false);
            result.put("message-error", "Ha ocurrido un error inesperado");
            return result;
        }
        List<SubProcess> subProcesses = Context.getProcessService().getAllHeavySubProcesses(process.getId());
        boolean delete = false;
        for (SubProcess subProcess : subProcesses) {
            List<User> subProcessMembers = subProcess.getMembers();
            User subProcessResponsible = subProcess.getResponsible();
            for (int i = 0; i < subProcessMembers.size(); i++) {
                User subProcessMember = subProcessMembers.get(i);
                if (!process.getMembers().contains(subProcessMember)) {
                    subProcessMembers.remove(i--);
                    delete = true;
                }
            }
            if (subProcessResponsible != null && !process.getMembers().contains(subProcessResponsible)) {
                subProcess.setResponsible(null);
                delete = true;
            }
            Context.getProcessService().saveSubProcess(subProcess);
        }
        if (delete) {
            result.put("success", false);
            result.put("message-warning", "Los miembros del proceso han sido eliminados automaticamente en los sub-procesos correspondientes");
        }
        result.put("success", true);
        return result;
    }


    @ModelAttribute(value = "processes")
    public List<Process> getProcess() {
        User loggedUser = getUserLogged();
        if (loggedUser != null) {
            return Context.getProcessService().getAllHeavyProcess(loggedUser.getEntity().getId());
        }
        return null;
    }

    @ModelAttribute("responsibleUsers")
    public List<User> getResponsibleUsers() {
        User loggedUser = getUserLogged();
        if (loggedUser != null) {
            return Context.getUserService().getUsersByRoleValue(loggedUser.getEntity().getId(), Roles.ROLE_PROCESS_SUPERVISORY.toString());
        }
        return null;
    }

    @ModelAttribute("memberUsers")
    public List<User> getMemberUsers() {
        User loggedUser = getUserLogged();
        List<User> result = null;
        if (loggedUser != null) {
            result = Context.getUserService().getUsersWithRoleValues(loggedUser.getEntity().getId(), Roles.ROLE_EXECUTER.toString(),Roles.ROLE_SUBPROCESS_SUPERVISORY.toString(),Roles.ROLE_ACTIVITY_RESPONSIBLE.toString());
        }

        return result;
    }
}
