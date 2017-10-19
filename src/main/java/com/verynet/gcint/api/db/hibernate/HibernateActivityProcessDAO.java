package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.ActivityProcessDAO;
import com.verynet.gcint.api.model.ActivityProcess;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 05/09/2017.
 */
public class HibernateActivityProcessDAO extends HibernateGeneralDAO implements ActivityProcessDAO {

    public HibernateActivityProcessDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public ActivityProcess saveActivityProcess(ActivityProcess activityProcess) {
        currentSession().saveOrUpdate(activityProcess);
        return activityProcess;
    }

    @Override
    public boolean deleteActivityProcess(Integer id) {
        ActivityProcess activityProcess = getActivityProcess(id);
        if (activityProcess != null) {
            currentSession().delete(activityProcess);
            return true;
        }
        return false;
    }

    @Override
    public List<ActivityProcess> getAllActivityProcess(Integer generalProcessId) {
        return currentSession().createCriteria(ActivityProcess.class)
                .createAlias("parent", "p").add(Restrictions.eq("p.id", generalProcessId)).list();
    }

    @Override
    public List<ActivityProcess> getAllActivityProcess(String userName) {
        return currentSession().createCriteria(ActivityProcess.class, "ap")
                .createAlias("ap.responsible", "r").add(Restrictions.eq("r.userName", userName)).list();
    }

    @Override
    public ActivityProcess getActivityProcess(Integer id) {
        return (ActivityProcess) currentSession().get(ActivityProcess.class, id);
    }
}
