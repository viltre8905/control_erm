package com.verynet.gcint.controllers.infoandcom.dashboard;

import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.controllers.templates.DashboardTemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by day on 23/09/2016.
 */
@Controller
@RequestMapping(value = "/info-and-com/dashboard")
public class InfoAndComDashboardController extends DashboardTemplateController {

    private ControllerUtil controllerUtil;

    @Autowired
    public InfoAndComDashboardController(ControllerUtil controllerUtil) {
        super(controllerUtil);
    }

    @Override
    public String getView() {
        return "infoandcom/dashboard/info";
    }

    @Override
    public String getComponent() {
        return Components.ic.toString();
    }

    @Override
    public String getDocumentsUrl() {
        return "infoandcom/dashboard/documents";
    }
}
