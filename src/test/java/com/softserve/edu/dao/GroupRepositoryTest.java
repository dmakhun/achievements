package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import java.util.Arrays;
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
public class GroupRepositoryTest {

    @Autowired
    ClassRepository classRepository;
    @Autowired
    CompetenceDao competenceDao;

    private Long id;

    @Test
    public void setUp() {
        Competence competence = new Competence("competence", null);
        competenceDao.save(competence);
        id = competenceDao.findByName("competence").getId();
        Group aGroup = new Group(competence, "class", null, null, null);
        Group aGroup2 = new Group(competence, "class2", null, null, null);
        classRepository.saveAll(Arrays.asList(aGroup, aGroup2));
    }

}
