package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.ReunionDAO;
import com.verynet.gcint.api.model.Reunion;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 03/10/2016.
 */
public class HibernateReunionDAO extends HibernateGeneralDAO implements ReunionDAO {
    public HibernateReunionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Reunion saveReunion(Reunion reunion) {
        currentSession().saveOrUpdate(reunion);
        return reunion;
    }

    @Override
    public Reunion getReunion(Integer id) {
        return (Reunion) currentSession().get(Reunion.class, id);
    }

    @Override
    public List<Reunion> getAllReunions(Integer entityId) {
        return currentSession().createCriteria(Reunion.class)
                .createAlias("entity", "e").add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public boolean deleteReunion(Integer id) {
        Reunion reunion = getReunion(id);
        if (reunion != null) {
            currentSession().delete(reunion);
            return true;
        }
        return false;
    }
}
