package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.ActivityProcessDAO;
import com.verynet.gcint.api.model.ActivityProcess;

import java.util.List;

/**
 * Created by day on 05/09/2017.
 */
public interface ActivityProcessService {
    public void setActivityProcessDAO(ActivityProcessDAO dao);

    public ActivityProcess saveActivityProcess(ActivityProcess activityProcess);

    public boolean deleteActivityProcess(Integer id);

    public List<ActivityProcess> getAllActivityProcess(Integer generalProcessId);

    public List<ActivityProcess>getAllActivityProcess(String userName);

    public ActivityProcess getActivityProcess(Integer id);
}
