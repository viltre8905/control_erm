package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.db.UserAttemptsDAO;
import com.verynet.gcint.api.model.User;
import com.verynet.gcint.api.model.UserAttempts;
import com.verynet.gcint.api.services.UserAttemptsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by day on 05/04/2017.
 */
@Transactional
public class UserAttemptsServiceImpl implements UserAttemptsService {
    private UserAttemptsDAO dao;
    private final int MAX_FAILS = 10;

    @Override
    public void setUserAttemptsDAO(UserAttemptsDAO dao) {
        this.dao = dao;
    }

    @Override
    public UserAttempts saveUserAttempts(UserAttempts userAttempts) {
        return dao.saveUserAttempts(userAttempts);
    }

    @Override
    @Transactional(readOnly = true)
    public UserAttempts getUserAttempts(String userName) {
        return dao.getUserAttempts(userName);
    }

    @Override
    public void updateFailAttempts(String userName) {
        User user = Context.getUserService().getUser(userName);
        if (user != null) {
            UserAttempts userAttempts = getUserAttempts(userName);
            if (userAttempts == null) {
                userAttempts = new UserAttempts();
                userAttempts.setUser(user);
                userAttempts.setAttempts(1);
            } else {
                if (userAttempts.getAttempts() + 1 < MAX_FAILS) {
                    userAttempts.setAttempts(userAttempts.getAttempts() + 1);
                } else {
                    user.setAccountNonLocked(false);
                    Context.getUserService().saveUser(user);
                }
            }
            userAttempts.setLastModified(new Date());
            saveUserAttempts(userAttempts);
        }
    }

    @Override
    public void resetFailAttempts(String userName) {
        User user = Context.getUserService().getUser(userName);
        if (user != null) {
            UserAttempts userAttempts = getUserAttempts(userName);
            if (userAttempts == null) {
                userAttempts = new UserAttempts();
            }
            userAttempts.setAttempts(0);
            userAttempts.setLastModified(new Date());
            saveUserAttempts(userAttempts);
        }
    }
}
