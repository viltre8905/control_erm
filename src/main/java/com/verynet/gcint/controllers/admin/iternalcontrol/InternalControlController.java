package com.verynet.gcint.controllers.admin.iternalcontrol;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.controllers.GeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 08/10/2017.
 */
@Controller
@RequestMapping(value = "/admin/internal-control")
public class InternalControlController extends GeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/processes")
    public String home() {
        return "admin/internalcontrol/processes";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/execute-guide")
    @ResponseBody
    Map executeGuide() {
        Map<String, Object> result = new HashMap<>();
        try {
            Context.getAnswerService().deleteAllAnswer(getUserLogged().getEntity().getId());
            result.put("success", "true");
        } catch (Exception e) {
            result.put("success", "false");
            result.put("message", "Ha ocurrido un error inesperado");
            logger.warn("Error deleting all answer: %s", e.getMessage());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/execute-risk")
    public
    @ResponseBody
    Map executeRisk() {
        Map<String, Object> result = new HashMap<>();
        try {
            Context.getActivityService().deleteAllRiskActivities(getUserLogged().getEntity().getId());
            result.put("success", "true");
        } catch (Exception e) {
            result.put("success", "false");
            result.put("message", "Ha ocurrido un error inesperado");
            logger.warn("Error deleting all risk activities: %s", e.getMessage());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/execute-committee")
    public
    @ResponseBody
    Map executeCommittee() {
        Map<String, Object> result = new HashMap<>();
        try {
            Context.getACInformService().deleteAllActionControlInform(getUserLogged().getEntity().getId());
            result.put("success", "true");
        } catch (Exception e) {
            result.put("success", "false");
            result.put("message", "Ha ocurrido un error inesperado");
            logger.warn("Error deleting all action control inform: %s", e.getMessage());
        }
        return result;
    }

}
