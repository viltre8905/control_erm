package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.TaskDAO;
import com.verynet.gcint.api.model.Task;
import com.verynet.gcint.api.services.TaskService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 16/02/2017.
 */
@Transactional
public class TaskServiceImpl implements TaskService {
    private TaskDAO dao;

    @Override
    public void setTaskDAO(TaskDAO dao) {
        this.dao = dao;
    }

    @Override
    public Task saveTask(Task task) {
        return dao.saveTask(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTask(Integer id) {
        return dao.getTask(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllTasks() {
        return dao.getAllTasks();
    }
}
