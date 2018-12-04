package com.softserve.edu.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompetenceDaoTest {

    @Autowired
    private CompetenceDao competenceDao;

    @Test
    public void testShowGroups() {
        List<Group> groups = competenceDao.findGroupsByCompetenceId(1);
        assertEquals(1, groups.size());
    }

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
