package com.verynet.gcint.api.db.hibernate;

import com.verynet.gcint.api.db.RoleDAO;
import com.verynet.gcint.api.model.Role;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by day on 28/07/2016.
 */
public class HibernateRoleDAO extends HibernateGeneralDAO implements RoleDAO {

    public HibernateRoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Role saveRole(Role role) {
        currentSession().saveOrUpdate(role);
        return role;
    }

    @Override
    public Role getRole(String name) {
        List<Role> roles = currentSession().createCriteria(Role.class)
                .add(Restrictions.eq("name", name)).list();
        return roles.size() > 0 ? roles.get(0) : null;
    }

    @Override
    public Role getRoleByValue(String value) {
        List<Role> roles = currentSession().createCriteria(Role.class)
                .add(Restrictions.eq("value", value)).list();
        return roles.size() > 0 ? roles.get(0) : null;
    }

    @Override
    public Role getRole(Integer id) {
        return (Role) currentSession().get(Role.class, id);
    }

    @Override
    public List<Role> getAllRoles() {
        return currentSession().createCriteria(Role.class).list();
    }

    @Override
    public List<Role> getAllRoles(String[] values) {
        Criterion[] criterions = new Criterion[values.length];
        for (int i = 0; i < values.length; i++) {
            criterions[i] = Restrictions.ne("value", values[i]);
        }
        Criteria criteria = currentSession().createCriteria(Role.class).add(Restrictions.and(criterions));
        return criteria.list();
    }

    @Override
    public boolean deleteRole(String name) {
        Role role = getRole(name);
        if (role != null) {
            currentSession().delete(role);
            return true;
        }
        return false;
    }
}
