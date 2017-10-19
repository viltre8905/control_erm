package com.verynet.gcint.controllers.admin.process;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.ActivityProcess;
import com.verynet.gcint.api.model.GeneralProcess;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.controllers.GeneralController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 05/09/2017.
 */
@Controller
@RequestMapping(value = "/admin/process/activity")
public class ActivityProcessController extends GeneralController {
    @RequestMapping(method = RequestMethod.GET, value = "/activities")
    public String getActivityProcessesFromProcess(@RequestParam(value = "id") Integer id, @RequestParam(value = "processId", required = false) Integer processId, ModelMap map) {
        List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(id);
        GeneralProcess process = Context.getProcessService().getHeavyProcess(id);
        map.put("isProcess", true);
        if (process == null) {
            process = Context.getProcessService().getHeavySubProcess(id);
            map.put("isProcess", false);
            map.put("subProcessId", processId);
        }
        map.put("activities", activityProcessList);
        map.put("process", process);
        return "/admin/process/activities";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getAtivityProccessData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        ActivityProcess activityProcess = Context.getActivityProcessService().getActivityProcess(id);
        if (activityProcess == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", activityProcess.getName());
        if (activityProcess.getResponsible() != null) {
            result.put("responsible", activityProcess.getResponsible().getId());
        }
        result.put("description", activityProcess.getDescription());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditActivityProcess(@RequestParam(value = "id", required = false) Integer id,
                                    @RequestParam(value = "idProcess", required = false) Integer idProcess,
                                    @RequestParam(value = "name") String name,
                                    @RequestParam(value = "responsible") Integer responsibleId,
                                    @RequestParam(value = "description") String description,
                                    @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        ActivityProcess activityProcess = null;
        GeneralProcess parent = Context.getProcessService().getGeneralProcess(idProcess);
        if (parent == null) {
            parent = Context.getProcessService().getSubProcess(idProcess);
        }
        if (action.equals("add")) {
            activityProcess = new ActivityProcess();
            activityProcess.setParent(parent);
        } else {
            activityProcess = Context.getActivityProcessService().getActivityProcess(id);
        }
        activityProcess.setName(name);
        activityProcess.setResponsible(Context.getUserService().getUser(responsibleId));
        activityProcess.setDescription(description);
        activityProcess.setEntity(parent.getEntity());
        activityProcess = Context.getActivityProcessService().saveActivityProcess(activityProcess);

        result.put("success", true);
        result.put("id", activityProcess.getId());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteActivityProccess(@RequestParam(value = "id") Integer id) {
        return Context.getActivityProcessService().deleteActivityProcess(id);
    }
}
