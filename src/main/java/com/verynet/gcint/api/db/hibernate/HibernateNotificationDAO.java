package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.NotificationDAO;
import com.verynet.gcint.api.model.Notification;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 11/02/2017.
 */
public class HibernateNotificationDAO extends HibernateGeneralDAO implements NotificationDAO {
    public HibernateNotificationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Notification saveNotification(Notification notification) {
        currentSession().saveOrUpdate(notification);
        return notification;
    }

    @Override
    public Notification getNotification(Integer id) {
        return (Notification) currentSession().get(Notification.class, id);
    }

    @Override
    public List<Notification> getAllNotificationsFromUser(Integer userId) {
        return currentSession().createCriteria(Notification.class).createAlias("target", "u")
                .add(Restrictions.eq("u.id", userId)).addOrder(Order.desc("notificationDate")).list();
    }

    @Override
    public boolean deleteNotification(Integer id) {
        Notification notification = getNotification(id);
        if (notification != null) {
            currentSession().delete(notification);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAllNotificationsFromUser(Integer userId) {
        List<Notification> notifications = getAllNotificationsFromUser(userId);
        for (Notification notification : notifications) {
            deleteNotification(notification.getId());
        }
    }
}
