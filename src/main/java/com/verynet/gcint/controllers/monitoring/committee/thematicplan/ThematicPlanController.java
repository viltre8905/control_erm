package com.verynet.gcint.controllers.monitoring.committee.thematicplan;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.EntityData;
import com.verynet.gcint.api.model.Theme;
import com.verynet.gcint.api.util.report.JasperUtil;
import com.verynet.gcint.controllers.CommonGeneralController;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by day on 19/02/2017.
 */
@Controller
@RequestMapping("/monitoring/committee/thematic-plan")
public class ThematicPlanController extends CommonGeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ResourceLoader resourceLoader;
    private DataSource dataSource;
    private JasperUtil jasperUtil;
    private final String REPORT_NAME = "Plan temático.pdf";

    @Autowired
    public ThematicPlanController(ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.jasperUtil = jasperUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/themes")
    public String getThematicPlan(ModelMap model, HttpSession session) {
        Integer entityId = getEntityData(session).getId();
        model.put("themes", Context.getThemeService().getAllThemes(entityId));
        return "monitoring/committee/thematicplan/themes";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getThemeData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Theme theme = Context.getThemeService().getTheme(id);
        if (theme == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        result.put("no", theme.getNo());
        result.put("description", theme.getTheme());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditTheme(@RequestParam(value = "id", required = false) Integer id,
                          @RequestParam(value = "no") Integer no,
                          @RequestParam(value = "description") String description,
                          @RequestParam(value = "action") String action, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Theme theme = null;
        if (action.equals("add")) {
            theme = new Theme();
        } else {
            theme = Context.getThemeService().getTheme(id);
        }
        theme.setNo(no);
        theme.setTheme(description);
        EntityData entityData = getEntityData(session);
        theme.setEntity(entityData);
        theme = Context.getThemeService().saveTheme(theme);
        result.put("id", theme.getId());
        result.put("count", Context.getThemeService().getAllThemes(entityData.getId()).size());
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteTheme(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getThemeService().deleteTheme(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting theme(%s): %s", id, e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create_report")
    public
    @ResponseBody
    Map createReport(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String resourcesPath = "classpath:/reports/thematic_plan.jasper";
        Resource r = resourceLoader.getResource(resourcesPath);
        try {
            String jasperPath = r.getFile().getPath();
            HashMap<String, Object> params = new HashMap<>();
            params.put("entityId", getEntityData(session).getId());
            JasperPrint jasperPrint = jasperUtil.generateReport(jasperPath, params, dataSource.getConnection());
            jasperUtil.exportReport(jasperPrint, null, null, REPORT_NAME);
            result.put("success", true);
        } catch (IOException | SQLException | JRException e) {
            logger.warn(String.format("Error creating report: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/export", produces = "multipart/form-data")
    public void exportReport(HttpServletResponse response) {
        try {
            jasperUtil.handleMultipartResponse(REPORT_NAME, response);
        } catch (IOException e) {
            logger.warn(String.format("Error exporting report: %s", e.getMessage()));
        }
    }
}
