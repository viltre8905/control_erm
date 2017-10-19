package com.verynet.gcint.controllers;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.EntityData;
import com.verynet.gcint.api.model.Notification;
import com.verynet.gcint.api.model.Ocupation;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.util.enums.Roles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 31/08/2016.
 */
@Controller
public class GeneralController {
    @ModelAttribute("userLogged")
    public User getUserLogged() {
        return Context.getAuthenticatedUser();
    }

    @ModelAttribute("profileOcupations")
    public List<Ocupation> getProfileOcupations() {
        return Context.getNomenclatureService().getAllOcupations();
    }

    @ModelAttribute("notifications")
    public List<Notification> getNotifications() {
        List<Notification> result = new ArrayList<>();
        User userLogged = getUserLogged();
        if (userLogged != null) {
            result = Context.getNotificationService().getAllNotificationsFromUser(userLogged.getId());
        }
        return result;
    }
}
