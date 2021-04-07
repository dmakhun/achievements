package com.edu.academy.dao;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.edu.academy.entity.Group;
import com.edu.academy.entity.User;
import java.util.stream.Stream;
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
public class UserRepositoryTest {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    User createUser() {
        User user = new User("Name", "Surname", "username",
                null, "password", null);
        user.setEmail("email");
        return user;
    }

    @Test
    public void testFindByGroupId2() {
        User user = createUser();
        Group group = new GroupRepositoryTest().createOpenGroup(null);
        group.setUsers(Stream.of(user).collect(toSet()));
        user.setGroups(Stream.of(group).collect(toSet()));
        userRepository.save(user);
        assertEquals(1, userRepository.findByGroups_Id(group.getId()).size());
    }


}
