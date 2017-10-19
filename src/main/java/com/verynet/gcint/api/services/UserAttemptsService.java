package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.UserAttemptsDAO;
import com.verynet.gcint.api.model.UserAttempts;

/**
 * Created by day on 05/04/2017.
 */
public interface UserAttemptsService {

    public void setUserAttemptsDAO(UserAttemptsDAO dao);

    public UserAttempts saveUserAttempts(com.verynet.gcint.api.model.UserAttempts userAttempts);

    public UserAttempts getUserAttempts(String userName);

    public void updateFailAttempts(String userName);

    public void resetFailAttempts(String userName);


}
