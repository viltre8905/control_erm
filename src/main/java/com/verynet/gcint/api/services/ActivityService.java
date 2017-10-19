package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.ActivityDAO;
import com.verynet.gcint.api.model.Activity;
import com.verynet.gcint.api.model.ActivityState;
import com.verynet.gcint.api.model.Evidence;

import java.util.List;
import java.util.Map;

/**
 * Created by day on 18/09/2016.
 */
public interface ActivityService {
    public void setActivityDAO(ActivityDAO dao);

    public Activity saveActivity(Activity activity);

    public Activity getActivity(Integer id);

    public List<Activity> getAllActivities();

    public List<Activity> getAllActivitiesFromEntity(Integer entityId);

    public List<Activity> getAllActivities(String componentCode, Integer processId);

    public List<Activity> getAllActivities(Integer entityId, String componentCode);

    public List<Activity> getAllActivities(Integer processId);

    public List<Activity> getAllActivities(String componentCode, Integer processId, Integer userId, boolean isResponsible);

    public List<Activity> getAllActivities(Integer processId, Integer userId, boolean isResponsible);

    public boolean deleteActivity(Integer id);

    public boolean deleteEvidence(Integer id);

    public ActivityState getActivityState(String name);

    public Evidence saveEvidence(Evidence evidence);

    public Evidence getEvidence(Integer id);

    public List<Evidence> getAllEvidences();

    public Map<Object, Object> getEntityMeasuresPlan(Integer entityId);

    public void deleteAllRiskActivities(Integer entityId);
}
