package com.verynet.gcint.controllers.monitoring.dashboard;

import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.controllers.templates.DashboardTemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by day on 23/09/2016.
 */
@Controller
@RequestMapping(value = "/monitoring/dashboard")
public class MonitoringDashboardController extends DashboardTemplateController {

    private ControllerUtil controllerUtil;

    @Autowired
    public MonitoringDashboardController(ControllerUtil controllerUtil) {
        super(controllerUtil);
    }

    @Override
    public String getView() {
        return "monitoring/dashboard/info";
    }

    @Override
    public String getComponent() {
        return Components.sm.toString();
    }

    @Override
    public String getDocumentsUrl() {
        return "monitoring/dashboard/documents";
    }
}
