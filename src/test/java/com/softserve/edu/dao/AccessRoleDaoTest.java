package com.softserve.edu.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.softserve.edu.entity.AccessRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@ExtendWith(SpringExtension.class)
@Transactional
public class AccessRoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void testFindRole() {
        long i = roleDao.findRoleByName("admin").getId();
        System.out.println("id role with name 'admin': " + i);
        assertEquals(2, i);
    }

    @Test
    public void testFindRoleByRolename() {
        AccessRole accessRole = roleDao.findRoleByName("user");
        System.out.println("Uuid from user " + accessRole.getId());
        assertEquals("i1", accessRole.getId());
        assertNotNull(accessRole);
    }
}
