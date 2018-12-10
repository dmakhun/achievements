package com.softserve.edu.dao;

import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
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
public class ClassRepositoryTest {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    CompetenceDao competenceDao;

    Long id;

    @BeforeEach
    public void setUp() {
        Competence competence = new Competence("competence", null);
        competenceDao.save(competence);
        id = competenceDao.findByName("competence").getId();
        Class aClass = new Class(competence, "class", null, null, null);
        Class aClass2 = new Class(competence, "class2", null, null, null);
        classRepository.saveAll(Arrays.asList(aClass, aClass2));
    }


}