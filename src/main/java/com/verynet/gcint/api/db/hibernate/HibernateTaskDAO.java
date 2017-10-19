package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.TaskDAO;
import com.verynet.gcint.api.model.Task;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by day on 16/02/2017.
 */
public class HibernateTaskDAO extends HibernateGeneralDAO implements TaskDAO {
    public HibernateTaskDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Task saveTask(Task task) {
        currentSession().saveOrUpdate(task);
        return task;
    }

    @Override
    public Task getTask(Integer id) {
        return (Task) currentSession().get(Task.class, id);
    }

    @Override
    public List<Task> getAllTasks() {
        return currentSession().createCriteria(Task.class).list();
    }
}
