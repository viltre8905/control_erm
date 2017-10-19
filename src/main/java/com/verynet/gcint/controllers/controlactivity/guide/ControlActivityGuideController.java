package com.verynet.gcint.controllers.controlactivity.guide;

import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.api.util.report.JasperUtil;
import com.verynet.gcint.controllers.templates.GuideTemplateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;

/**
 * Created by day on 24/09/2016.
 */
@Controller
@RequestMapping(value = "/control-activity/guide")
public class ControlActivityGuideController extends GuideTemplateController {

    @Autowired
    public ControlActivityGuideController(ControllerUtil controllerUtil, ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        super(controllerUtil, resourceLoader, dataSource, jasperUtil);
    }

    @Override
    public String getView() {
        return "controlactivity/guide/guides";
    }

    @Override
    public String getComponent() {
        return Components.ac.toString();
    }
}
