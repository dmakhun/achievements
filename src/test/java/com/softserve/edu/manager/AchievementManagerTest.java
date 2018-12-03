package com.softserve.edu.manager;

import static org.junit.Assert.assertNotNull;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.exception.AchievementManagerException;
import com.softserve.edu.manager.impl.AchievementManagerImpl;
import java.util.List;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@RunWith(JUnitParamsRunner.class)
public class AchievementManagerTest {

    @Mock
    private AchievementDao achievementDao;
    @Mock
    private AchievementTypeDao achievementTypeDao;
    @Mock
    private UserDao userDao;

    @InjectMocks
    private AchievementManager achievementManager = new AchievementManagerImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"-2, 3, 'comment' "})
    public void testGiveAwardToUserNegUserID(long userID,
            long achievementTypeId, String comment) throws AchievementManagerException {
        achievementManager.awardUser(userID, achievementTypeId, comment);
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"3, -5, 'comment' "})
    public void testGiveAwardToUserNegAchievementTypeID(long userID,
            long achievementTypeId, String comment) throws AchievementManagerException {
        achievementManager.awardUser(userID, achievementTypeId, comment);
    }

    @Test
    public void testFindUserAchievementsNotNull() {
        List<Achievement> actual;
        actual = achievementManager.findUserAchievementsByUserId(0L);
        assertNotNull(actual);
    }

}