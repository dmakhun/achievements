package com.softserve.edu.dao.impl;

import static java.util.stream.Collectors.toSet;

import com.softserve.edu.dao.ClassDao;
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
public class ClassDaoImpl extends GenericDaoImpl<Group> implements ClassDao {

    @Autowired
    private UserDao userDao;

    public List<Group> findGroupsToBeOpened() {
        return findEntityList(Group.SHOW_GROUPS_OPENED_IN_FUTURE, new Date());
    }

    @Override
    public List<Group> findGroupsToBeOpened(Long competenceId) {
        return findEntityList(
                Group.SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE, new Date(),
                competenceId);
    }

    @Override
    public List<Group> findByCompetence(Long competenceId, boolean onlyOpened) {
        if (onlyOpened) {
            return findEntityList(Group.FIND_ONLY_OPENED_GROUPS,
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
