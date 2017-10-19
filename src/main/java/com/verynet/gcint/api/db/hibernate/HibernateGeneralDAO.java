package com.verynet.gcint.api.db.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by day on 28/07/2016.
 */
public abstract class HibernateGeneralDAO {
    protected SessionFactory sessionFactory;

    public HibernateGeneralDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
