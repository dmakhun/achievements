package com.softserve.edu.manager;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import com.softserve.edu.manager.impl.RoleManagerImplementation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        when(roleDao.findUsers(idMock)).thenReturn(expected);
        List<User> actualUsersList = roleManager.findUsers(idMock);
        assertEquals(expected, actualUsersList);
    }

    @Test
    public final void testFindRoleUser() {
        when(roleDao.findRole("ROLE_USER")).thenReturn(3L);
        Long actual = roleManager.findRole("ROLE_USER");
        Long l = 3L;
        assertEquals(l, actual);
    }

    @Test
    public final void testFindRoleManager() {
        when(roleDao.findRole("ROLE_MANAGER")).thenReturn(2L);
        Long actual = roleManager.findRole("ROLE_MANAGER");
        Long l = 2L;
        assertEquals(l, actual);
    }

    @Test
    public final void testFindRoleAdmin() {
        when(roleDao.findRole("ROLE_ADMIN")).thenReturn(1L);
        Long actual = roleManager.findRole("ROLE_ADMIN");
        Long l = 1L;
        assertEquals(l, actual);
    }

    @Test
    public final void testFindRoleUserNotNull() {
        when(roleDao.findRole("ROLE_USER")).thenReturn(3L);
        Long actual = roleManager.findRole("ROLE_USER");
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleManagerNotNull() {
        when(roleDao.findRole("ROLE_MANAGER")).thenReturn(2L);
        Long actual = roleManager.findRole("ROLE_MANAGER");
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleAdminNotNull() {
        when(roleDao.findRole("ROLE_ADMIN")).thenReturn(1L);
        Long actual = roleManager.findRole("ROLE_ADMIN");
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleByRolenameUser() {
        when(roleDao.findRoleByName("ROLE_USER")).thenReturn(role);
        Role actual = roleManager.findRoleByRolename("ROLE_USER");
        assertEquals(role, actual);
    }

    @Test
    public final void testFindRoleByRolenameManager() {
        when(roleDao.findRoleByName("ROLE_MANAGER")).thenReturn(role);
        Role actual = roleManager.findRoleByRolename("ROLE_MANAGER");
        assertEquals(role, actual);
    }

    @Test
    public final void testFindRoleByRolenameAdmin() {
        when(roleDao.findRoleByName("ROLE_ADMIN")).thenReturn(role);
        Role actual = roleManager.findRoleByRolename("ROLE_ADMIN");
        assertEquals(role, actual);
    }

    @Test
    public final void testFindRoleByRolenameUserNotNull() {
        when(roleDao.findRoleByName("ROLE_USER")).thenReturn(role);
        Role actual = roleManager.findRoleByRolename("ROLE_USER");
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleByRolenameManagerNotNull() {
        when(roleDao.findRoleByName("ROLE_MANAGER")).thenReturn(role);
        Role actual = roleManager.findRoleByRolename("ROLE_MANAGER");
        assertNotNull(actual);
    }

    @Test
    public final void testFindRoleByRolenameAdminNotNull() {
        when(roleDao.findRoleByName("ROLE_ADMIN")).thenReturn(role);
        Role actual = roleManager.findRoleByRolename("ROLE_ADMIN");
        assertNotNull(actual);
    }

    @Test
    public final void testFindUsersByRoleUuid() {
        List<User> expected = new ArrayList<>();
        List<User> actual;
        actual = roleManager.findUsersByRoleUuid(uuIdMock);
        assertEquals(expected, actual);
    }

    @Test
    public final void testFindUsersByRoleUuidNotNull() {
        List<User> actual = new ArrayList<User>();
        actual = roleManager.findUsersByRoleUuid(uuIdMock);
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

    @Test
    public final void testFindRoleByUuid() {
        when(roleDao.findByUuid(classRole, uuIdMock)).thenReturn(role);
        Role expected = roleManager.findRoleByUuid(uuIdMock);
        assertEquals(role, expected);
    }

    @Test
    public final void testFindRoleByUuidNotNull() {
        when(roleDao.findByUuid(classRole, uuIdMock)).thenReturn(role);
        Role expectedRole = roleManager.findRoleByUuid(uuIdMock);
        assertNotNull(expectedRole);
    }
}
