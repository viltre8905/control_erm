package com.verynet.gcint.api.services.impl;

import com.verynet.gcint.api.context.Context;
import com.verynet.gcint.api.model.Role;
import com.verynet.gcint.api.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by day on 30/07/2016.
 */
@RunWith(JUnit4.class)
public class UserAndRoleTest {
    private GenericXmlApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        applicationContext = new GenericXmlApplicationContext();
        applicationContext.load("classpath:applicationContext-service.xml");
        applicationContext.refresh();
    }

    @Test
    public void save() {
        User user = new User();
        user.setEnabled(true);
        user.setUserName("viltre");
        user.setPassword("12345");
        User userSaved = Context.getUserService().saveUser(user);
        Assert.assertNotNull(userSaved);

        Role r = new Role();
        r.setName("ROLE_USER");
        Role roleSaved = Context.getRoleService().saveRole(r);
        Assert.assertNotNull(roleSaved);

        List<Role> roles = new ArrayList<>();
        roles.add(r);
        userSaved.setRoles(roles);
        userSaved = Context.getUserService().saveUser(userSaved);
        Assert.assertNotNull(userSaved);

        List<User> users = Context.getUserService().getAllUsers();
        Assert.assertEquals(users.size(), 1);
        Context.getUserService().deleteUser("viltre");
        users = Context.getUserService().getAllUsers();
        Assert.assertEquals(users.size(), 0);
    }
}
