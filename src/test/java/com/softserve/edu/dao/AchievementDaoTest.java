package com.softserve.edu.dao;

import static com.softserve.edu.util.Constants.ROLE_MANAGER;
import static org.junit.Assert.assertEquals;

import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class AchievementDaoTest {

    @Autowired
    private AchievementDao achievementDao;
    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao.save(new User("Dmytro", "Makhun", "dmak", new AccessRole(ROLE_MANAGER), "password",
                null));
    }

    @Test
    public void testGetAchievementsByUserId() {
        User user = userDao.findByUsername("dmak");
        assertEquals(0, achievementDao.findAchievementsByUserId(user.getId()).size());
    }
}
