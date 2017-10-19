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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 07/09/2016.
 */
@Controller
@RequestMapping(value = "/admin/process/sub-process")
public class SubProcessController extends GeneralController {

    @RequestMapping(method = RequestMethod.GET, value = "/sub-processes")
    public String getSubProcessesFromProcess(@RequestParam(value = "id") Integer id, ModelMap map) {
        List<SubProcess> subProcesses = Context.getProcessService().getAllHeavySubProcesses(id);
        Process process = Context.getProcessService().getHeavyProcess(id);
        map.put("subProcesses", subProcesses);
        map.put("process", process);
        return "/admin/process/subProcesses";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getSubProccessData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        SubProcess subProcess = Context.getProcessService().getSubProcess(id);
        if (subProcess == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", subProcess.getName());
        if (subProcess.getResponsible() != null) {
            result.put("responsible", subProcess.getResponsible().getId());
        }
        result.put("description", subProcess.getDescription());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditProcess(@RequestParam(value = "id", required = false) Integer id,
                            @RequestParam(value = "idProcess", required = false) Integer idProcess,
                            @RequestParam(value = "name") String name,
                            @RequestParam(value = "responsible") Integer responsibleId,
                            @RequestParam(value = "description") String description,
                            @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        SubProcess subProcess = null;
        Process parent = Context.getProcessService().getHeavyProcess(idProcess);
        if (action.equals("add")) {
            subProcess = new SubProcess();
            subProcess.setParent(parent);
        } else {
            subProcess = Context.getProcessService().getHeavySubProcess(id);
        }
        subProcess.setName(name);
        subProcess.setResponsible(Context.getUserService().getUser(responsibleId));
        subProcess.setDescription(description);
        subProcess.setEntity(parent.getEntity());
        subProcess = Context.getProcessService().saveSubProcess(subProcess);
        if (!action.equals("add")) {
            int pos = subProcess.getMembers().indexOf(subProcess.getResponsible());
            if (pos != -1) {
                subProcess.getMembers().remove(pos);
                subProcess = Context.getProcessService().saveSubProcess(subProcess);
            }
        }
        List<Integer> membersId = new ArrayList<>();
        List<String> membersName = new ArrayList<>();
        List<User> members = parent.getMembers();
        for (User member : members) {
            if (!member.getId().equals(subProcess.getResponsible().getId())&&
                    !member.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
                membersId.add(member.getId());
                membersName.add(member.getFirstName() + " " + member.getLastName());
            }
        }
        List<Integer> selectedMemberId = new ArrayList<>();
        if (subProcess.getMembers() != null) {
            for (User member : subProcess.getMembers()) {
                selectedMemberId.add(member.getId());
            }
        }

        result.put("membersId", membersId);
        result.put("membersName", membersName);
        result.put("selectedMemberId", selectedMemberId);
        result.put("success", true);
        result.put("id", subProcess.getId());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteSubProccess(@RequestParam(value = "id") Integer id) {
        return Context.getProcessService().deleteSubProcess(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/members")
    public
    @ResponseBody
    Map setMembers(@RequestParam(value = "id") Integer id,
                   @RequestParam(value = "idProcess") Integer idProcess,
                   @RequestParam(value = "members") String membersJson) {
        Map<String, Object> result = new HashMap<>();
        List<User> members = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject membersJsonObject = (JsonObject) jsonParser.parse(membersJson);
        Process process = Context.getProcessService().getHeavyProcess(idProcess);
        List<User> memberUsers = process.getMembers();
        for (User user : memberUsers) {
            JsonElement value = membersJsonObject.get("value " + user.getId());
            if (value != null) {
                members.add(user);
            }
        }
        SubProcess subProcess = Context.getProcessService().getSubProcess(id);
        subProcess.setMembers(members);
        subProcess = Context.getProcessService().saveSubProcess(subProcess);
        if (subProcess == null) {
            result.put("success", false);
            result.put("message-error", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }
}
