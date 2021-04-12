package com.softserve.edu.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
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