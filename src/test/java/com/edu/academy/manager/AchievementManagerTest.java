package com.edu.academy.manager;


import com.edu.academy.dao.AchievementRepository;
import com.edu.academy.dao.AchievementTypeRepository;
import com.edu.academy.exception.AchievementManagerException;
import com.edu.academy.manager.impl.AchievementManagerImpl;
import junitparams.Parameters;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@Disabled
public class AchievementManagerTest {

    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementTypeRepository achievementTypeRepository;

    @InjectMocks
    private AchievementManager achievementManager = new AchievementManagerImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Parameters({"-2, 3, 'comment' "})
    public void testGiveAwardToUserNegUserID(long userID,
            long achievementTypeId, String comment) throws AchievementManagerException {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                achievementManager.awardUser(userID, achievementTypeId, comment));
    }

    @Test
    @Parameters({"3, -5, 'comment' "})
    public void testGiveAwardToUserNegAchievementTypeID(long userID,
            long achievementTypeId, String comment) throws AchievementManagerException {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                achievementManager.awardUser(userID, achievementTypeId, comment));
    }

}