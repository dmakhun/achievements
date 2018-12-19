package com.softserve.edu.dao;

import static com.softserve.edu.util.Constants.ROLE_MANAGER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class AchievementRepositoryTest {

    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao.save(new User("Dmytro", "Makhun", "dmak", new AccessRole(ROLE_MANAGER), "password",
                null));
    }

    @Test
    public void testGetAchievementsByUserId() {
        User user = userDao.findByUsername("dmak");
        assertEquals(0, achievementRepository.findByUserId(user.getId()).size());
    }
}
