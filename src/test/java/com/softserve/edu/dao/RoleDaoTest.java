package com.softserve.edu.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void testFindUsers() {
        List<User> users = roleDao.findUsersById(1);
        System.out.println("Users with role id 1");
        for (User user : users) {
            System.out.println(user.getName());
        }
        assertEquals(2, users.size());
    }

    @Test
    public void testFindRole() {
        long i = roleDao.findRoleByName("admin").getId();
        System.out.println("id role with name 'admin': " + i);
        assertEquals(2, i);
    }

    @Test
    public void testFindRoleByRolename() {
        Role role = roleDao.findRoleByName("user");
        System.out.println("Uuid from user " + role.getId());
        assertEquals("i1", role.getId());
        assertNotNull(role);
    }
}
