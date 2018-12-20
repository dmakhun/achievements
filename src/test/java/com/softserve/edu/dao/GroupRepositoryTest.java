package com.softserve.edu.dao;

import static java.util.Arrays.asList;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
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
    GroupRepository groupRepository;
    @Autowired
    CompetenceRepository competenceRepository;
    @Autowired
    UserRepository userRepository;

    private Long competenceId;

    @BeforeEach
    public void setUp() {
        Competence competence = new Competence("competence", null);
        competenceRepository.save(competence);
        competenceId = competenceRepository.findByName("competence").getId();
        Group aGroup = createPendingGroup(competence);
        Group aGroup2 = createOpenGroup(competence);
        groupRepository.saveAll(asList(aGroup, createClosedGroup(competence), aGroup2));
    }

    public Group createPendingGroup(Competence competence) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 3);
        Date endDate = calendar.getTime();
        return new Group(competence, "groupName", startDate, endDate, null);
    }

    public Group createOpenGroup(Competence competence) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, 3);
        Date endDate = calendar.getTime();
        return new Group(competence, "groupName", startDate, endDate, null);
    }

    public Group createClosedGroup(Competence competence) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date endDate = calendar.getTime();
        return new Group(competence, "groupName", startDate, endDate, null);
    }

    @Test
    public void testQuery() {
        User user = new UserRepositoryTest().createUser();
        Group group = createOpenGroup(null);
//        user.setGroups((new HashSet<>(asList(group))));
        user.addGroup(group);
        userRepository.save(user);
        groupRepository.findOpenedByUserId(user.getId());
    }

}
