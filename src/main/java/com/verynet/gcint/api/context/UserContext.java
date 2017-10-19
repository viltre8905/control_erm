package com.verynet.gcint.api.context;

import com.verynet.gcint.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by day on 28/07/2016.
 */
public class UserContext {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static UserContext instance;

    private UserContext() {
        logger.debug("Instantiating user context");
    }

    public static UserContext getInstance() {
        if (instance == null) {
            instance = new UserContext();
        }
        return instance;
    }

    public User getAuthenticatedUser() {
        String userName = null;
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
            }
            logger.debug(String.format("Logged user name: '%s'", userName));
            user = Context.getUserService().getUser(userName);
            logger.debug(String.format("Logged user name: '%s'", user));
        }
        return user;
    }

    public boolean hasOnlyRole(String role) {
        User user = getAuthenticatedUser();
        if (user == null) {
            return false;
        }
        return user.hasOnlyRole(role);
    }

    public boolean hasRole(String role){
        User user = getAuthenticatedUser();
        if (user == null) {
            return false;
        }
        return user.hasRole(role);
    }
}
