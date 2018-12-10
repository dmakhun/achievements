package com.softserve.edu.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.softserve.edu.entity.Competence;
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
public class CompetenceDaoTest {

    @Autowired
    private CompetenceDao competenceDao;

    @Test
    public void testFindByName() {
        assertEquals("Java", competenceDao.findByName("Java").getName());
    }

    @Test
    public void testFindByUser() {
        List<Competence> competencies = competenceDao.findCompetencesByUserId(7L);
        for (Competence competency : competencies) {
            System.out.println(competency.getName());
        }
        assertEquals(1, competencies.size());
    }

    @Test
    public void testListWithUsers() {
        System.out.println("Competence size: "
                + competenceDao.findAllCompetences().size());
        assertNotNull(competenceDao.findAllCompetences());
    }

}
