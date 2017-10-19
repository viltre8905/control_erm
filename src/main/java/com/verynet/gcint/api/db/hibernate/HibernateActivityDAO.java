package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.ActivityDAO;
import com.verynet.gcint.api.model.Activity;
import com.verynet.gcint.api.model.ActivityState;
import com.verynet.gcint.api.model.Evidence;
import com.verynet.gcint.api.model.NegativeAnswer;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 18/09/2016.
 */
public class HibernateActivityDAO extends HibernateGeneralDAO implements ActivityDAO {
    public HibernateActivityDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Activity saveActivity(Activity activity) {
        currentSession().saveOrUpdate(activity);
        return activity;
    }

    @Override
    public Activity getActivity(Integer id) {
        return (Activity) currentSession().get(Activity.class, id);
    }

    @Override
    public List<Activity> getAllActivities() {
        return currentSession().createCriteria(Activity.class).list();
    }

    @Override
    public List<Activity> getAllActivitiesFromEntity(Integer entityId) {
        return currentSession().createCriteria(Activity.class).createAlias("process", "p")
                .createAlias("p.entity", "e").add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public List<Activity> getAllActivities(String componentCode, Integer processId) {
        return currentSession().createCriteria(Activity.class).createAlias("process", "p")
                .createAlias("component", "c").add(Restrictions.and(Restrictions.eq("p.id", processId),
                        Restrictions.eq("c.code", componentCode))).list();
    }

    @Override
    public List<Activity> getAllActivities(Integer entityId, String componentCode) {
        return currentSession().createCriteria(Activity.class).createAlias("component", "c")
                .createAlias("process", "p").createAlias("p.entity", "e").add(Restrictions.and(
                        Restrictions.eq("c.code", componentCode), Restrictions.eq("e.id", entityId)
                )).list();
    }

    @Override
    public List<Activity> getAllActivities(String componentCode, Integer processId, Integer userId, boolean isResponsible) {
        if (isResponsible) {
            return currentSession().createCriteria(Activity.class).createAlias("process", "p").createAlias("responsible", "r")
                    .createAlias("component", "c").add(Restrictions.and(Restrictions.eq("p.id", processId),
                            Restrictions.eq("r.id", userId), Restrictions.eq("c.code", componentCode))).list();
        } else {
            return currentSession().createCriteria(Activity.class).createAlias("process", "p").createAlias("executor", "ex")
                    .createAlias("component", "c").add(Restrictions.and(Restrictions.eq("p.id", processId),
                            Restrictions.eq("ex.id", userId), Restrictions.eq("c.code", componentCode))).list();
        }
    }

    @Override
    public List<Activity> getAllActivities(Integer processId, Integer userId, boolean isResponsible) {
        if (isResponsible) {
            return currentSession().createCriteria(Activity.class).createAlias("process", "p").createAlias("responsible", "r").add(Restrictions.and(Restrictions.eq("p.id", processId),
                    Restrictions.eq("r.id", userId))).list();
        } else {
            return currentSession().createCriteria(Activity.class).createAlias("process", "p").createAlias("executor", "ex")
                    .add(Restrictions.and(Restrictions.eq("p.id", processId),
                            Restrictions.eq("ex.id", userId))).list();
        }
    }

    @Override
    public List<Activity> getAllActivities(Integer processId) {
        return currentSession().createCriteria(Activity.class).createAlias("process", "p")
                .add(Restrictions.eq("p.id", processId)).list();
    }

    @Override
    public boolean deleteActivity(Integer id) {
        Activity activity = getActivity(id);
        if (activity != null) {
            currentSession().delete(activity);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEvidence(Integer id) {
        Activity activity = getActivity(id);
        if (activity != null && activity.getEvidence() != null) {
            Evidence evidence = getEvidence(activity.getEvidence().getId());
            activity.setEvidence(null);
            saveActivity(activity);
            if (evidence != null) {
                currentSession().delete(evidence);
                return true;
            }
        }
        return false;
    }

    @Override
    public ActivityState getActivityState(String name) {
        List<ActivityState> activityStates = currentSession().createCriteria(ActivityState.class).add(Restrictions.eq("name", name)).list();
        return activityStates.size() > 0 ? activityStates.get(0) : null;
    }

    @Override
    public Evidence saveEvidence(Evidence evidence) {
        currentSession().saveOrUpdate(evidence);
        return evidence;
    }

    @Override
    public Evidence getEvidence(Integer id) {
        return (Evidence) currentSession().get(Evidence.class, id);
    }

    @Override
    public List<Evidence> getAllEvidences() {
        return currentSession().createCriteria(Evidence.class).list();
    }

    @Override
    public List<Activity> getEntityMeasuresPlan(Integer entityId) {
        return currentSession().createCriteria(Activity.class).createAlias("process", "p")
                .createAlias("p.entity", "e").add(Restrictions.and(
                        Restrictions.eq("e.id", entityId), Restrictions.isNull("risk"))).list();
    }

    @Override
    public void deleteAllRiskActivities(Integer entityId) {
        List<Activity> activities = currentSession().createCriteria(Activity.class).createAlias("process", "p")
                .createAlias("p.entity", "e").add(Restrictions.and(Restrictions.eq("e.id", entityId), Restrictions.isNotNull("risk"))).list();
        for (Activity activity : activities) {
            currentSession().delete(activity);
        }
    }
}
