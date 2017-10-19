package com.verynet.gcint.api.services;

import com.verynet.gcint.api.db.RoleDAO;
import com.verynet.gcint.api.model.Role;

import java.util.List;

/**
 * Created by day on 28/07/2016.
 */
public interface RoleService {
    public void setRoleDAO(RoleDAO dao);

    public Role saveRole(Role role);

    public Role getRole(String name);

    public Role getRoleByValue(String value);

    public Role getRole(Integer id);

    public List<Role> getAllRoles();

    public List<Role> getAllRoles(String... values);

    public boolean deleteRole(String name);
}
