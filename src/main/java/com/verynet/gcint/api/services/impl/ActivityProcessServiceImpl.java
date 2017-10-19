package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.ActivityProcessDAO;
import com.verynet.gcint.api.model.ActivityProcess;
import com.verynet.gcint.api.services.ActivityProcessService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 05/09/2017.
 */
@Transactional
public class ActivityProcessServiceImpl implements ActivityProcessService {
    private ActivityProcessDAO dao;

    @Override
    public void setActivityProcessDAO(ActivityProcessDAO dao) {
        this.dao = dao;
    }

    @Override
    public ActivityProcess saveActivityProcess(ActivityProcess activityProcess) {
        return dao.saveActivityProcess(activityProcess);
    }

    @Override
    public boolean deleteActivityProcess(Integer id) {
        return dao.deleteActivityProcess(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityProcess> getAllActivityProcess(Integer generalProcessId) {
        return dao.getAllActivityProcess(generalProcessId);
    }

    @Override
    public List<ActivityProcess> getAllActivityProcess(String userName) {
        return dao.getAllActivityProcess(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public ActivityProcess getActivityProcess(Integer id) {
        return dao.getActivityProcess(id);
    }
}
