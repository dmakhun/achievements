package com.softserve.edu.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import com.softserve.edu.manager.impl.RoleManagerImplementation;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoleManagerTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleManager roleManager =
            new RoleManagerImplementation();

    private Class<Role> classRole;
    private Role role;
    private int idMock = 1;
    private Long idMockLong;
    private String uuIdMock = "8a3e8480-d2d5-4b49-9c49-0ace2b718441";


    @Before
    public void setUp() {
        classRole = Role.class;
        role = new Role();
    }

    @Test
    public final void testFindUsers() {
        List<User> expected = new ArrayList<>();
        when(roleDao.findUsersById(idMock)).thenReturn(expected);
        List<User> actualUsersList = roleManager.findUsers(idMock);
        assertEquals(expected, actualUsersList);
    }

    @Test
    public final void testFindRoleUser() {
        when(roleDao.findRoleByName("ROLE_USER").getId()).thenReturn(3L);
        Long actual = roleManager.findRoleByName("ROLE_USER").getId();
        Long l = 3L;
        assertEquals(l, actual);
    }

    @Test
    public final void testFindRoleManager() {
        when(roleDao.findRoleByName("ROLE_MANAGER").getId()).thenReturn(2L);
        Long actual = roleManager.findRoleByName("ROLE_MANAGER").getId();
        Long l = 2L;
        assertEquals(l, actual);
    }

    @Test
    public final void testFindRoleAdmin() {
        when(roleDao.findRoleByName("ROLE_ADMIN").getId()).thenReturn(1L);
        Long actual = roleManager.findRoleByName("ROLE_ADMIN").getId();
        Long l = 1L;
        assertEquals(l, actual);
    }

    @Test
    public final void testFindRoleUserNotNull() {
        when(roleDao.findRoleByName("ROLE_USER").getId()).thenReturn(3L);
        Long actual = roleManager.findRoleByName("ROLE_USER").getId();
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleManagerNotNull() {
        when(roleDao.findRoleByName("ROLE_MANAGER").getId()).thenReturn(2L);
        Long actual = roleManager.findRoleByName("ROLE_MANAGER").getId();
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleAdminNotNull() {
        when(roleDao.findRoleByName("ROLE_ADMIN").getId()).thenReturn(1L);
        Long actual = roleManager.findRoleByName("ROLE_ADMIN").getId();
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleByRolenameUser() {
        when(roleDao.findRoleByName("ROLE_USER")).thenReturn(role);
        Role actual = roleManager.findRoleByName("ROLE_USER");
        assertEquals(role, actual);
    }

    @Test
    public final void testFindRoleByRolenameManager() {
        when(roleDao.findRoleByName("ROLE_MANAGER")).thenReturn(role);
        Role actual = roleManager.findRoleByName("ROLE_MANAGER");
        assertEquals(role, actual);
    }

    @Test
    public final void testFindRoleByRolenameAdmin() {
        when(roleDao.findRoleByName("ROLE_ADMIN")).thenReturn(role);
        Role actual = roleManager.findRoleByName("ROLE_ADMIN");
        assertEquals(role, actual);
    }

    @Test
    public final void testFindRoleByRolenameUserNotNull() {
        when(roleDao.findRoleByName("ROLE_USER")).thenReturn(role);
        Role actual = roleManager.findRoleByName("ROLE_USER");
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleByRolenameManagerNotNull() {
        when(roleDao.findRoleByName("ROLE_MANAGER")).thenReturn(role);
        Role actual = roleManager.findRoleByName("ROLE_MANAGER");
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleByRolenameAdminNotNull() {
        when(roleDao.findRoleByName("ROLE_ADMIN")).thenReturn(role);
        Role actual = roleManager.findRoleByName("ROLE_ADMIN");
        assertNotNull(actual);
    }

    @Test
    public final void testGetById() {
        role.setName("ROLE_ADMIN");
        when(roleDao.findById(Role.class, idMockLong)).thenReturn(role);
        Role actual = roleManager.findById(idMockLong);
        verify(roleDao).findById(Role.class, idMockLong);
        assertEquals(role, actual);
    }

    @Test
    public final void testGetByIdNotNull() {
        when(roleDao.findById(classRole, idMockLong)).thenReturn(role);
        Role expected = roleManager.findById(idMockLong);
        assertNotNull(expected);
    }
}
