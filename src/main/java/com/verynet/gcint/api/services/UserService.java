package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.UserDAO;
import com.verynet.gcint.api.model.Notification;
import com.verynet.gcint.api.model.User;

import java.util.List;

/**
 * Created by day on 28/07/2016.
 */
public interface UserService {
    public void setUserDAO(UserDAO dao);

    public User saveUser(User user);

    public User getUser(String userName);

    public User getUser(Integer id);

    public List<User> getUsersByRoleValue(Integer entityId, String value);

    public List<User> getUsersWithRoleValues(Integer entityId, String... values);

    public List<User> getUsersWithOutRoleValues(Integer entityId, String... values);

    public List<User> getAllUsers();

    public List<User> getAllUsers(Integer entityId);

    public boolean deleteUser(String userName);
}
