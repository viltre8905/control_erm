package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.UserDAO;
import com.verynet.gcint.api.model.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 28/07/2016.
 */
public class HibernateUserDAO extends HibernateGeneralDAO implements UserDAO {
    public HibernateUserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User saveUser(User user) {
        currentSession().saveOrUpdate(user);
        return user;
    }

    @Override
    public User getUser(String userName) {
        List<User> users = currentSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", userName)).list();
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public List<User> getUsersByRoleValue(Integer entityId, String value) {
        List<User> users = currentSession().createCriteria(User.class).createAlias("roles", "r")
                .createAlias("entity", "e").add(Restrictions.and(Restrictions.eq("r.value", value),
                        Restrictions.eq("e.id", entityId))).list();
        return users;
    }

    @Override
    public List<User> getUsersWithRoleValues(Integer entityId, String[] values) {
        Criterion[] criterions = new Criterion[values.length];
        for (int i = 0; i < values.length; i++) {
            criterions[i] = Restrictions.eq("det_role.value", values[i]);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class, "det_user");
        detachedCriteria.createAlias("det_user.roles", "det_role").add(Restrictions.or(criterions)).setProjection(Property.forName("roles"));
        Criteria criteria = currentSession().createCriteria(User.class).createAlias("entity", "e")
                .add(Restrictions.and(Restrictions.eq("e.id", entityId), Property.forName("roles").in(detachedCriteria)));
        return criteria.list();
    }

    @Override
    public List<User> getUsersWithOutRoleValues(Integer entityId, String[] values) {
        Criterion[] criterions = new Criterion[values.length];
        for (int i = 0; i < values.length; i++) {
            criterions[i] = Restrictions.eq("det_role.value", values[i]);
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class, "det_user");
        detachedCriteria.createAlias("det_user.roles", "det_role").add(Restrictions.or(criterions)).setProjection(Property.forName("roles"));
        Criteria criteria = currentSession().createCriteria(User.class).createAlias("entity", "e")
                .add(Restrictions.and(Restrictions.eq("e.id", entityId), Property.forName("roles").notIn(detachedCriteria)));
        return criteria.list();
    }

    @Override
    public User getUser(Integer id) {
        return (User) currentSession().get(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return currentSession().createCriteria(User.class).list();
    }

    @Override
    public List<User> getAllUsers(Integer entityId) {
        return currentSession().createCriteria(User.class).createAlias("entity", "e")
                .add(Restrictions.eq("e.id", entityId)).list();
    }

    @Override
    public boolean deleteUser(String userName) {
        User user = getUser(userName);
        if (user != null) {
            currentSession().delete(user);
            return true;
        }
        return false;
    }
}
