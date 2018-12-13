package com.softserve.edu.dao.impl;

import static java.util.stream.Collectors.toSet;

import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDaoImpl extends GenericDaoImpl<Group> implements GroupDao {

    @Autowired
    private UserDao userDao;

    @Override
    public List<Group> findGroupsToBeOpenedByCompetenceId(Long competenceId) {
        return findEntityList(
                Group.FIND_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE_ID, new Date(),
                competenceId);
    }

    @Override
    public List<Group> findByCompetenceId(Long competenceId, boolean onlyOpened) {
        if (onlyOpened) {
            return findEntityList(Group.FIND_OPENED_GROUPS,
                    competenceId, new Date());
        }
        return findEntityList(Group.FIND_GROUPS, competenceId);
    }

    @Override
    public void addUser(Long userId, Long groupId) {
        User user = userDao.findById(User.class, userId);
        Group aGroup = findById(Group.class, groupId);
        aGroup.setUsers(Stream.of(user).collect(toSet()));
    }

    @Override
    public List<User> userList(Long groupId) {
        return new ArrayList<>(findById(Group.class, groupId).getUsers());
    }

    @Override
    public Group findGroupByName(String name) {
        return findEntity(Group.GET_GROUP_BY_NAME, name);
    }
}
