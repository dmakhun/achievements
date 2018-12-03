package com.softserve.edu.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
public class AchievementDaoTest {

    @Autowired
    private AchievementDao achievementDao;

    @Test
    public void testGetAchievementsByUserId() {
        assertEquals(1, achievementDao.findAchievementsByUserId(7L).size());
    }
}
