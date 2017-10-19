package com.verynet.gcint.controllers.risk.dashboard;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.controllers.templates.DashboardTemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 23/09/2016.
 */
@Controller
@RequestMapping(value = "/risk/dashboard")
public class RiskDashboardController extends DashboardTemplateController {

    private ControllerUtil controllerUtil;

    @Autowired
    public RiskDashboardController(ControllerUtil controllerUtil) {
        super(controllerUtil);
    }

    @Override
    public String getView() {
        return "risk/dashboard/info";
    }

    @Override
    public String getComponent() {
        return Components.er.toString();
    }

    @Override
    public String getDocumentsUrl() {
        return "risk/dashboard/documents";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/level")
    public
    @ResponseBody
    Map getLevel(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer processId = getProcessSelected(session);
        try {
            if (processId == -1) {
                result = Context.getRiskService().getLevelPercent(processId, getEntityData(session).getId());
            } else {
                result = Context.getRiskService().getLevelPercent(processId, null);
            }
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }


}
