package com.softserve.edu.manager;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.softserve.edu.dao.ClassDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GenericDao;
import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.manager.impl.GroupManagerImplementation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ClassManagerTest {

    @Mock
    private ClassDao classDao;
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

    @BeforeEach
    public void setUp() {
        competence = new Competence();
        aClass = new Class();
        listClasses = new ArrayList<>();
        listClasses.add(aClass);
    }

    @Test
    public final void testInFutureEquals() {
        when(classDao.findGroupsToBeOpened()).thenReturn(listClasses);
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
        when(classDao.findGroupsToBeOpened(idMockLong)).thenReturn(listClasses);
        List<Class> listActual = groupManager.inFuture(idMockLong);
        assertEquals(listClasses, listActual);
    }

    @Test
    public final void testInFutureLongNotNull() {
        when(classDao.findGroupsToBeOpened(idMockLong)).thenReturn(listClasses);
        List<Class> listActual = groupManager.inFuture(idMockLong);
        assertNotNull(listActual);
    }

    @Test
    public final void testInFutureLongNull() {
        when(classDao.findGroupsToBeOpened(null)).thenReturn(null);
        List<Class> listActual = groupManager.inFuture(null);
        assertNull(listActual);
    }

    @Test
    public final void testFindByCompetenceEquals() {
        when(classDao.findByCompetence(idMockLong, false)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, false);
        assertEquals(listClasses, listActual);
    }

    @Test
    public final void testFindByCompetenceNull() {
        when(classDao.findByCompetence(idMockLong, false)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, false);
        assertNotNull(listActual);
    }

    @Test
    public final void testFindByCompetenceOnlyOpenedEquals() {
        when(classDao.findByCompetence(idMockLong, true)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, true);
        assertEquals(listClasses, listActual);
    }

    @Test
    public final void testFindByCompetenceOnlyOpenedNotNull() {
        when(classDao.findByCompetence(idMockLong, true)).thenReturn(listClasses);
        List<Class> listActual = groupManager.findAllByCompetenceId(idMockLong, true);
        assertNotNull(listActual);
    }

    @Test
    public final void testModify() throws GroupManagerException {
        when(competenceDao.findById(Competence.class, idMockLong)).thenReturn(competence);
        when(classDao.findById(Class.class, idMockLong)).thenReturn(aClass);
        groupManager.modify(idMockLong, uuIdMock, date, date, idMockLong);
    }

    @Test
    public final void testCreateStringDateDateString() throws GroupManagerException {
        when(competenceDao.findById(Competence.class, idMockLong)).thenReturn(competence);
        //when(classDao.save(aClass)).thenReturn(true);
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
