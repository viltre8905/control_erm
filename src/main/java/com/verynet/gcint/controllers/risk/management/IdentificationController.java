package com.verynet.gcint.controllers.risk.management;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.TimestampUtil;
import com.verynet.gcint.api.util.enums.ActivityStates;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.api.util.enums.Roles;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by day on 26/09/2016.
 */
@Controller
@RequestMapping(value = "/risk/management")
public class IdentificationController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ControllerUtil controllerUtil;
    private ResourceLoader resourceLoader;
    private DataSource dataSource;
    private JasperUtil jasperUtil;
    private final String REPORT_NAME1 = "Ficha técnica de riesgos.pdf";
    private final String REPORT_NAME2 = "Plan de prevención de riesgos.pdf";

    @Autowired
    public IdentificationController(ControllerUtil controllerUtil, ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        this.controllerUtil = controllerUtil;
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.jasperUtil = jasperUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/identification")
    public String identification(ModelMap model, HttpSession session) {
        Integer processId = getProcessSelected(session);
        GeneralProcess process = getObjectProcessSelected(session);
        model.put("objectProcessSelected", process);
        model.put("objectives", process.getObjectives());
        model.put("subProcesses", Context.getProcessService().getAllSubProcesses(processId));
        return "risk/management/identification";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
    public
    @ResponseBody
    Map getRiskData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Risk risk = Context.getRiskService().getRisk(id);
        if (risk == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("description", risk.getDescription());
        result.put("generator", risk.getGenerator());
        result.put("assets", risk.getAssets());
        result.put("cause", risk.getCause());
        result.put("consequence", risk.getConsequence());
        result.put("probability", risk.getProbability());
        result.put("impact", risk.getImpact());
        result.put("procedence", risk.getProcedence());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditRisk(@RequestParam(value = "id", required = false) Integer id,
                         @RequestParam(value = "idObjective", required = false) Integer idObjective,
                         @RequestParam(value = "description") String description,
                         @RequestParam(value = "generator") String generator,
                         @RequestParam(value = "assets", required = false) String assets,
                         @RequestParam(value = "cause") String cause,
                         @RequestParam(value = "consequence") String consequence,
                         @RequestParam(value = "probability") String probability,
                         @RequestParam(value = "impact") String impact,
                         @RequestParam(value = "procedence") Integer procedence,
                         @RequestParam(value = "action") String action) {
        Map<String, Object> result = new HashMap<>();
        Risk risk;
        if (action.equals("add")) {
            risk = new Risk();
            risk.setIncludeInReport(true);
        } else {
            risk = Context.getRiskService().getRisk(id);
        }
        risk.setDescription(description);
        risk.setGenerator(generator);
        risk.setAssets(assets);
        risk.setCause(cause);
        risk.setConsequence(consequence);
        risk.setProbability(probability);
        risk.setImpact(impact);
        risk.setProcedence(procedence);
        risk.setLevel("");
        try {
            risk = Context.getRiskService().saveRisk(risk);
            if (action.equals("add")) {
                Objective objective = Context.getProcessService().getObjective(idObjective);
                objective.getRisks().add(risk);
                objective = Context.getProcessService().saveObjective(objective);
                risk.setObjective(objective);
                Context.getRiskService().saveRisk(risk);
            }
            result.put("id", risk.getId());
            result.put("level", risk.getLevel());
            result.put("success", true);
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing risk: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteRisk(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getRiskService().deleteRisk(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting risk(%s): %s", id, e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activities")
    public String getActivities(@RequestParam(value = "id") Integer id, ModelMap model, HttpSession session,
                                HttpServletRequest request, HttpServletResponse response) throws IOException {
        Risk risk = Context.getRiskService().getRisk(id);
        if (risk != null) {
            model.put("activities", risk.getActivities());
            Integer processId = getProcessSelected(session);
            model.put("objectProcessSelected", getObjectProcessSelected(session));
            model.put("idRisk", id);
            User userLogged = getUserLogged();
            if (userLogged.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
                SubProcess subProcess = Context.getProcessService().getHeavySubProcess(processId);
                model.put("executors", subProcess.getMembers());
            }
            if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
                List<GeneralProcess> generalProcessList = new ArrayList<>();
                generalProcessList.addAll(Context.getProcessService().getAllSubProcesses(processId));
                generalProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(processId));
                model.put("subProcesses", generalProcessList);
            }
            return "risk/management/activities";
        } else {
            response.sendRedirect(request.getContextPath() + "/risk/management/identification");
            return "risk/management/identification";
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activity/create")
    public
    @ResponseBody
    Map createOrEditActivity(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "idRisk", required = false) Integer idRisk,
                             @RequestParam(value = "description") String description,
                             @RequestParam(value = "executor", required = false) Integer executorId,
                             @RequestParam(value = "subProcess", required = false) Integer subProcessId,
                             @RequestParam(value = "date") String accomplishDate,
                             @RequestParam(value = "observation", required = false) String observation,
                             @RequestParam(value = "action") String action,
                             HttpSession session) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer processId = getProcessSelected(session);
            Activity activity;
            if (action.equals("add")) {
                activity = new Activity();
            } else {
                activity = Context.getActivityService().getActivity(id);
            }
            Component component = Context.getNomenclatureService().getComponent(Components.er.toString());
            ActivityState activityState = Context.getActivityService().getActivityState(ActivityStates.Asignada.toString());
            if (executorId != null) {
                GeneralProcess process = Context.getProcessService().getSubProcess(processId);
                if (executorId != -2) {
                    User executor = Context.getUserService().getUser(executorId);
                    createActivity(activity, executor, process,
                            observation, description, accomplishDate, component, activityState, action, idRisk);
                    result.put("executor", String.format("%s %s", executor.getFirstName(), executor.getLastName()));
                } else {
                    SubProcess subProcess = Context.getProcessService().getHeavySubProcess(processId);
                    List<User> executors = subProcess.getMembers();
                    for (User executor : executors) {
                        createActivity(new Activity(), executor, process,
                                observation, description, accomplishDate, component, activityState, action, idRisk);
                        result.put("executor", String.format("%s %s", executor.getFirstName(), executor.getLastName()));
                    }
                }
            } else {
                if (subProcessId != -2) {
                    GeneralProcess process = Context.getProcessService().getSubProcess(subProcessId);
                    if (process == null) {
                        process = Context.getActivityProcessService().getActivityProcess(subProcessId);
                    }
                    createActivity(activity, process.getResponsible(), process,
                            observation, description, accomplishDate, component, activityState, action, idRisk);
                    result.put("executor", String.format("%s %s", process.getResponsible().getFirstName(), process.getResponsible().getLastName()));
                    result.put("subProcess", process.getName());
                } else {
                    List<GeneralProcess> processes = new ArrayList<>();
                    processes.addAll(Context.getProcessService().getAllSubProcesses(processId));
                    processes.addAll(Context.getActivityProcessService().getAllActivityProcess(processId));
                    for (GeneralProcess process : processes) {
                        createActivity(new Activity(), process.getResponsible(), process,
                                observation, description, accomplishDate, component, activityState, action, idRisk);
                        result.put("executor", String.format("%s %s", process.getResponsible().getFirstName(), process.getResponsible().getLastName()));
                        result.put("subProcess", process.getName());
                    }
                }
            }
            result.put("id", activity.getId());
            result.put("date", activity.toShortAccomplishDate());
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing risk control activities: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activity/data")
    public
    @ResponseBody
    Map getActivityData(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Activity activity = Context.getActivityService().getActivity(id);
        if (activity == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("description", activity.getActivityDescription());
        result.put("date", activity.toShortAccomplishDate());
        result.put("observation", activity.getObservation());
        User userLogged = getUserLogged();
        if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
            result.put("subProcess", activity.getProcess().getId());
        } else {
            result.put("executor", activity.getExecutor().getId());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activity/delete")
    public
    @ResponseBody
    boolean deleteActivity(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getActivityService().deleteActivity(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting risk control activity(%s): %s", id, e.getMessage()));
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/solution")
    public
    @ResponseBody
    Map getActivitySolution(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Activity activity = Context.getActivityService().getActivity(id);
        if (activity == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("description", activity.getEvidence().getDescription());
        Document document = activity.getEvidence().getDocument();
        if (document != null) {
            result.put("title", document.getTitle());
            result.put("type", document.getDocumentType().getName());
            result.put("procedence", document.getDocumentProcedence().getName());
            result.put("fileName", String.format("%s.%s", document.getDocumentMetadata().getFileName(), document.getDocumentMetadata().getExtension()));
            result.put("id", document.getDocumentMetadata().getId());
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/solution/accept")
    public
    @ResponseBody
    Map acceptSolution(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Activity activity = Context.getActivityService().getActivity(id);
        if (activity == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        activity.setActivityState(Context.getActivityService().getActivityState(ActivityStates.Aceptada.toString()));
        Context.getActivityService().saveActivity(activity);
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/solution/reject")
    public
    @ResponseBody
    Map rejectSolution(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        Activity activity = Context.getActivityService().getActivity(id);
        if (activity == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        activity.setActivityState(Context.getActivityService().getActivityState(ActivityStates.Rechazada.toString()));
        Context.getActivityService().saveActivity(activity);
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/document", produces = "multipart/form-data")
    public void getDocument(@RequestParam(value = "id") String id, HttpServletResponse response) {
        DocumentMetadata documentMetadata = Context.getDocumentService().getDocumentMetadata(id);
        try {
            controllerUtil.handleMultipartResponse(documentMetadata, response);
        } catch (IOException io) {
            logger.warn(String.format("Error downloading risk control activity evidence(%s): %s", id, io.getMessage()));
            io.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create_report")
    public
    @ResponseBody
    Map createReport(HttpSession session, @RequestParam(value = "report", required = false) Integer report,
                     @RequestParam(value = "idRisk", required = false) Integer idRisk) {
        Map<String, Object> result = new HashMap<>();
        Resource r = resourceLoader.getResource("classpath:/reports/risks.jasper");
        if (report != null) {
            if (report == 1) {
                r = resourceLoader.getResource("classpath:/reports/risk_control.jasper");
            } else {
                r = resourceLoader.getResource("classpath:/reports/risk.jasper");
            }
        }
        try {
            String jasperPath = r.getFile().getPath();
            HashMap<String, Object> params = new HashMap<>();
            if (idRisk == null) {
                params.put("processId", getProcessSelected(session));
            } else {
                params.put("id", idRisk);
            }
            JasperPrint jasperPrint = jasperUtil.generateReport(jasperPath, params, dataSource.getConnection());
            if (report != null && report != 2) {
                jasperUtil.exportReport(jasperPrint, null, null, REPORT_NAME2);
            } else {
                jasperUtil.exportReport(jasperPrint, null, null, REPORT_NAME1);
            }
            result.put("success", true);
        } catch (IOException | SQLException | JRException e) {
            logger.warn(String.format("Error creating report: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/export", produces = "multipart/form-data")
    public void exportReport(HttpServletResponse response, @RequestParam(value = "report", required = false) Integer report) {
        try {
            if (report != null && report != 2) {
                jasperUtil.handleMultipartResponse(REPORT_NAME2, response);
            } else {
                jasperUtil.handleMultipartResponse(REPORT_NAME1, response);
            }
        } catch (IOException e) {
            logger.warn(String.format("Error exporting report: %s", e.getMessage()));
        }
    }

    private void createActivity(Activity activity, User executor, GeneralProcess process, String observation, String description, String accomplishDate, Component component, ActivityState activityState, String action, Integer idRisk) {
        activity.setExecutor(executor);
        activity.setProcess(process);
        activity.setActivityDescription(description);
        Date date = new Date();
        activity.setInitDate(date);
        activity.setAccomplishDate(TimestampUtil.getDayFromSimpleText(accomplishDate));
        activity.setObservation(observation);
        activity.setResponsible(getUserLogged());
        if (action.equals("add")) {
            activity.setComponent(component);
        }
        activity.setActivityState(activityState);
        activity = Context.getActivityService().saveActivity(activity);
        if (action.equals("add")) {
            Risk risk = Context.getRiskService().getRisk(idRisk);
            risk.getActivities().add(activity);
            risk = Context.getRiskService().saveRisk(risk);
            activity.setRisk(risk);
            Context.getActivityService().saveActivity(activity);
        }
    }
}
