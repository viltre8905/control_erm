package com.verynet.gcint.controllers.controlenvironment.dashboard;

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
@RequestMapping(value = "/control-environment/dashboard")
public class ControlEnvironmentDashboardController extends DashboardTemplateController {
    private ControllerUtil controllerUtil;

    @Autowired
    public ControlEnvironmentDashboardController(ControllerUtil controllerUtil) {
        super(controllerUtil);
    }


    @Override
    public String getView() {
        return "controlenvironment/dashboard/info";
    }

    @Override
    public String getComponent() {
        return Components.ec.toString();
    }

    @Override
    public String getDocumentsUrl() {
        return "controlenvironment/dashboard/documents";
    }
}
