package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.Activity;
import com.verynet.gcint.api.model.ActivityState;
import com.verynet.gcint.api.model.Evidence;
import com.verynet.gcint.api.model.NegativeAnswer;

import java.util.List;

/**
 * Created by day on 18/09/2016.
 */
public interface ActivityDAO {
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

    public List<Activity> getEntityMeasuresPlan(Integer entityId);

    public void deleteAllRiskActivities(Integer entityId);
}
