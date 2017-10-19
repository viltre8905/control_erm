package com.verynet.gcint.controllers;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.EntityData;
import com.verynet.gcint.api.model.GeneralProcess;
import com.verynet.gcint.api.model.StrategicObjective;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.enums.Roles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 15/09/2016.
 */
@Controller
public class CommonGeneralController extends GeneralController {

    @ModelAttribute(value = "entityData")
    public EntityData getEntityData(HttpSession session) {
        User loggedUser = getUserLogged();
        EntityData entityData = null;
        if (loggedUser != null && !loggedUser.hasRole(Roles.ROLE_SUPER_ADMIN.toString())) {
            if (loggedUser.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString())) {
                if (session.getAttribute("entity") == null) {
                    session.setAttribute("entity", loggedUser.getEntity().getId());
                }
                entityData = Context.getEntityService().getLightWeightEntityData((Integer) session.getAttribute("entity"));
            } else {
                entityData = Context.getEntityService().getLightWeightEntityData(loggedUser.getEntity().getId());
            }
        }
        return entityData;
    }

    @ModelAttribute(value = "strategic_objectives")
    public List<StrategicObjective> getStrategicObjectives(HttpSession session) {
        List<StrategicObjective> result = new ArrayList<>();
        EntityData entityData = getEntityData(session);
        if (entityData != null) {
            result = Context.getStrategicObjectiveService().getAllStrategicObjective(entityData.getId());
        }
        return result;
    }


    @ModelAttribute(value = "processes")
    public List<GeneralProcess> getProcesses(HttpSession session, HttpServletRequest request) {
        List<GeneralProcess> result = new ArrayList<>();
        User userLogged = getUserLogged();
        if (userLogged != null) {
            if (userLogged.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString())) {
                result.addAll(Context.getProcessService().getAllProcess(getEntityData(session).getId()));
            } else if (userLogged.hasRole(Roles.ROLE_PROCESS_SUPERVISORY.toString())) {
                result.addAll(Context.getProcessService().getAllProcesses(getUserLogged().getUserName(), true));
            }
            if (userLogged.hasRole(Roles.ROLE_SUBPROCESS_SUPERVISORY.toString())) {
                result.addAll(Context.getProcessService().getAllSubProcesses(getUserLogged().getUserName(), true));
            }
            if (userLogged.hasRole(Roles.ROLE_EXECUTER.toString())) {
                result.addAll(Context.getProcessService().getAllSubProcesses(getUserLogged().getUserName(), false));
            }
            if (userLogged.hasRole(Roles.ROLE_ACTIVITY_RESPONSIBLE.toString())) {
                result.addAll(Context.getActivityProcessService().getAllActivityProcess(getUserLogged().getUserName()));
            }
        }
        if (userLogged != null && userLogged.hasRole(Roles.ROLE_GENERAL_SUPERVISORY.toString())) {
            if (session.getAttribute("processSelected") == null && result.size() > 0) {
                if (!request.getRequestURI().contains("dashboard") && !request.getRequestURI().contains("supervisoryInit")) {
                    session.setAttribute("processSelected", result.get(0).getId());
                } else {
                    session.setAttribute("processSelected", -1);
                }
            } else if (session.getAttribute("processSelected") != null && session.getAttribute("processSelected").equals(-1) && !request.getRequestURI().contains("dashboard") && !request.getRequestURI().contains("supervisoryInit")) {
                session.setAttribute("processSelected", result.get(0).getId());
            }
        } else {
            if (session.getAttribute("processSelected") == null && result.size() > 0) {
                session.setAttribute("processSelected", result.get(0).getId());
            }
        }
        return result;
    }

    public Integer getProcessSelected(HttpSession session) {
        return (Integer) session.getAttribute("processSelected");
    }

    public GeneralProcess getObjectProcessSelected(HttpSession session) {
        Integer processSelected = getProcessSelected(session);
        if (processSelected != null) {
            return Context.getProcessService().getGeneralProcess(processSelected);
        }
        return null;
    }

}
