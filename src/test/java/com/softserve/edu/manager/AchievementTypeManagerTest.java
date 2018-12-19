package com.softserve.edu.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.impl.AchievementTypeManagerImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AchievementTypeManagerTest {

    @Mock
    private CompetenceDao competenceDao;
    @Mock
    private AchievementTypeRepository achievementTypeRepository;

    @InjectMocks
    private AchievementTypeManager achievementTypeManager =
            new AchievementTypeManagerImpl();

    private Competence competence;
    private long IdMock = 1;
    private List<AchievementType> expectedList;

    @BeforeEach
    public void setUp() {
        competence = new Competence();
        expectedList = new ArrayList<>();
    }

    @Test
    public void testCreate() throws AchievementTypeManagerException {
        when(competenceDao.findById(Competence.class, IdMock)).thenReturn(competence);
        AchievementType achievementTypeExpected = new AchievementType().setName("Java")
                .setPoints(12).setCompetence(competence);
        achievementTypeRepository.save(achievementTypeExpected);
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
        assertEquals(achievementTypeExpected, achievementTypeActual, "failure - not equal");
    }

    @Test
    public void testAchievementTypeListEquals() {
        when(achievementTypeRepository.findAll()).thenReturn(expectedList);
        Iterable<AchievementType> actualList = achievementTypeManager.achievementTypesList();
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testAchievementTypeListNotNull() {
        when(achievementTypeRepository.findAll()).thenReturn(expectedList);
        Iterable<AchievementType> actualList = achievementTypeManager.achievementTypesList();
        assertNotNull(actualList);
    }
}