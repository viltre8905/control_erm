package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.db.ActivityDAO;
import com.verynet.gcint.api.model.Activity;
import com.verynet.gcint.api.model.ActivityState;
import com.verynet.gcint.api.model.Evidence;
import com.verynet.gcint.api.model.NegativeAnswer;
import com.verynet.gcint.api.services.ActivityService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by day on 18/09/2016.
 */
@Transactional
public class ActivityServiceImpl implements ActivityService {
    private ActivityDAO dao;

    @Override
    public void setActivityDAO(ActivityDAO dao) {
        this.dao = dao;
    }

    @Override
    public Activity saveActivity(Activity activity) {
        return dao.saveActivity(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public Activity getActivity(Integer id) {
        return dao.getActivity(id);
    }

    @Override
    public List<Activity> getAllActivities() {
        return dao.getAllActivities();
    }

    @Override
    public List<Activity> getAllActivitiesFromEntity(Integer entityId) {
        return dao.getAllActivitiesFromEntity(entityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> getAllActivities(String componentCode, Integer processId) {
        return dao.getAllActivities(componentCode, processId);
    }

    @Override
    public List<Activity> getAllActivities(Integer entityId, String componentCode) {
        return dao.getAllActivities(entityId, componentCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Activity> getAllActivities(String componentCode, Integer processId, Integer userId, boolean isResponsible) {
        return dao.getAllActivities(componentCode, processId, userId, isResponsible);
    }

    @Override
    public List<Activity> getAllActivities(Integer processId, Integer userId, boolean isResponsible) {
        return dao.getAllActivities(processId, userId, isResponsible);
    }

    @Override
    public List<Activity> getAllActivities(Integer processId) {
        return dao.getAllActivities(processId);
    }

    @Override
    public boolean deleteActivity(Integer id) {
        return dao.deleteActivity(id);
    }

    @Override
    public boolean deleteEvidence(Integer id) {
        return dao.deleteEvidence(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityState getActivityState(String name) {
        return dao.getActivityState(name);
    }

    @Override
    public Evidence saveEvidence(Evidence evidence) {
        return dao.saveEvidence(evidence);
    }

    @Override
    @Transactional(readOnly = true)
    public Evidence getEvidence(Integer id) {
        return dao.getEvidence(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evidence> getAllEvidences() {
        return dao.getAllEvidences();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Object, Object> getEntityMeasuresPlan(Integer entityId) {
        Map<Object, Object> result = new HashMap<>();
        List<Activity> activities = dao.getEntityMeasuresPlan(entityId);
        for (Activity activity : activities) {
            Map<Object, List<Activity>> activitiesMap = new HashMap<>();
            Map<Object, Object> deficiencyMap = new HashMap<>();
            List<Activity> activityList = new ArrayList<>();
            String componentName = activity.getComponent().getName();
            String deficiency = activity.getDeficiency() != null ? activity.getDeficiency().getBody().toString() : "";
            String processName = activity.getProcess().getName();
            if (result.get(processName) == null) {
                activityList.add(activity);
                activitiesMap.put(deficiency, activityList);
                deficiencyMap.put(componentName, activitiesMap);
                result.put(processName, deficiencyMap);
            } else if (((Map<Object, Object>) result.get(processName)).get(componentName) == null) {
                activityList.add(activity);
                activitiesMap.put(deficiency, activityList);
                ((Map<Object, Object>) result.get(processName)).put(componentName, activitiesMap);
            } else {
                activitiesMap = (Map<Object, List<Activity>>) ((Map<Object, Object>) result.get(processName)).get(componentName);
                if (activitiesMap.get(deficiency) == null) {
                    activityList.add(activity);
                    activitiesMap.put(deficiency, activityList);
                } else {
                    activitiesMap.get(deficiency).add(activity);
                }
            }
        }
        return result;
    }

    @Override
    public void deleteAllRiskActivities(Integer entityId) {
        dao.deleteAllRiskActivities(entityId);
    }
}
