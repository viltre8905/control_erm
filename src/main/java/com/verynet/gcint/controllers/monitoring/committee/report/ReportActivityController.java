package com.verynet.gcint.controllers.monitoring.committee.report;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.model.Process;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.TimestampUtil;
import com.verynet.gcint.api.util.enums.ActivityStates;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.controllers.CommonGeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by day on 02/10/2016.
 */
@Controller
@RequestMapping("/monitoring/committee/report/deficiency/activity")
public class ReportActivityController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ControllerUtil controllerUtil;

    @Autowired
    public ReportActivityController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/activities")
    public String getActivities(@RequestParam(value = "id") Integer id, ModelMap map, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Deficiency deficiency = Context.getDeficiencyService().getDeficiency(id);
        if (deficiency != null) {
            map.put("deficiency", deficiency);
            map.put("allProcesses", Context.getProcessService().getAllProcess(getEntityData(session).getId()));
            return "monitoring/committee/report/deficiency/activity/activities";
        } else {
            response.sendRedirect(request.getContextPath() + "/monitoring/activity/activities");
            return "monitoring/activity/activities";
        }
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
        result.put("process", activity.getProcess().getId());

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public
    @ResponseBody
    Map createOrEditActivity(@RequestParam(value = "id", required = false) Integer id,
                             @RequestParam(value = "idDeficiency", required = false) Integer deficiencyId,
                             @RequestParam(value = "description") String description,
                             @RequestParam(value = "process") Integer processId,
                             @RequestParam(value = "date") String accomplishDate,
                             @RequestParam(value = "observation", required = false) String observation,
                             @RequestParam(value = "action") String action,
                             HttpSession session) throws Exception {
        Map<String, Object> result = new HashMap<>();
        try {
            Activity activity;
            if (action.equals("add")) {
                activity = new Activity();
            } else {
                activity = Context.getActivityService().getActivity(id);
            }

            Component component = Context.getNomenclatureService().getComponent(Components.sm.toString());
            ActivityState activityState = Context.getActivityService().getActivityState(ActivityStates.Asignada.toString());
            Deficiency deficiency = Context.getDeficiencyService().getDeficiency(deficiencyId);
            if (processId != -2) {
                Process process = Context.getProcessService().getProcess(processId);
                createActivity(activity, process,
                        observation, description, accomplishDate, component, activityState, action, deficiency);
                result.put("executor", String.format("%s %s", activity.getExecutor().getFirstName(), activity.getExecutor().getLastName()));
                result.put("process", process.getName());
            } else {
                List<Process> processes = Context.getProcessService().getAllProcess(getEntityData(session).getId());
                for (Process process : processes) {
                    activity = new Activity();
                    createActivity(activity, process,
                            observation, description, accomplishDate, component, activityState, action, deficiency);
                    result.put("executor", String.format("%s %s", activity.getExecutor().getFirstName(), activity.getExecutor().getLastName()));
                    result.put("process", process.getName());
                }
            }
            result.put("date", activity.toShortAccomplishDate());
            result.put("id", activity.getId());
        } catch (Exception e) {
            logger.error(String.format("Error creating or editing activity by control committee: %s", e.getMessage()));
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
            logger.warn(String.format("Error deleting activity(%s) by control committee: %s", id, e.getMessage()));
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
            logger.warn(String.format("Error downloading activity document evidence(%s) by control committee: %s", id, io.getMessage()));
            io.printStackTrace();
        }
    }

    private void createActivity(Activity activity, GeneralProcess process, String observation, String description, String accomplishDate, Component component, ActivityState activityState, String action, Deficiency deficiency) {
        activity.setExecutor(process.getResponsible());
        activity.setProcess(process);
        activity.setActivityDescription(description);
        Date date = new Date();
        activity.setInitDate(date);
        activity.setAccomplishDate(TimestampUtil.getDayFromSimpleText(accomplishDate));
        activity.setObservation(observation);
        activity.setResponsible(getUserLogged());
        if (action.equals("add")) {
            activity.setComponent(component);
            activity.setDeficiency(deficiency);
        }
        activity.setActivityState(activityState);
        Context.getActivityService().saveActivity(activity);
    }
}
