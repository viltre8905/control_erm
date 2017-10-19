package com.verynet.gcint.controllers.monitoring.activity;

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
@RequestMapping(value = "/monitoring/activity")
public class MonitoringActivityController extends ActivityTemplateController {

    @Autowired
    public MonitoringActivityController(ControllerUtil controllerUtil, ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        super(controllerUtil, resourceLoader, dataSource, jasperUtil);
    }

    @Override
    public String getView() {
        return "monitoring/activity/activities";
    }

    @Override
    public String getComponent() {
        return Components.sm.toString();
    }
}
