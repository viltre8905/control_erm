package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.db.RoleDAO;
import com.verynet.gcint.api.model.Role;
import com.verynet.gcint.api.services.RoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by day on 28/07/2016.
 */
@Transactional
public class RoleServiceImpl implements RoleService {
    private RoleDAO dao;

    @Override
    public void setRoleDAO(RoleDAO dao) {
        this.dao = dao;
    }

    @Override
    public Role saveRole(Role role) {
        if (role.getId() == null) {
            Role roleEntity = getRole(role.getName());
            if (roleEntity != null) {
                // throw new LRSException(String.format("%s role already exists", role.getPermissionCode()));
            }
        }
        Role savedRole = dao.saveRole(role);
        return savedRole;
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRole(String name) {
        return dao.getRole(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRole(Integer id) {
        return dao.getRole(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByValue(String value) {
        return dao.getRoleByValue(value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return dao.getAllRoles();
    }

    @Override
    public List<Role> getAllRoles(String... values) {
        return dao.getAllRoles(values);
    }

    @Override
    public boolean deleteRole(String name) {
        return dao.deleteRole(name);
    }
}
