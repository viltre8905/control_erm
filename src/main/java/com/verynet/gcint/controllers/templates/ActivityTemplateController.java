package com.verynet.gcint.controllers.templates;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.TimestampUtil;
import com.verynet.gcint.api.util.enums.ActivityStates;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by day on 25/09/2016.
 */
public abstract class ActivityTemplateController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ControllerUtil controllerUtil;
    private ResourceLoader resourceLoader;
    private DataSource dataSource;
    private JasperUtil jasperUtil;
    private final String REPORT_NAME = "Plan de acción.pdf";

    @Autowired
    public ActivityTemplateController(ControllerUtil controllerUtil, ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        this.controllerUtil = controllerUtil;
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.jasperUtil = jasperUtil;
    }

    public abstract String getComponent();

    public abstract String getView();

    @RequestMapping(method = RequestMethod.GET, value = "/activities")
    public String home(ModelMap map, HttpSession session) {
        Integer processId = getProcessSelected(session);
        User userLogged = getUserLogged();
        if (userLogged.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
            SubProcess subProcess = Context.getProcessService().getHeavySubProcess(processId);
            if (subProcess != null) {
                map.put("executors", subProcess.getMembers());
            }
        }
        if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
            List<GeneralProcess> generalProcessList = new ArrayList<>();
            generalProcessList.addAll(Context.getProcessService().getAllSubProcesses(processId));
            generalProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(processId));
            map.put("subProcesses", generalProcessList);
        }
        map.put("processSelected", processId);
        map.put("objectProcessSelected", getObjectProcessSelected(session));
        if (userLogged.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString()) ||
                userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString()) ||
                userLogged.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
            List<List<Map<String, List<NegativeAnswer>>>> result = Context.getAnswerService().getAllDeepNegativeAnswers(getComponent(), processId);
            map.put("negativeAnswerOfProcess", result.get(0));
            map.put("negativeAnswerOfSubProcess", result.get(1));
        } else {
            List<Map<String, List<NegativeAnswer>>> mapList = new ArrayList<>();
            HashMap<String, List<NegativeAnswer>> listHashMap = new HashMap<>();
            List<NegativeAnswer> negativeAnswers = getNegativeAnswers(processId);
            if (negativeAnswers.size() > 0) {
                listHashMap.put("", negativeAnswers);
                mapList.add(listHashMap);
            }
            map.put("negativeAnswerOfProcess", mapList);
            map.put("negativeAnswerOfSubProcess", new ArrayList<>());
        }
        List<Activity> activityList = Context.getActivityService().getAllActivities(getComponent(), processId, userLogged.getId(), false);
        GeneralProcess process = getObjectProcessSelected(session);
        if (process instanceof ActivityProcess && ((ActivityProcess) process).getParent() instanceof SubProcess) {
            activityList.addAll(Context.getActivityService().getAllActivities(getComponent(), ((ActivityProcess) process).getParent().getId(), userLogged.getId(), false));
        }
        map.put("activities", activityList);

        return getView();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/data")
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

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditActivity(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "negativeAnswerId", required = false) Integer negativeAnswerId,
                             @RequestParam(value = "description") String description,
                             @RequestParam(value = "executor", required = false) Integer executorId,
                             @RequestParam(value = "subProcess", required = false) Integer subProcessId,
                             @RequestParam(value = "date") String accomplishDate,
                             @RequestParam(value = "observation", required = false) String observation,
                             @RequestParam(value = "action") String action,
                             HttpSession session, ModelMap model) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer processId = getProcessSelected(session);
            Activity activity;
            if (action.equals("add")) {
                activity = new Activity();
            } else {
                activity = Context.getActivityService().getActivity(id);
            }
            Component component = Context.getNomenclatureService().getComponent(getComponent());
            ActivityState activityState = Context.getActivityService().getActivityState(ActivityStates.Asignada.toString());
            NegativeAnswer negativeAnswer = null;
            if (negativeAnswerId != null) {
                negativeAnswer = (NegativeAnswer) Context.getAnswerService().getAnswer(negativeAnswerId);
            }
            if (executorId != null) {
                GeneralProcess process = Context.getProcessService().getSubProcess(processId);
                if (executorId != -2) {
                    User executor = Context.getUserService().getUser(executorId);
                    createActivity(activity, executor, process,
                            observation, description, accomplishDate, component, activityState, action, negativeAnswer);
                    result.put("executor", String.format("%s %s", executor.getFirstName(), executor.getLastName()));
                } else {
                    SubProcess subProcess = Context.getProcessService().getHeavySubProcess(processId);
                    List<User> executors = subProcess.getMembers();
                    for (User executor : executors) {
                        createActivity(new Activity(), executor, process,
                                observation, description, accomplishDate, component, activityState, action, negativeAnswer);
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
                            observation, description, accomplishDate, component, activityState, action, negativeAnswer);
                    result.put("executor", String.format("%s %s", process.getResponsible().getFirstName(), process.getResponsible().getLastName()));
                    result.put("subProcess", process.getName());
                } else {
                    List<GeneralProcess> processes = new ArrayList<>();
                    processes.addAll(Context.getProcessService().getAllSubProcesses(processId));
                    processes.addAll(Context.getActivityProcessService().getAllActivityProcess(processId));
                    for (GeneralProcess process : processes) {
                        createActivity(new Activity(), process.getResponsible(), process,
                                observation, description, accomplishDate, component, activityState, action, negativeAnswer);
                        result.put("executor", String.format("%s %s", process.getResponsible().getFirstName(), process.getResponsible().getLastName()));
                        result.put("subProcess", process.getName());
                    }
                }

            }
            result.put("id", activity.getId());
            result.put("date", activity.toShortAccomplishDate());

        } catch (Exception e) {
            logger.error(String.format("Error creating or editing activity(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    public
    @ResponseBody
    boolean deleteActivity(@RequestParam(value = "id") Integer id) {
        try {
            return Context.getActivityService().deleteActivity(id);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting activity(%s)(Component: %s): %s", id, getComponent(), e.getMessage()));
            return false;
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public
    @ResponseBody
    Map updateActivity(@RequestParam(value = "id") Integer id,
                       @RequestParam(value = "description") String description,
                       @RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "type", required = false) Integer typeId,
                       @RequestParam(value = "procedence", required = false) Integer procedenceId,
                       MultipartHttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Activity activity = Context.getActivityService().getActivity(id);
            Context.getActivityService().deleteEvidence(id);
            Evidence evidence = new Evidence();
            evidence.setDescription(description);
            DocumentMetadata documentMetadata = controllerUtil.handleMultipartRequest(request);
            if (documentMetadata != null) {
                Document document = new Document();
                document.setTitle(title);
                document.setDocumentType(Context.getNomenclatureService().getDocumentType(typeId));
                document.setDocumentProcedence(Context.getNomenclatureService().getDocumentProcedence(procedenceId));
                document.setDocumentMetadata(documentMetadata);
                document = Context.getDocumentService().saveDocument(document);
                evidence.setDocument(document);
            }
            evidence = Context.getActivityService().saveEvidence(evidence);
            activity.setEvidence(evidence);
            activity.setActivityState(Context.getActivityService().getActivityState(ActivityStates.Resuelta.toString()));
            Context.getActivityService().saveActivity(activity);
        } catch (Exception e) {
            logger.error(String.format("Error setting evidence to activity(Component: %s): %s", id, getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
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
            logger.warn(String.format("Error downloading evidence(%s)(Component: %s): %s", id, getComponent(), io.getMessage()));
            io.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create_report")
    public
    @ResponseBody
    Map createReport(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String resourcesPath = "classpath:/reports/activities_subprocess_supervisory.jasper";
        User userLogged = getUserLogged();
        boolean isGeneralSupervisory = false;
        if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
            resourcesPath = "classpath:/reports/activities_process_supervisory.jasper";
        } else if (userLogged.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString())) {
            resourcesPath = "classpath:/reports/activities_general_supervisory.jasper";
            isGeneralSupervisory = true;
        }
        Resource r = resourceLoader.getResource(resourcesPath);
        try {
            String jasperPath = r.getFile().getPath();
            HashMap<String, Object> params = new HashMap<>();
            params.put("processId", getProcessSelected(session));
            params.put("componentId", Context.getNomenclatureService().getComponent(getComponent()).getId());
            if (!isGeneralSupervisory) {
                params.put("userId", userLogged.getId());
            }
            JasperPrint jasperPrint = jasperUtil.generateReport(jasperPath, params, dataSource.getConnection());
            jasperUtil.exportReport(jasperPrint, null, null, REPORT_NAME);
            result.put("success", true);
        } catch (IOException | SQLException | JRException e) {
            logger.warn(String.format("Error creating report(Component: %s): %s", getComponent(), e.getMessage()));
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
            logger.warn(String.format("Error exporting report(Component: %s): %s", getComponent(), e.getMessage()));
        }
    }

    public List<NegativeAnswer> getNegativeAnswers(Integer processId) {
        List<NegativeAnswer> result = Context.getAnswerService().getAllNegativeAnswers(getComponent(), processId);
        return result;
    }

    @ModelAttribute("documentTypes")
    public List<DocumentType> getAllDocumentTypes() {
        return Context.getNomenclatureService().getAllDocumentTypes();
    }

    @ModelAttribute("documentProcedences")
    public List<DocumentProcedence> getAllDocumentProcedences() {
        return Context.getNomenclatureService().getAllDocumentProcedences();
    }

    private void createActivity(Activity activity, User executor, GeneralProcess process, String observation, String description, String accomplishDate, Component component, ActivityState activityState, String action, NegativeAnswer negativeAnswer) {
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
        if (action.equals("add") && negativeAnswer != null) {
            Context.getAnswerService().addActivityToNegativeAnswer(negativeAnswer.getId(), activity);
        }
    }


}
