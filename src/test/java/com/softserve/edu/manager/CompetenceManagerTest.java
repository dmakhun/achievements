package com.softserve.edu.manager;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.impl.CompetenceManagerImplementation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CompetenceManagerTest {

    @InjectMocks
    private CompetenceManager competenceManager = new CompetenceManagerImplementation();
    @Mock
    private CompetenceDao competenceDao;
    @Mock
    private AchievementTypeDao achievementTypeDao;
    private int idMock = 1;
    private long idMockLong = 1;
    private String uuIdMock = "8a3e8480-d2d5-4b49-9c49-0ace2b718441";
    private Group group;
    private AchievementType achievementType;
    private List<Group> listGroups = new ArrayList<>(idMock);
    private List<Competence> listCompetences = new ArrayList<>();
    private List<AchievementType> listAchievementTypes = new ArrayList<>();
    private Set<Competence> setCompetences = new HashSet<>();
    private Competence competence;
    private Class<Competence> competenceClass;

    @Before
    public void setUp() {
        achievementType = new AchievementType();
        group = new Group();
        competence = new Competence();
        listGroups.add(group);
        listCompetences.add(competence);
        listAchievementTypes.add(achievementType);
        setCompetences.add(competence);
        competenceClass = Competence.class;
    }

    @Test
    public void testGetGroups() {
        when(competenceDao.showGroups(idMock)).thenReturn(listGroups);
        List<Group> listActual = competenceManager.findGroups(idMock);
        assertEquals(listGroups, listActual);
    }

    @Test
    public void testFindGroupsByCompetenceUuid() {
        when(competenceDao.findGroupsByCompetenceUuid(uuIdMock)).thenReturn(listGroups);
        List<Group> listActual = competenceManager.findGroupsByCompetenceUuid(uuIdMock);
        assertEquals(listGroups, listActual);
    }

    @Test
    public void testfindAllCompetence() {
        when(competenceDao.findAllCompetences()).thenReturn(listCompetences);
        List<Competence> listActual = competenceManager.findAllCompetences();
        assertEquals(listCompetences, listActual);
    }

    @Test
    public void testGetTypesOfAchievements() {
        when(achievementTypeDao.findByCompetenceId(idMockLong)).thenReturn(listAchievementTypes);
        List<AchievementType> listActual = competenceManager.findTypesOfAchievements(idMockLong);
        assertEquals(listAchievementTypes, listActual);
    }

    @Test
    public void testCreateEquals() throws CompetenceManagerException {
        Competence competenceExpected = new Competence();
        competenceExpected.setName("Java");
        competence = competenceManager.create("Java");
        assertEquals(competenceExpected, competence);
    }

    @Test
    public void testCreateNotSame() throws CompetenceManagerException {
        Competence competenceExpected = new Competence();
        competenceExpected.setName("Android");
        competence = competenceManager.create("Java");
        assertNotSame(competenceExpected, competence);
    }

    @Test
    public void testDeleteByID() throws CompetenceManagerException {
        when(competenceDao.findById(competenceClass, idMockLong)).thenReturn(competence);
        boolean deleted = competenceManager.delete(idMockLong);
        assertTrue("Not Deleted", deleted);
    }

    @Test(expected = CompetenceManagerException.class)
    public void testDeleteByUuidNullException() throws CompetenceManagerException {
        competenceManager.deleteByUuid(null);
    }

    @Test
    public void testDeleteByUuid() throws CompetenceManagerException {
        when(competenceDao.findByUuid(competenceClass, uuIdMock)).thenReturn(competence);
        boolean deleted = competenceManager.deleteByUuid(uuIdMock);
        assertTrue("Not Deleted", deleted);
    }

    @Test(expected = CompetenceManagerException.class)
    public void testDeleteByStringFalse() throws CompetenceManagerException {
        boolean deleted = competenceManager.delete(idMockLong);
        assertFalse("Failure - must be false", deleted);
    }

    @Test
    public void testFindByUser() {
        when(competenceDao.findCompetencesByUserId(idMockLong)).thenReturn(listCompetences);
        List<Competence> expected = competenceManager.findByUser(idMockLong);
        assertEquals(expected, listCompetences);
    }

    @Test
    public void testFindByUserUuidEquals() {
        when(competenceDao.findByUserUuid(uuIdMock)).thenReturn(listCompetences);
        List<Competence> expected = competenceManager.findByUserUuid(uuIdMock);
        assertEquals(expected, listCompetences);
    }

    @Test
    public void testFindByUserUuidNotNull() {
        when(competenceDao.findByUserUuid(uuIdMock)).thenReturn(listCompetences);
        List<Competence> expected = competenceManager.findByUserUuid(uuIdMock);
        assertNotNull(expected);
    }

    @Test
    public void testGetByIDNotNull() {
        when(competenceDao.findById(competenceClass, idMockLong)).thenReturn(competence);
        assertNotNull(competenceManager.findByID(idMockLong));
    }

    @Test
    public void testGetByIDEquals() {
        when(competenceDao.findById(competenceClass, idMockLong)).thenReturn(competence);
        Competence actual = competenceManager.findByID(idMockLong);
        assertEquals(competence, actual);
    }

    @Test
    public void testGetAchievementTypesByCompetenceUuidEquals() {
        when(achievementTypeDao.findByCompetenceUuid(uuIdMock)).thenReturn(listAchievementTypes);
        List<AchievementType> expected = competenceManager.findAchievementTypesByCompetenceUuid(uuIdMock);
        assertEquals(listAchievementTypes, expected);
    }

    @Test
    public void testGetAchievementTypesByCompetenceUuidNotNull() {
        when(achievementTypeDao.findByCompetenceUuid(uuIdMock)).thenReturn(listAchievementTypes);
        List<AchievementType> expected = competenceManager.findAchievementTypesByCompetenceUuid(uuIdMock);
        assertNotNull(expected);
    }
}
