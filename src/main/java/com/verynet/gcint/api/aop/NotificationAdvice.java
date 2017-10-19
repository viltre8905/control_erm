package com.verynet.gcint.api.aop;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Activity;
import com.verynet.gcint.api.model.Notification;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.enums.ActivityStates;
import com.verynet.gcint.api.util.enums.Components;
import com.verynet.gcint.api.util.enums.NotificationTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * Created by day on 10/02/2017.
 */
public class NotificationAdvice implements AfterReturningAdvice {
    protected static final Logger logger = LoggerFactory.getLogger(NotificationAdvice.class);

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        Activity activity = (Activity) returnValue;
        Notification notification = new Notification();
        User user = null;
        User sender = null;
        boolean responsible = false;
        try {
            if (!(activity.getActivityState().getName().equals(ActivityStates.Asignada.toString()) && ((activity.getDeficiency() != null
                    && activity.getDeficiency().getActionControlInform() == null) || activity.getRisk() != null))) {
                if (activity.getActivityState().getName().equals(ActivityStates.Asignada.toString())) {
                    notification.setType(NotificationTypes.warning.toString());
                    notification.setTitle("Nueva Actividad");
                    notification.setBody(String.format("Una nueva actividad ha sido<br>asignada en el componente<br>%s.<br><br><b>Proceso, Sub-Proceso o Actividad:</b><br>%s.", activity.getComponent().getName(), activity.getProcess().getName()));
                } else if (activity.getActivityState().getName().equals(ActivityStates.Rechazada.toString())) {
                    notification.setType(NotificationTypes.danger.toString());
                    notification.setTitle("Actividad Rechazada");
                    notification.setBody(String.format("La solución propuesta a una actividad<br> en el componente %s<br> ha sido rechazada.<br><br><b>Proceso, Sub-Proceso o Actividad:</b><br>%s.", activity.getComponent().getName(), activity.getProcess().getName()));
                } else {
                    notification.setType(NotificationTypes.complete.toString());
                    if (activity.getActivityState().getName().equals(ActivityStates.Aceptada.toString())) {
                        notification.setTitle("Solución Aceptada");
                        notification.setBody(String.format("Se ha aceptado la solución propuesta<br> de una actividad en el componente<br>%s.<br><br><b>Proceso, Sub-Proceso o Actividad:</b><br>%s.", activity.getComponent().getName(), activity.getProcess().getName()));
                    } else {
                        notification.setTitle("Actividad Resuelta");
                        notification.setBody(String.format("Se ha realizado una actividad<br>en el componente <br>%s.<br><br><b>Proceso, Sub-Proceso o Actividad:</b><br>%s.", activity.getComponent().getName(), activity.getProcess().getName()));
                        user = activity.getResponsible();
                        sender = activity.getExecutor();
                        responsible = true;
                    }
                }
                if (user == null) {
                    user = activity.getExecutor();
                    sender = activity.getResponsible();
                }

                if (activity.getComponent().getCode().equals(Components.ec.toString())) {
                    notification.setPath("/control-environment/activity/activities");
                } else if (activity.getComponent().getCode().equals(Components.ac.toString())) {
                    notification.setPath("/control-activity/activity/activities");
                } else if (activity.getComponent().getCode().equals(Components.er.toString())) {
                    if (responsible) {
                        notification.setPath(String.format("/risk/management/activities?id=%s", activity.getRisk().getId()));
                    } else {
                        notification.setPath("/risk/activity/activities");
                    }
                } else if (activity.getComponent().getCode().equals(Components.ic.toString())) {
                    notification.setPath("/info-and-com/activity/activities");
                } else {
                    if (responsible) {
                        notification.setPath(String.format("/monitoring/committee/report/deficiency/activity/activities?id=%s", activity.getDeficiency().getId()));
                    } else {
                        notification.setPath("/monitoring/activity/activities");
                    }
                }

                notification.setSender(sender);
                notification.setNotificationDate(new Date());
                notification.setRead(false);
                notification.setTarget(user);
                Context.getNotificationService().saveNotification(notification);
            }
        } catch (Exception e) {
            logger.warn(String.format("Error sending notification to user: %s", e.getMessage()));
        }
    }
}
