package com.verynet.gcint.api.db;

import com.verynet.gcint.api.model.UserAttempts;

/**
 * Created by day on 05/04/2017.
 */
public interface UserAttemptsDAO {
    public UserAttempts saveUserAttempts(UserAttempts userAttempts);

    public UserAttempts getUserAttempts(String userName);
}
