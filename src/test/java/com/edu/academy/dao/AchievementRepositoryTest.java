package com.edu.academy.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Transactional
@Rollback
@ExtendWith(SpringExtension.class)
@Disabled
public class AchievementRepositoryTest {

    @Autowired
    private AchievementRepository achievementRepository;

    @Test
    public void testFindTotalPoints() {
        assertEquals(50, achievementRepository.findTotalAchievementPointsByUserId(1));
    }

    @Test
    public void testFindByUserId() {
        assertEquals(1, achievementRepository.findByUserId(3).size());
    }
}
