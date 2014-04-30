package com.softserve.edu.manager;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNotNull;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.exception.CompetenceManagerException;

/**
 * 
 * @author dmakhtc
 *
 */
@RunWith(JUnitParamsRunner.class)
public class AchievementManagerImplTest {

	@Mock private AchievementDao achievementDao;
	@Mock private AchievementTypeDao achievementTypeDao;
	@Mock private UserDao userDao;

	@InjectMocks private AchievementManager achievementManager = 
									new AchievementManagerImplementation();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters({ "-2, 3, 'comment' " })
	public void testGiveAwardToUserNegUserID(long userID,
			long achievementTypeId, String comment) throws CompetenceManagerException {
		achievementManager.awardUser(userID, achievementTypeId, comment);
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters({ "3, -5, 'comment' " })
	public void testGiveAwardToUserNegAchievementTypeID(long userID,
			long achievementTypeId, String comment) throws CompetenceManagerException {
		achievementManager.awardUser(userID, achievementTypeId, comment);
	}

	@Test
	public void testFindUserAchievementsNotNull() {
		List<Achievement> actual = new ArrayList<Achievement>();
		actual = achievementManager.findUserAchievements(0l);
		assertNotNull(actual);
	}

}