package com.softserve.edu.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.softserve.edu.entity.AchievementType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@ExtendWith(SpringExtension.class)
@Transactional
public class AchievementTypeDaoTest {

    @Autowired
    private AchievementTypeDao achievementTypeDao;

    @Test
    public void testfindByCompetenceId() {
        List<AchievementType> achType = achievementTypeDao.findByCompetenceId(1L);
        assertEquals(3, achType.size());
    }


}
