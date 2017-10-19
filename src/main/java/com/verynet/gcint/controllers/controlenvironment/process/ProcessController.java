package com.verynet.gcint.controllers.controlenvironment.process;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Objective;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.api.model.SubProcess;
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
 * Created by day on 19/09/2016.
 */
@Controller
@RequestMapping(value = "/control-environment/process")
public class ProcessController extends CommonGeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/objectives")
    public String getObjectives(ModelMap map, HttpSession session) {
        map.put("objectProcessSelected", getObjectProcessSelected(session));
        return "controlenvironment/process/objectives";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/objective/data")
    public
    @ResponseBody
    Map getObjectiveData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Objective objective = Context.getProcessService().getObjective(id);
        if (objective == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", objective.getObjective());
        result.put("description", objective.getDescription());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/data/save")
    public
    @ResponseBody
    Map saveObjective(@RequestParam(value = "vision") String vision,
                      @RequestParam(value = "mission") String mission,
                      HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            Process process = Context.getProcessService().getProcess(getProcessSelected(session));
            process.setVision(vision);
            process.setMission(mission);
            Context.getProcessService().saveProcess(process);
        } catch (Exception e) {
            logger.error(String.format("Error saving general process data: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/objective/save")
    public
    @ResponseBody
    Map createOrEditObjective(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "name") String name,
                              @RequestParam(value = "description") String description,
                              @RequestParam(value = "action") String action,
                              HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Objective objective = null;
        if (action.equals("add")) {
            objective = new Objective();
        } else {
            objective = Context.getProcessService().getObjective(id);
        }
        objective.setObjective(name);
        objective.setDescription(description);
        try {
            objective = Context.getProcessService().saveObjective(objective);
            if (action.equals("add")) {
                User userLogged = getUserLogged();
                if (userLogged != null) {
                    if (userLogged.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
                        SubProcess subProcess = Context.getProcessService().getSubProcess(getProcessSelected(session));
                        subProcess.getObjectives().add(objective);
                        subProcess = Context.getProcessService().saveSubProcess(subProcess);
                        objective.setProcess(subProcess);
                        Context.getProcessService().saveObjective(objective);
                    } else {
                        Process process = Context.getProcessService().getProcess(getProcessSelected(session));
                        process.getObjectives().add(objective);
                        Context.getProcessService().saveProcess(process);
                        objective.setProcess(process);
                        Context.getProcessService().saveObjective(objective);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing process objective: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("id", objective.getId());
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/objective/delete")
    public
    @ResponseBody
    boolean deleteObjective(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getProcessService().deleteObjective(id);
        } catch (Exception e) {
            logger.error(String.format("Error deleting process objective(%s): %s", id, e.getMessage()));
            return false;
        }
    }

}
