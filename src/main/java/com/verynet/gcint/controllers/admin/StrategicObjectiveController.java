package com.verynet.gcint.controllers.admin;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.StrategicObjective;
import com.verynet.gcint.controllers.GeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 24/02/2017.
 */
@Controller
@RequestMapping(value = "/admin/entity/strategicObjective")
public class StrategicObjectiveController extends GeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public String getStrategicObjectives(@RequestParam(value = "entityId") Integer entityId, ModelMap map) {
        map.put("entityId", entityId);
        map.put("strategicObjectives", Context.getStrategicObjectiveService().getAllStrategicObjective(entityId));
        return "admin/entity/strategicObjectives";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getObjectiveData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        StrategicObjective strategicObjective = Context.getStrategicObjectiveService().getStrategicObjective(id);
        if (strategicObjective == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        result.put("title", strategicObjective.getTitle());
        result.put("description", strategicObjective.getObjective());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditObjective(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "entityId", required = false) Integer entityId,
                              @RequestParam(value = "title") String title,
                              @RequestParam(value = "description") String description,
                              @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        StrategicObjective strategicObjective = null;
        if (action.equals("add")) {
            strategicObjective = new StrategicObjective();
        } else {
            strategicObjective = Context.getStrategicObjectiveService().getStrategicObjective(id);
        }
        strategicObjective.setTitle(title);
        strategicObjective.setObjective(description);
        try {
            if (action.equals("add")) {
                strategicObjective.setEntity(Context.getEntityService().getLightWeightEntityData(entityId));
            }
            strategicObjective = Context.getStrategicObjectiveService().saveStrategicObjective(strategicObjective);
            result.put("id", strategicObjective.getId());
            result.put("success", true);
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing strategic objective: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteObjective(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getStrategicObjectiveService().deleteStrategicObjective(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting strategic objective: %s", e.getMessage()));
            return false;
        }
    }

}
