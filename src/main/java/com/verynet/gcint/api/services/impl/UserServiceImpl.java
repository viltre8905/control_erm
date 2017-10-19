package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.UserDAO;
import com.verynet.gcint.api.model.Notification;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.services.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 28/07/2016.
 */
@Transactional
public class UserServiceImpl implements UserService {
    private UserDAO dao;

    @Override
    public void setUserDAO(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User saveUser(User user) {
        User savedUser = dao.saveUser(user);
        return savedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String userName) {
        return dao.getUser(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Integer id) {
        return dao.getUser(id);
    }

    @Override
    public List<User> getUsersByRoleValue(Integer entityId, String value) {
        return dao.getUsersByRoleValue(entityId, value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersWithRoleValues(Integer entityId, String... values) {
        return dao.getUsersWithRoleValues(entityId, values);
    }

    @Override
    public List<User> getUsersWithOutRoleValues(Integer entityId, String... values) {
        return dao.getUsersWithOutRoleValues(entityId, values);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public List<User> getAllUsers(Integer entityId) {
        return dao.getAllUsers(entityId);
    }

    @Override
    public boolean deleteUser(String userName) {
        return dao.deleteUser(userName);
    }

}
