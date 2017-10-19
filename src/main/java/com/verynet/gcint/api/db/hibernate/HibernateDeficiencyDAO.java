package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.DeficiencyDAO;
import com.verynet.gcint.api.model.Deficiency;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by day on 02/10/2016.
 */
public class HibernateDeficiencyDAO extends HibernateGeneralDAO implements DeficiencyDAO {
    public HibernateDeficiencyDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Deficiency saveDeficiency(Deficiency deficiency) {
        currentSession().saveOrUpdate(deficiency);
        return deficiency;
    }

    @Override
    public Deficiency getDeficiency(Integer id) {
        return (Deficiency) currentSession().get(Deficiency.class, id);
    }

    @Override
    public List<Deficiency> getAllDeficiencies() {
        return currentSession().createCriteria(Deficiency.class).list();
    }

    @Override
    public boolean deleteDeficiency(Integer id) {
        Deficiency deficiency = getDeficiency(id);
        if (deficiency != null) {
            currentSession().delete(deficiency);
            return true;
        }
        return false;
    }
}
