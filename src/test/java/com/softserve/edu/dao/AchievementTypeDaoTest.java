package com.softserve.edu.dao;

import com.softserve.edu.entity.AchievementType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AchievementTypeDaoTest {

    @Autowired
    private AchievementTypeDao achievementTypeDao;

    @Test
    public void testfindByCompetenceId() {
        List<AchievementType> achType = achievementTypeDao.findByCompetenceId(1L);
        System.out.println("achievement types of competence with id = 1");
        for (AchievementType anAchType : achType) {
            System.out.println(anAchType.getName());
        }
        assertEquals(3, achType.size());
    }

    @Test
    public void testfindByCompetenceUuid() {
        List<AchievementType> achType = achievementTypeDao.findByCompetenceUuid("i1");
        System.out.println("achievement types of competence with uuid = 'i1'");
        for (AchievementType anAchType : achType) {
            System.out.println(anAchType.getName());
        }
        assertEquals(4, achType.size());
    }

}
