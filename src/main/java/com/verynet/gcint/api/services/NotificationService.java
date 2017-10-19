package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.NotificationDAO;
import com.verynet.gcint.api.model.Notification;

import java.util.List;

/**
 * Created by day on 11/02/2017.
 */
public interface NotificationService {
    public void setNotificationDAO(NotificationDAO dao);

    public Notification saveNotification(Notification notification);

    public Notification getNotification(Integer id);

    public List<Notification> getAllNotificationsFromUser(Integer userId);

    public void deleteAllNotificationsFromUser(Integer userId);
}
