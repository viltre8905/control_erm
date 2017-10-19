package com.verynet.gcint.controllers.monitoring.committee.report;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.ActionControlInform;
import com.verynet.gcint.api.model.Deficiency;
import com.verynet.gcint.controllers.CommonGeneralController;
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
 * Created by day on 02/10/2016.
 */
@Controller
@RequestMapping("/monitoring/committee/report/deficiency")
public class ReportDeficiencyController extends CommonGeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public String getMembers(@RequestParam(value = "id") Integer id, ModelMap map) {
        ActionControlInform actionControlInform = Context.getACInformService().getHeavyActionControlInform(id);
        map.put("report", actionControlInform);
        return "monitoring/committee/report/deficiency/all";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getGuideData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Deficiency deficiency = Context.getDeficiencyService().getDeficiency(id);
        if (deficiency == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("number", deficiency.getNo());
        result.put("description", deficiency.getBody());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditDeficiency(@RequestParam(value = "id", required = false) Integer id,
                               @RequestParam(value = "idReport", required = false) Integer idReport,
                               @RequestParam(value = "number") Integer number,
                               @RequestParam(value = "description") String description,
                               @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        Deficiency deficiency;
        try {
            if (action.equals("add")) {
                deficiency = new Deficiency();
                ActionControlInform actionControlInform = Context.getACInformService().getActionControlInform(idReport);
                deficiency.setActionControlInform(actionControlInform);
            } else {
                deficiency = Context.getDeficiencyService().getDeficiency(id);
            }
            deficiency.setNo(number);
            deficiency.setBody(description);
            deficiency = Context.getDeficiencyService().saveDeficiency(deficiency);
            result.put("id", deficiency.getId());
            result.put("success", true);
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing deficiency by control committee: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteDeficiency(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getDeficiencyService().deleteDeficiency(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting deficiency(%s) by control committee: %s", id, e.getMessage()));
            return false;
        }
    }
}
