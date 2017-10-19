package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.NotificationDAO;
import com.verynet.gcint.api.model.Notification;
import com.verynet.gcint.api.services.NotificationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 11/02/2017.
 */
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private NotificationDAO dao;

    @Override
    public void setNotificationDAO(NotificationDAO dao) {
        this.dao = dao;
    }

    @Override
    public Notification saveNotification(Notification notification) {
        return dao.saveNotification(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public Notification getNotification(Integer id) {
        return dao.getNotification(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getAllNotificationsFromUser(Integer userId) {
        return dao.getAllNotificationsFromUser(userId);
    }

    @Override
    public void deleteAllNotificationsFromUser(Integer userId) {
        dao.deleteAllNotificationsFromUser(userId);
    }
}
