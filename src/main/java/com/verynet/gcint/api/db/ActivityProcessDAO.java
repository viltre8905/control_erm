package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.ActivityProcess;

import java.util.List;

/**
 * Created by day on 05/09/2017.
 */
public interface ActivityProcessDAO {

    public ActivityProcess saveActivityProcess(ActivityProcess activityProcess);

    public boolean deleteActivityProcess(Integer id);

    public List<ActivityProcess> getAllActivityProcess(Integer generalProcessId);

    public List<ActivityProcess>getAllActivityProcess(String userName);

    public ActivityProcess getActivityProcess(Integer id);
}
