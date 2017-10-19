package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.ACInformDAO;
import com.verynet.gcint.api.model.ActionControlInform;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 01/10/2016.
 */
public class HibernateACInformDAO extends HibernateGeneralDAO implements ACInformDAO {
    public HibernateACInformDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public ActionControlInform saveActionControlInform(ActionControlInform actionControlInform) {
        currentSession().saveOrUpdate(actionControlInform);
        return actionControlInform;
    }

    @Override
    public ActionControlInform getActionControlInform(Integer id) {
        return (ActionControlInform) currentSession().get(ActionControlInform.class, id);
    }

    @Override
    public List<ActionControlInform> getAllActionControlInforms(Integer entityId) {
        return currentSession().createCriteria(ActionControlInform.class, "a")
                .createAlias("a.entity", "e").add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public boolean deleteActionControlInform(Integer id) {
        ActionControlInform actionControlInform = getActionControlInform(id);
        if (actionControlInform != null) {
            currentSession().delete(actionControlInform);
            return true;
        }
        return false;
    }


    @Override
    public void deleteAllActionControlInform(Integer entityId) {
        List<ActionControlInform> actionControlInformList = getAllActionControlInforms(entityId);
        for (ActionControlInform actionControlInform : actionControlInformList) {
            currentSession().delete(actionControlInform);
        }
    }
}
