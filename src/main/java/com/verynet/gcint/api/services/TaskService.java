package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.TaskDAO;
import com.verynet.gcint.api.model.Task;

import java.util.List;

/**
 * Created by day on 16/02/2017.
 */
public interface TaskService {
    public void setTaskDAO(TaskDAO dao);

    public Task saveTask(Task task);

    public Task getTask(Integer id);

    public List<Task> getAllTasks();
}
