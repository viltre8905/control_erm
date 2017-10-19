package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.Task;

import java.util.List;

/**
 * Created by day on 16/02/2017.
 */
public interface TaskDAO {
    public Task saveTask(Task task);

    public Task getTask(Integer id);

    public List<Task> getAllTasks();
}
