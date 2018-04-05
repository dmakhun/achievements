package com.softserve.edu.manager;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.dao.impl.GenericDaoImplementation;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.impl.AchievementTypeManagerImplementation;
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
import static org.mockito.Mockito.when;

/**
 * @author dmakhtc
 */
@RunWith(MockitoJUnitRunner.class)
public class AchievementTypeManagerImplTest {

    @Mock
    private CompetenceDao competenceDao;
    @Mock
    private AchievementTypeDao achievementTypeDao;
    @Mock
    private GenericDaoImplementation<AchievementType> genericDaoImplementation;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private AchievementTypeManager achievementTypeManager =
            new AchievementTypeManagerImplementation();

    private Competence competence;
    private long IdMock = 1;
    private List<AchievementType> expectedList;

    @Before
    public void setUp() {
        competence = new Competence();
        expectedList = new ArrayList<AchievementType>();
    }

    @Test
    public void testCreateJava() throws AchievementTypeManagerException {
        when(competenceDao.findById(Competence.class, IdMock)).thenReturn(competence);
        AchievementType achievementTypeExpected = new AchievementType().setName("Java")
                .setPoints(12).setCompetence(competence);
        achievementTypeDao.save(achievementTypeExpected);
        AchievementType achievementTypeActual = achievementTypeManager.create(
                "Java", 12, IdMock);
        assertEquals(achievementTypeExpected, achievementTypeActual);
    }

    @Test
    public void testCreateAndroid() throws AchievementTypeManagerException {
        when(competenceDao.findById(Competence.class, IdMock)).thenReturn(competence);
        AchievementType achievementTypeExpected = new AchievementType()
                .setName("Android").setPoints(15).setCompetence(competence);
        AchievementType achievementTypeActual = achievementTypeManager.create(
                "Android", 15, IdMock);
        assertEquals("failure - not equal", achievementTypeExpected,
                achievementTypeActual);
    }

    @Test
    public void testAchievementTypeListEquals() {
        when(achievementTypeDao.findAll(AchievementType.class)).thenReturn(expectedList);
        List<AchievementType> actualList = achievementTypeManager.achievementTypeList();
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testAchievementTypeListNotNull() {
        when(achievementTypeDao.findAll(AchievementType.class)).thenReturn(expectedList);
        List<AchievementType> actualList = achievementTypeManager.achievementTypeList();
        assertNotNull(actualList);
    }
}