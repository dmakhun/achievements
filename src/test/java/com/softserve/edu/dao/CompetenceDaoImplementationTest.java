package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CompetenceDaoImplementationTest {

    @Autowired
    CompetenceDao competenceDao;

    @Test
    public void testShowGroups() {
        List<Group> groups = competenceDao.showGroups(1);
        assertEquals(1, groups.size());
    }

    @Test
    public void testFindGroupsByCompetenceUuid() {
        List<Group> groups = competenceDao.findGroupsByCompetenceUuid("i1");
        for (Iterator<Group> gr = groups.iterator(); gr.hasNext(); ) {
            System.out.println(gr.next().getName());
        }
        assertEquals(2, groups.size());
    }

    @Test
    public void testFindByName() {
        assertEquals("Java", competenceDao.findByName("Java").getName());
    }

    @Test
    public void testFindByUser() {
        List<Competence> competencies = competenceDao.findByUser(7L);
        for (Iterator<Competence> comp = competencies.iterator(); comp
                .hasNext(); ) {
            System.out.println(comp.next().getName());
        }
        assertEquals(1, competencies.size());
    }

    @Test
    public void testFindByUserUuid() {
        List<Competence> competencies = competenceDao.findByUserUuid("i1");
        for (Iterator<Competence> comp = competencies.iterator(); comp.hasNext(); ) {
            System.out.println(comp.next().getName());
        }
        assertEquals(2, competencies.size());
    }

    @Test
    public void testListWithUsers() {
        System.out.println("Competence size: "
                + competenceDao.listWithUsers().size());
        assertNotNull(competenceDao.listWithUsers());
    }

}
