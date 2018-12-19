package com.softserve.edu.manager;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.softserve.edu.dao.AchievementRepository;
import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.exception.AchievementManagerException;
import com.softserve.edu.manager.impl.AchievementManagerImpl;
import java.util.List;
import junitparams.Parameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AchievementManagerTest {

    @Mock
    private AchievementRepository achievementRepository;
    @Mock
    private AchievementTypeRepository achievementTypeRepository;
    @Mock
    private UserDao userDao;

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

    @Parameters({"3, -5, 'comment' "})
    public void testGiveAwardToUserNegAchievementTypeID(long userID,
            long achievementTypeId, String comment) throws AchievementManagerException {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                achievementManager.awardUser(userID, achievementTypeId, comment));
    }

    @Test
    public void testFindUserAchievementsNotNull() {
        List<Achievement> actual;
        actual = achievementManager.findUserAchievementsByUserId(0L);
        assertNotNull(actual);
    }

}