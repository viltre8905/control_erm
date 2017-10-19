package com.verynet.gcint.controllers.templates;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.util.ControllerUtil;
import com.verynet.gcint.api.util.TimestampUtil;
import com.verynet.gcint.api.util.enums.ActivityStates;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.api.util.enums.Roles;
import com.verynet.gcint.controllers.CommonGeneralController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by day on 25/09/2016.
 */
public abstract class DashboardTemplateController extends CommonGeneralController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ControllerUtil controllerUtil;

    @Autowired
    public DashboardTemplateController(ControllerUtil controllerUtil) {
        this.controllerUtil = controllerUtil;
    }

    public abstract String getComponent();

    public abstract String getDocumentsUrl();

    public abstract String getView();

    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public String home(ModelMap map, HttpSession session) {
        Integer processId = getProcessSelected(session);
        Integer entityDataId = getEntityData(session).getId();
        map.put("activitiesState", getActivitiesState(processId, entityDataId));
        if (getComponent().equals(Components.er.toString())) {
            map.put("objectivesAffected", Context.getProcessService().getObjectivesMoreAffected(processId, entityDataId, 10));
        }
        return getView();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/allData")
    @ResponseBody
    public Map getDashboardData(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer processId = getProcessSelected(session);
            Integer entityDataId = getEntityData(session).getId();
            Map allData = getAllData(processId, entityDataId);
            result.put("efficacy", allData);
            result.put("questionCount", allData.get("questionCount"));
            result.put("procedurePercent", allData.get("procedurePercent"));
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error getting dashboard data(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/activities/states")
    public
    @ResponseBody
    Map getActivitiesStates(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer processId = getProcessSelected(session);
            User userLogged = getUserLogged();
            List<Activity> activities = new ArrayList<>();
            if (userLogged.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString())) {
                if (processId != -1) {
                    List<SubProcess> subProcesses = Context.getProcessService().getAllSubProcesses(processId);
                    List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
                    for (SubProcess subProcess : subProcesses) {
                        activities.addAll(Context.getActivityService().getAllActivities(getComponent(), subProcess.getId()));
                        activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(subProcess.getId()));
                    }
                    for (ActivityProcess activityProcess : activityProcessList) {
                        activities.addAll(Context.getActivityService().getAllActivities(getComponent(), activityProcess.getId()));
                    }
                } else {
                    activities.addAll(Context.getActivityService().getAllActivities(getEntityData(session).getId(), getComponent()));
                }
            } else if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
                activities.addAll(Context.getActivityService().getAllActivities(getComponent(), processId, userLogged.getId(), false));
                List<SubProcess> subProcesses = Context.getProcessService().getAllSubProcesses(processId);
                List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
                for (SubProcess subProcess : subProcesses) {
                    activities.addAll(Context.getActivityService().getAllActivities(getComponent(), subProcess.getId(), userLogged.getId(), true));
                    activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(subProcess.getId()));
                }
                for (ActivityProcess activityProcess : activityProcessList) {
                    activities.addAll(Context.getActivityService().getAllActivities(getComponent(), activityProcess.getId(), userLogged.getId(), true));
                }
            }
            if (userLogged.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
                activities = Context.getActivityService().getAllActivities(getComponent(), processId, userLogged.getId(), false);
                activities.addAll(Context.getActivityService().getAllActivities(getComponent(), processId, userLogged.getId(), true));
                List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
                for (ActivityProcess activityProcess : activityProcessList) {
                    activities.addAll(Context.getActivityService().getAllActivities(getComponent(), activityProcess.getId(), userLogged.getId(), true));
                }
            }
            fillActivityState(result, activities);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error getting activities States(Component: %s): %s", getComponent(), e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/documents")
    public String getDocuments(ModelMap map, HttpSession session) {
        Integer processId = getProcessSelected(session);
        Integer entityDataId = getEntityData(session).getId();
        map.put("documents", getAllDocuments(processId, entityDataId));
        return getDocumentsUrl();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download", produces = "multipart/form-data")
    public void getDocumentFile(@RequestParam(value = "id") String id, HttpServletResponse response) {
        DocumentMetadata documentMetadata = Context.getDocumentService().getDocumentMetadata(id);
        try {
            controllerUtil.handleMultipartResponse(documentMetadata, response);
        } catch (IOException io) {
            logger.warn(String.format("Error downloading document(%s)(Component: %s): %s", id, getComponent(), io.getMessage()));
            io.printStackTrace();
        }
    }

    public List<Document> getAllDocuments(Integer processId, Integer entityDataId) {
        List<Document> documents = new ArrayList<>();
        List<AffirmativeAnswer> answers;
        if (processId != -1) {
            answers = Context.getAnswerService().getAffirmativeAnswers(getComponent(), processId);
        } else {
            answers = Context.getAnswerService().getAffirmativeAnswers(entityDataId, getComponent());
        }
        for (AffirmativeAnswer affirmativeAnswer : answers) {
            if (affirmativeAnswer.getEvidence().getDocument() != null) {
                documents.add(affirmativeAnswer.getEvidence().getDocument());
            }
        }
        return documents;
    }


    public Map getAllData(Integer processId, Integer entityDataId) {
        Map<String, Object> result = new HashMap<>();
        result.put("efficacy", "Ineficaz");
        try {
            if (processId != -1) {
                return Context.getGuideService().getAllDashboardData(getComponent(), processId);
            } else {
                return Context.getGuideService().getAllDashboardData(entityDataId, getComponent(), processId);
            }

        } catch (Exception e) {
            logger.warn(String.format("Error getting efficacy(Component: %s): %s", getComponent(), e.getMessage()));
        }
        return result;
    }

    public Map getActivitiesState(Integer processId, Integer entityDataId) {
        Map<String, Object> result = new HashMap<>();
        User userLogged = getUserLogged();
        List<Activity> activities = new ArrayList<>();
        if (userLogged.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString())) {
            if (processId != -1) {
                List<SubProcess> subProcesses = Context.getProcessService().getAllSubProcesses(processId);
                List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
                List<Map> maps = new ArrayList<>();
                activities.addAll(Context.getActivityService().getAllActivities(getComponent(), processId));
                for (SubProcess subProcess : subProcesses) {
                    List<Activity> activityList = Context.getActivityService().getAllActivities(getComponent(), subProcess.getId());
                    activities.addAll(activityList);
                    Map<String, Object> map = new HashMap<>();
                    map.put("subProcess", subProcess.getName());
                    fillActivityState(map, activityList);
                    maps.add(map);
                    activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(subProcess.getId()));
                }
                for (ActivityProcess activityProcess : activityProcessList) {
                    List<Activity> activityList = Context.getActivityService().getAllActivities(getComponent(), activityProcess.getId());
                    activities.addAll(activityList);
                    Map<String, Object> map = new HashMap<>();
                    map.put("activityProcess", activityProcess.getName());
                    fillActivityState(map, activityList);
                    maps.add(map);
                }
                result.put("subProcesses", maps);
            } else {
                activities.addAll(Context.getActivityService().getAllActivities(entityDataId, getComponent()));
            }
        } else if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
            activities.addAll(Context.getActivityService().getAllActivities(getComponent(), processId, userLogged.getId(), false));
            List<SubProcess> subProcesses = Context.getProcessService().getAllSubProcesses(processId);
            List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
            List<Map> maps = new ArrayList<>();
            for (SubProcess subProcess : subProcesses) {
                List<Activity> activityList = Context.getActivityService().getAllActivities(getComponent(), subProcess.getId(), userLogged.getId(), true);
                activities.addAll(activityList);
                Map<String, Object> map = new HashMap<>();
                map.put("subProcess", subProcess.getName());
                fillActivityState(map, activityList);
                maps.add(map);
                activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(subProcess.getId()));
            }
            for (ActivityProcess activityProcess : activityProcessList) {
                List<Activity> activityList = Context.getActivityService().getAllActivities(getComponent(), activityProcess.getId(), userLogged.getId(), true);
                activities.addAll(activityList);
                Map<String, Object> map = new HashMap<>();
                map.put("activityProcess", activityProcess.getName());
                fillActivityState(map, activityList);
                maps.add(map);
            }
            result.put("subProcesses", maps);
        } else {
            activities = Context.getActivityService().getAllActivities(getComponent(), processId, userLogged.getId(), false);
            activities.addAll(Context.getActivityService().getAllActivities(getComponent(), processId, userLogged.getId(), true));
            List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
            List<Map> maps = new ArrayList<>();
            for (ActivityProcess activityProcess : activityProcessList) {
                List<Activity> activityList = Context.getActivityService().getAllActivities(getComponent(), activityProcess.getId(), userLogged.getId(), true);
                activities.addAll(activityList);
                Map<String, Object> map = new HashMap<>();
                map.put("activityProcess", activityProcess.getName());
                fillActivityState(map, activityList);
                maps.add(map);
            }
        }
        fillActivityState(result, activities);
        return result;
    }

    private void fillActivityState(Map data, List<Activity> activities) {
        int solvedCount = 0;
        int nonCompletionCount = 0;
        int closeToday = 0;
        int inTime = 0;
        for (Activity activity : activities) {
            if (activity.getActivityState().getName().equals(ActivityStates.Resuelta.toString()) ||
                    activity.getActivityState().getName().equals(ActivityStates.Aceptada.toString())) {
                solvedCount++;
            } else if (activity.getActivityState().getName().equals(ActivityStates.Asignada.toString())) {
                if (activity.getAccomplishDate().before(new Date())) {
                    nonCompletionCount++;
                } else if (TimestampUtil.areEquals(activity.getAccomplishDate(), new Date())) {
                    closeToday++;
                } else {
                    inTime++;
                }
            }
        }
        data.put("solvedCount", solvedCount);
        data.put("nonCompletionCount", nonCompletionCount);
        data.put("closeToday", closeToday);
        data.put("inTime", inTime);
    }
}
