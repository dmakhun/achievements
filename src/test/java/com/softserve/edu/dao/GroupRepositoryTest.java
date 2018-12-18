package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
public class GroupRepositoryTest {

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    CompetenceDao competenceDao;

    @Autowired
    GroupDao groupDao;

    private Long competenceId;

    @BeforeEach
    public void setUp() {
        Competence competence = new Competence("competence", null);
        competenceDao.save(competence);
        competenceId = competenceDao.findByName("competence").getId();
        Group aGroup = createPendingGroup(competence);
        Group aGroup2 = createOpenGroup(competence);
        groupRepository.saveAll(Arrays.asList(aGroup, createClosedGroup(competence), aGroup2));
    }

    private Group createPendingGroup(Competence competence) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 3);
        Date endDate = calendar.getTime();
        return new Group(competence, "groupName", startDate, endDate, null);
    }

    private Group createOpenGroup(Competence competence) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 3);
        Date endDate = calendar.getTime();
        return new Group(competence, "groupName", startDate, endDate, null);
    }

    private Group createClosedGroup(Competence competence) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date endDate = calendar.getTime();
        return new Group(competence, "groupName", startDate, endDate, null);
    }

}
