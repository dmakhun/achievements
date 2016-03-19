package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("groupDao")
public class GroupDaoImplementation extends GenericDaoImplementation<Group>
        implements GroupDao {

    @Autowired
    UserDao userDao;

    public List<Group> inFuture() {
        Date today = new Date();
        return findEntityList(Group.SHOW_GROUPS_OPENED_IN_FUTURE, today);

    }

    @Override
    public List<Group> inFuture(Long competenceId) {
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
        User user = entityManager.find(User.class, userId);
        Group group = entityManager.find(Group.class, groupId);
        group.getUsers().add(user);
    }

    @Override
    public void addUser(String userUuid, String groupUuid) {

        User user = userDao.findEntity(Group.ADD_GROUP_TO_USER, userUuid);
        Group group = this.findEntity(Group.ADD_USER_TO_GROUP, groupUuid);
        group.getUsers().add(user);
    }

    @Override
    public List<User> userList(Long groupId) {
        return userDao.findEntityList(Group.GET_USER_LIST_IN_GROUP, groupId);
    }

    @Override
    public List<User> findUsersByGroupUuid(String groupUuid) {
        return userDao.findEntityList(Group.FIND_USER_BY_GROUP_UUID, groupUuid);
    }

    @Override
    public Group findGroupByName(String name) {
        return this.findEntity(Group.GET_GROUP_BY_NAME, name);
    }
}
