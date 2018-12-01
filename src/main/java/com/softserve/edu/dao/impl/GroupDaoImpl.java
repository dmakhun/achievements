package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Repository("groupDao")
public class GroupDaoImpl extends GenericDaoImpl<Group>
        implements GroupDao {

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
    public List<Group> findByCompetenceUuid(String competenceUuid,
                                            boolean onlyOpened) {
        if (onlyOpened) {
            return findEntityList(Group.FIND_ONLY_OPENED_GROUPS_UUID,
                    competenceUuid, new Date());
        }
        return findEntityList(Group.FIND_LIST_GROUPS_BY_COMPETENCE_UUID,
                competenceUuid);
    }

    @Override
    public void addUser(Long userId, Long groupId) {
        User user = userDao.findById(User.class, userId);
        Group group = findById(Group.class, groupId);
        group.setUsers(Stream.of(user).collect(toSet()));
    }

    @Override
    public void addUser(String userUuid, String groupUuid) {
        User user = userDao.findByUuid(User.class, userUuid);
        Group group = findByUuid(Group.class, groupUuid);
        group.setUsers(Stream.of(user).collect(toSet()));
    }

    @Override
    public List<User> userList(Long groupId) {
        return new ArrayList<>(findById(Group.class, groupId).getUsers());
    }

    @Override
    public List<User> findUsersByGroupUuid(String groupUuid) {
        return new ArrayList<>(findByUuid(Group.class, groupUuid).getUsers());
    }

    @Override
    public Group findGroupByName(String name) {
        return findEntity(Group.GET_GROUP_BY_NAME, name);
    }
}
