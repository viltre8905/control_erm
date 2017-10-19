package com.verynet.gcint.controllers.monitoring.committee.preventionplan;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Risk;
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
 * Created by day on 02/03/2017.
 */
@Controller
@RequestMapping(value = "/monitoring/committee/risk")
public class PreventionPlanController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ResourceLoader resourceLoader;
    private DataSource dataSource;
    private JasperUtil jasperUtil;
    private final String REPORT_NAME = "Plan de prevención de riesgos.pdf";

    @Autowired
    public PreventionPlanController(ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.jasperUtil = jasperUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/preventionPlan")
    public String getPreventionPlan(ModelMap model, HttpSession session) {
        Integer entityId = getEntityData(session).getId();
        model.put("all_processes", Context.getProcessService().getAllProcess(entityId));
        return "monitoring/committee/preventionplan/planRiskPrevention";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/status")
    public
    @ResponseBody
    boolean setRiskStatus(@RequestParam(value = "id") Integer id,
                          @RequestParam(value = "status") boolean status) {
        Risk risk = Context.getRiskService().getRisk(id);
        risk.setIncludeInReport(status);
        Context.getRiskService().saveRisk(risk);
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create_report")
    public
    @ResponseBody
    Map createReport(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String resourcesPath = "classpath:/reports/entity_risk_control.jasper";
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
