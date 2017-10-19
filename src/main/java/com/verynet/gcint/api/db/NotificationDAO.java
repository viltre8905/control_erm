package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.Notification;

import java.util.List;

/**
 * Created by day on 11/02/2017.
 */
public interface NotificationDAO {
    public Notification saveNotification(Notification notification);

    public Notification getNotification(Integer id);

    public List<Notification> getAllNotificationsFromUser(Integer userId);

    public boolean deleteNotification(Integer id);

    public void deleteAllNotificationsFromUser(Integer userId);
}
