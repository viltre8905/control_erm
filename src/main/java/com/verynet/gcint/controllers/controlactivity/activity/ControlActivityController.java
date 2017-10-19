package com.verynet.gcint.controllers.controlactivity.activity;

import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.api.util.report.JasperUtil;
import com.verynet.gcint.controllers.templates.ActivityTemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;

/**
 * Created by day on 25/09/2016.
 */
@Controller
@RequestMapping(value = "/control-activity/activity")
public class ControlActivityController extends ActivityTemplateController {

    @Autowired
    public ControlActivityController(ControllerUtil controllerUtil, ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        super(controllerUtil, resourceLoader, dataSource, jasperUtil);
    }

    @Override
    public String getView() {
        return "controlactivity/activity/activities";
    }

    @Override
    public String getComponent() {
        return Components.ac.toString();
    }
}
