package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.StrategicObjectiveDAO;
import com.verynet.gcint.api.model.StrategicObjective;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 24/02/2017.
 */
public class HibernateStrategicObjectiveDAO extends HibernateGeneralDAO implements StrategicObjectiveDAO {
    public HibernateStrategicObjectiveDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public StrategicObjective saveStrategicObjective(StrategicObjective strategicObjective) {
        currentSession().saveOrUpdate(strategicObjective);
        return strategicObjective;
    }

    @Override
    public StrategicObjective getStrategicObjective(Integer id) {
        return (StrategicObjective) currentSession().get(StrategicObjective.class, id);
    }

    @Override
    public List<StrategicObjective> getAllStrategicObjective(Integer entityId) {
        return currentSession().createCriteria(StrategicObjective.class)
                .createAlias("entity", "e").add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public boolean deleteStrategicObjective(Integer id) {
        StrategicObjective strategicObjective = getStrategicObjective(id);
        if (strategicObjective != null) {
            currentSession().delete(strategicObjective);
            return true;
        }
        return false;
    }
}
