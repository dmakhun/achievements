package com.softserve.edu.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.impl.CompetenceManagerImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CompetenceManagerTest {

    @InjectMocks
    private CompetenceManager competenceManager = new CompetenceManagerImpl();
    @Mock
    private CompetenceDao competenceDao;
    @Mock
    private AchievementTypeDao achievementTypeDao;
    private int idMock = 1;
    private long idMockLong = 1;
    private String uuIdMock = "8a3e8480-d2d5-4b49-9c49-0ace2b718441";
    private Class aClass;
    private AchievementType achievementType;
    private List<Class> listClasses = new ArrayList<>(idMock);
    private List<Competence> listCompetences = new ArrayList<>();
    private List<AchievementType> listAchievementTypes = new ArrayList<>();
    private Set<Competence> setCompetences = new HashSet<>();
    private Competence competence;
    private java.lang.Class competenceClass;

    @Before
    public void setUp() {
        achievementType = new AchievementType();
        aClass = new Class();
        competence = new Competence();
        listClasses.add(aClass);
        listCompetences.add(competence);
        listAchievementTypes.add(achievementType);
        setCompetences.add(competence);
        competenceClass = Competence.class;
    }

    @Test
    public void testGetGroups() {
        when(competenceDao.findGroupsByCompetenceId(idMock)).thenReturn(listClasses);
        List<Class> listActual = competenceManager.findGroups(idMock);
        assertEquals(listClasses, listActual);
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
        List<AchievementType> listActual = competenceManager
                .findAchievementsTypesByCompetenceId(idMockLong);
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
    public void testDeleteByStringFalse() throws CompetenceManagerException {
        boolean deleted = competenceManager.delete(idMockLong);
        assertFalse("Failure - must be false", deleted);
    }

    @Test
    public void testFindByUser() {
        when(competenceDao.findCompetencesByUserId(idMockLong)).thenReturn(listCompetences);
        List<Competence> expected = competenceManager.findByUserId(idMockLong);
        assertEquals(expected, listCompetences);
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
}
