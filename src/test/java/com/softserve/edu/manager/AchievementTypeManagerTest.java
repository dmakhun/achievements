package com.softserve.edu.manager;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.impl.AchievementTypeManagerImpl;
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


@RunWith(MockitoJUnitRunner.class)
public class AchievementTypeManagerTest {

    @Mock
    private CompetenceDao competenceDao;
    @Mock
    private AchievementTypeDao achievementTypeDao;

    @InjectMocks
    private AchievementTypeManager achievementTypeManager =
            new AchievementTypeManagerImpl();

    private Competence competence;
    private long IdMock = 1;
    private List<AchievementType> expectedList;

    @Before
    public void setUp() {
        competence = new Competence();
        expectedList = new ArrayList<>();
    }

    @Test
    public void testCreate() throws AchievementTypeManagerException {
        when(competenceDao.findById(Competence.class, IdMock)).thenReturn(competence);
        AchievementType achievementTypeExpected = new AchievementType().setName("Java")
                .setPoints(12).setCompetence(competence);
        achievementTypeDao.save(achievementTypeExpected);
        AchievementType achievementTypeActual = achievementTypeManager.createAchievementType(
                "Java", 12, IdMock);
        assertEquals(achievementTypeExpected, achievementTypeActual);
    }

    @Test
    public void testCreate2() throws AchievementTypeManagerException {
        when(competenceDao.findById(Competence.class, IdMock)).thenReturn(competence);
        AchievementType achievementTypeExpected = new AchievementType()
                .setName("Android").setPoints(15).setCompetence(competence);
        AchievementType achievementTypeActual = achievementTypeManager.createAchievementType(
                "Android", 15, IdMock);
        assertEquals("failure - not equal", achievementTypeExpected,
                achievementTypeActual);
    }

    @Test
    public void testAchievementTypeListEquals() {
        when(achievementTypeDao.findAll(AchievementType.class)).thenReturn(expectedList);
        List<AchievementType> actualList = achievementTypeManager.achievementTypesList();
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testAchievementTypeListNotNull() {
        when(achievementTypeDao.findAll(AchievementType.class)).thenReturn(expectedList);
        List<AchievementType> actualList = achievementTypeManager.achievementTypesList();
        assertNotNull(actualList);
    }
}