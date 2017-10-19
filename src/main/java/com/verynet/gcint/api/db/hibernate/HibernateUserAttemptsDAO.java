package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.UserAttemptsDAO;
import com.verynet.gcint.api.model.UserAttempts;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 05/04/2017.
 */
public class HibernateUserAttemptsDAO extends HibernateGeneralDAO implements UserAttemptsDAO {

    public HibernateUserAttemptsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public UserAttempts saveUserAttempts(UserAttempts userAttempts) {
        currentSession().saveOrUpdate(userAttempts);
        return userAttempts;
    }

    @Override
    public UserAttempts getUserAttempts(String userName) {
        List<UserAttempts> userAttemptsList = currentSession().createCriteria(UserAttempts.class).createAlias("user", "u")
                .add(Restrictions.eq("u.userName", userName)).list();
        return userAttemptsList.size() > 0 ? userAttemptsList.get(0) : null;
    }
}
