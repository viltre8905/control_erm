/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.verynet.gcint.controllers;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.*;
import com.verynet.gcint.api.util.JavaUtil;
import com.verynet.gcint.api.util.TimestampUtil;
import com.verynet.gcint.api.util.enums.ActivityStates;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.api.util.enums.Roles;
import com.verynet.gcint.api.util.report.JasperUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.lang3.StringUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author day
 */
@Controller
public class HomeController extends CommonGeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ResourceLoader resourceLoader;
    private DataSource dataSource;
    private JasperUtil jasperUtil;
    private final String REPORT_NAME = "Datos generales de la entidad.pdf";

    @Autowired
    public HomeController(ResourceLoader resourceLoader, DataSource dataSource, JasperUtil jasperUtil) {
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
        this.jasperUtil = jasperUtil;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public String getHome(ModelMap map, HttpSession session) {
        if (!getUserLogged().hasRole(Roles.ROLE_ADMIN.toString())
                && !getUserLogged().hasRole(Roles.ROLE_SUPER_ADMIN.toString())) {
            Integer processId = getProcessSelected(session);
            session.setAttribute("entity", getUserLogged().getEntity().getId());
            map.put("processSelected", processId);
            Integer entityDataId = getEntityData(session).getId();
            map.put("objectivesAffected", Context.getProcessService().getObjectivesMoreAffected(processId, entityDataId, 10));
        }

        return "home";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/supervisoryHome")
    public String getSupervisoryHome(ModelMap model, HttpSession session) {
        model.put("entityDataList", getEntityData(session).getEntities());
        return "supervisoryHome";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/supervisoryInit")
    public String getSupervisoryInit(ModelMap map, HttpSession session) {
        Integer processId = getProcessSelected(session);
        Integer entityDataId = getEntityData(session).getId();
        map.put("objectivesAffected", Context.getProcessService().getObjectivesMoreAffected(processId, entityDataId, 10));
        return "supervisoryInit";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/setProcessSelected")
    public
    @ResponseBody
    boolean setProcessSelected(@RequestParam(value = "id") Integer id, HttpSession session) {
        try {
            session.setAttribute("processSelected", id);
        } catch (Exception e) {
            logger.error(String.format("Error setting process selected(%s): %s", id, e.getMessage()));
            return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changePassword")
    public
    @ResponseBody
    Map changePassword(@RequestParam(value = "oldPassword") String oldPassword,

                       @RequestParam(value = "newPassword") String newPassword) {
        Map<String, Object> result = new HashMap<>();
        try {
            User user = getUserLogged();
            if (user != null) {
                if (!user.getPassword().equals(JavaUtil.md5(oldPassword))) {
                    result.put("success", false);
                    result.put("message", "La contraseña vieja es incorrecta");
                    return result;
                }
                user.setPassword(JavaUtil.md5(newPassword));
                Context.getUserService().saveUser(user);
            }
        } catch (Exception e) {
            logger.error(String.format("Error changing password: %s", e.getMessage()));
            result.put("success", false);
            result.put("success", "Ha ocurrido un error inesperado");
            return result;
        }
        result.put("success", true);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/data")
    public
    @ResponseBody
    Map getUserData() {
        Map<String, Object> result = new HashMap<>();
        User user = getUserLogged();
        if (user == null) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error crítico de base datos");
            return result;
        }
        result.put("success", true);
        result.put("name", user.getFirstName());
        result.put("lastName", user.getLastName());
        result.put("email", user.getEmail());
        result.put("identification", user.getIdentification());
        if (user.getOcupation() != null) {
            result.put("ocupation", user.getOcupation().getId());
        } else {
            result.put("ocupation", "-1");
        }
        result.put("pathPhoto", user.getPathPhoto());
        result.put("userName", user.getUserName());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/save")
    public
    @ResponseBody
    Map saveUser(@RequestParam(value = "name") String name,
                 @RequestParam(value = "lastName") String lastName,
                 @RequestParam(value = "userName") String userName,
                 @RequestParam(value = "identification", required = false) String identification,
                 @RequestParam(value = "ocupation") Integer ocupationId,
                 @RequestParam(value = "pathPhoto", required = false) String pathPhoto,
                 @RequestParam(value = "email", required = false) String email) {
        Map<String, Object> result = new HashMap<>();
        User user = getUserLogged();
        User anotherUser = Context.getUserService().getUser(userName);
        if (anotherUser != null && !anotherUser.getId().equals(user.getId())) {
            result.put("success", false);
            result.put("message", "EL usuario ya existe");
            return result;
        }
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setIdentification(identification);
        user.setOcupation(Context.getNomenclatureService().getOcupation(ocupationId));
        if (StringUtils.isNotBlank(pathPhoto)) {
            user.setPathPhoto(pathPhoto);
        } else {
            user.setPathPhoto(null);
        }
        user.setEmail(email);
        try {
            Context.getUserService().saveUser(user);
            result.put("success", true);
        } catch (Exception e) {
            logger.error(String.format("Error editing user: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/notification/delete")
    public
    @ResponseBody
    Map deleteNotifications() {
        Map<String, Object> result = new HashMap<>();
        try {
            Context.getNotificationService().deleteAllNotificationsFromUser(getUserLogged().getId());
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error deleting all notifications: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notification/changeState")
    public
    @ResponseBody
    Map changeNotificationState(@RequestParam(value = "id") Integer id,
                                @RequestParam(value = "read") Boolean read) {
        Map<String, Object> result = new HashMap<>();
        try {
            Notification notification = Context.getNotificationService().getNotification(id);
            notification.setRead(read);
            Context.getNotificationService().saveNotification(notification);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error changing notification state: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/entity/create_report")
    public
    @ResponseBody
    Map createReport(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Resource r = resourceLoader.getResource("classpath:/reports/entity.jasper");
        try {
            String jasperPath = r.getFile().getPath();
            HashMap<String, Object> params = new HashMap<>();
            params.put("entityId", getEntityData(session).getId());
            JasperPrint jasperPrint = jasperUtil.generateReport(jasperPath, params, dataSource.getConnection());
            jasperUtil.exportReport(jasperPrint, null, null, REPORT_NAME);
            result.put("success", true);
        } catch (IOException | SQLException | JRException e) {
            logger.warn(String.format("Error creating entity report: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/entity/export", produces = "multipart/form-data")
    public void exportReport(HttpServletResponse response) {
        try {
            jasperUtil.handleMultipartResponse(REPORT_NAME, response);
        } catch (IOException e) {
            logger.warn(String.format("Error exporting entity report: %s", e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/supervisoryHome/changeEntity")
    public
    @ResponseBody
    boolean changeEntity(@RequestParam(value = "id") Integer id, HttpSession session) {
        try {
            session.setAttribute("entity", id);
        } catch (Exception e) {
            logger.error(String.format("Error changing entity: %s", e.getMessage()));
            return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/supervisoryHome/undoEntity")
    public
    @ResponseBody
    boolean undoEntity(HttpSession session) {
        try {
            session.setAttribute("entity", getEntityData(session).getParent().getId());
        } catch (Exception e) {
            logger.error(String.format("Error undoing entity report: %s", e.getMessage()));
            return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/supervisoryHome/enterEntity")
    public
    @ResponseBody
    boolean enterEntity(@RequestParam(value = "id") Integer id, HttpSession session) {
        try {
            session.setAttribute("entity", id);
            session.setAttribute("processSelected", null);
        } catch (Exception e) {
            logger.error(String.format("Error entering on specific entity: %s", e.getMessage()));
            return false;
        }
        return true;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/supervisoryHome/activities/states")
    public
    @ResponseBody
    Map getSupervisoryActivitiesStates(@RequestParam(value = "id") Integer id, HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<Activity> activities = new ArrayList<>();
        try {
            activities.addAll(Context.getActivityService().getAllActivitiesFromEntity(id));
            fillActivityState(result, activities);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error getting supervisory activities states(Entity: %s): %s", id, e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "supervisoryHome/dashboard/efficacy")
    @ResponseBody
    public Map getSupervisoryEfficacy(@RequestParam(value = "id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("efficacy", "Ineficaz");
        try {
            result = Context.getGuideService().getAllSupervisoryGeneralDashboardData(id);
        } catch (Exception e) {
            logger.warn(String.format("Error getting supervisory efficacy(Entity: %s): %s", id, e.getMessage()));
            result.put("message", "Ha ocurrido un error inesperado");
        }
        result.put("success", true);
        result.put("id", id);
        return result;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/dashboard/efficacy")
    @ResponseBody
    public Map getEfficacy(@RequestParam(value = "val") Integer val,
                           HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put("efficacy", "Ineficaz");
        try {
            Integer processId = getProcessSelected(session);
            if (processId == null) {
                getProcesses(session, request);
                processId = getProcessSelected(session);
            }
            result = Context.getGuideService().getAllGeneralDashboardData(getEntityData(session).getId(), processId, val);
        } catch (Exception e) {
            logger.warn(String.format("Error getting efficacy: %s", e.getMessage()));
            result.put("message", "Ha ocurrido un error inesperado");
        }
        result.put("success", true);
        result.put("val", val);
        return result;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/dashboard/activities/states")
    public
    @ResponseBody
    Map getActivitiesStates(@RequestParam(value = "val") Integer val, HttpSession session, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            Integer processId = getProcessSelected(session);
            if (processId == null) {
                getProcesses(session, request);
                processId = getProcessSelected(session);
            }
            User userLogged = getUserLogged();
            List<Activity> activities = new ArrayList<>();
            String component = null;
            switch (val) {
                case 2:
                    component = Components.ec.toString();
                    break;
                case 3:
                    component = Components.er.toString();
                    break;
                case 4:
                    component = Components.ac.toString();
                    break;
                case 5:
                    component = Components.ic.toString();
                    break;
                case 6:
                    component = Components.sm.toString();
                    break;
            }
            if (userLogged.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString())) {
                if (processId != -1) {
                    List<SubProcess> subProcesses = Context.getProcessService().getAllSubProcesses(processId);
                    List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
                    for (SubProcess subProcess : subProcesses) {
                        if (component == null) {
                            activities.addAll(Context.getActivityService().getAllActivities(subProcess.getId()));
                        } else {
                            activities.addAll(Context.getActivityService().getAllActivities(component, subProcess.getId()));
                        }
                        activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(subProcess.getId()));
                    }
                    for (ActivityProcess activityProcess : activityProcessList) {
                        if (component == null) {
                            activities.addAll(Context.getActivityService().getAllActivities(activityProcess.getId()));
                        } else {
                            activities.addAll(Context.getActivityService().getAllActivities(component, activityProcess.getId()));
                        }
                    }
                } else {
                    if (component == null) {
                        activities.addAll(Context.getActivityService().getAllActivitiesFromEntity(getEntityData(session).getId()));
                    } else {
                        activities.addAll(Context.getActivityService().getAllActivities(getEntityData(session).getId(), component));
                    }
                }
            } else if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
                List<SubProcess> subProcesses = Context.getProcessService().getAllSubProcesses(processId);
                List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
                for (SubProcess subProcess : subProcesses) {
                    if (component == null) {
                        activities.addAll(Context.getActivityService().getAllActivities(subProcess.getId(), userLogged.getId(), true));

                    } else {
                        activities.addAll(Context.getActivityService().getAllActivities(component, subProcess.getId(), userLogged.getId(), true));
                    }
                    activityProcessList.addAll(Context.getActivityProcessService().getAllActivityProcess(subProcess.getId()));
                }
                for (ActivityProcess activityProcess : activityProcessList) {
                    if (component == null) {
                        activities.addAll(Context.getActivityService().getAllActivities(activityProcess.getId(), userLogged.getId(), true));

                    } else {
                        activities.addAll(Context.getActivityService().getAllActivities(component, activityProcess.getId(), userLogged.getId(), true));
                    }
                }
            }
            if (userLogged.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
                List<ActivityProcess> activityProcessList = Context.getActivityProcessService().getAllActivityProcess(processId);
                if (component == null) {
                    activities = Context.getActivityService().getAllActivities(processId, userLogged.getId(), false);
                    activities.addAll(Context.getActivityService().getAllActivities(processId, userLogged.getId(), true));
                } else {
                    activities = Context.getActivityService().getAllActivities(component, processId, userLogged.getId(), false);
                    activities.addAll(Context.getActivityService().getAllActivities(component, processId, userLogged.getId(), true));
                }
                for (ActivityProcess activityProcess : activityProcessList) {
                    if (component == null) {
                        activities = Context.getActivityService().getAllActivities(activityProcess.getId(), userLogged.getId(), false);
                        activities.addAll(Context.getActivityService().getAllActivities(activityProcess.getId(), userLogged.getId(), true));
                    } else {
                        activities = Context.getActivityService().getAllActivities(component, activityProcess.getId(), userLogged.getId(), false);
                        activities.addAll(Context.getActivityService().getAllActivities(component, activityProcess.getId(), userLogged.getId(), true));
                    }
                }

            }
            fillActivityState(result, activities);
            result.put("success", true);
        } catch (Exception e) {
            logger.warn(String.format("Error getting activity states: %s", e.getMessage()));
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dashboard/risk/level")
    public
    @ResponseBody
    Map getLevel(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        Integer processId = getProcessSelected(session);
        try {
            if (processId == -1) {
                result = Context.getRiskService().getLevelPercent(processId, getEntityData(session).getId());
            } else {
                result = Context.getRiskService().getLevelPercent(processId, null);
            }
            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "Ha ocurrido un error inesperado");
        }
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
