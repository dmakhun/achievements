package com.softserve.edu.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GenericDao;
import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.manager.impl.GroupManagerImplementation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ClassManagerTest {

    @Mock
    private GroupDao groupDao;
    @Mock
    private CompetenceDao competenceDao;
    @Mock
    private GenericDao<Class> genericDao;

    @InjectMocks
    private GroupManager groupManager =
            new GroupManagerImplementation();

    private Competence competence;
    private List<Class> listClasses;
    private Date date;
    private Class aClass;
    private long idMockLong = 1;
    private String uuIdMock = "8a3e8480-d2d5-4b49-9c49-0ace2b718441";

    @Before
    public void setUp() {
        competence = new Competence();
        aClass = new Class();
        listClasses = new ArrayList<>();
        listClasses.add(aClass);
    }

    @Test
    public final void testInFutureEquals() {
        when(groupDao.findGroupsToBeOpened()).thenReturn(listClasses);
        List<Class> listActual = groupManager.inFuture();
        assertEquals(listClasses, listActual);
    }

    @Test
    public final void testInFutureNotNull() {
        List<Class> listActual = groupManager.inFuture();
        assertNotNull(listActual);
    }

    @Test
    public final void testInFutureLongEquals() {
        when(groupDao.findGroupsToBeOpened(idMockLong)).thenReturn(listClasses);
        List<Class> listActual = groupManager.inFuture(idMockLong);
        assertEquals(listClasses, listActual);
    }

    @Test
    public final void testInFutureLongNotNull() {
        when(groupDao.findGroupsToBeOpened(idMockLong)).thenReturn(listClasses);
        List<Class> listActual = groupManager.inFuture(idMockLong);
        assertNotNull(listActual);
    }

    @Test
    public final void testInFutureLongNull() {
        when(groupDao.findGroupsToBeOpened(null)).thenReturn(null);
        List<Class> listActual = groupManager.inFuture(null);
        assertNull(listActual);
    }

    @Test
    public final void testFindByCompetenceEquals() {
        when(groupDao.findByCompetence(idMockLong, false)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, false);
        assertEquals(listClasses, listActual);
    }

    @Test
    public final void testFindByCompetenceNull() {
        when(groupDao.findByCompetence(idMockLong, false)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, false);
        assertNotNull(listActual);
    }

    @Test
    public final void testFindByCompetenceOnlyOpenedEquals() {
        when(groupDao.findByCompetence(idMockLong, true)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, true);
        assertEquals(listClasses, listActual);
    }

    @Test
    public final void testFindByCompetenceOnlyOpenedNotNull() {
        when(groupDao.findByCompetence(idMockLong, true)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, true);
        assertNotNull(listActual);
    }

    @Test
    public final void testModify() throws GroupManagerException {
        when(competenceDao.findById(Competence.class, idMockLong)).thenReturn(competence);
        when(groupDao.findById(Class.class, idMockLong)).thenReturn(aClass);
        groupManager.modify(idMockLong, uuIdMock, date, date, idMockLong);
    }

    @Test
    public final void testCreateStringDateDateString() throws GroupManagerException {
        when(competenceDao.findById(Competence.class, idMockLong)).thenReturn(competence);
        //when(groupDao.save(aClass)).thenReturn(true);
        groupManager.create("name", date, date, idMockLong);
    }

    @Test
    public final void testAddUserLongLong() throws GroupManagerException {
        groupManager.addUser(idMockLong, idMockLong);
    }

    @Test
    public final void testUsersEquals() {
        List<User> expected = new ArrayList<>();
        List<User> actual = new ArrayList<>();
        actual = groupManager.users(idMockLong);
        assertEquals(expected, actual);
    }

    @Test
    public final void testUsersNotNull() {
        List<User> actual = new ArrayList<>();
        actual = groupManager.users(idMockLong);
        assertNotNull(actual);
    }

    @Test
    public final void testDeleteById() throws GroupManagerException {
        groupManager.deleteById(idMockLong);
    }

}
