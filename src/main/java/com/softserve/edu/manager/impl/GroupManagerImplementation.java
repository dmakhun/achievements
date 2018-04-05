package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.manager.GroupManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service("groupManager")
public class GroupManagerImplementation implements GroupManager {

    private static final Logger LOGGER = Logger
            .getLogger(GroupManagerImplementation.class);

    @Autowired
    GroupDao groupDao;

    @Autowired
    CompetenceDao competenceDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> inFuture() {
        return groupDao.inFuture();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> inFuture(Long competenceId) {
        return groupDao.inFuture(competenceId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> findByCompetence(Long competenceId, boolean onlyOpened) {
        return groupDao.findByCompetence(competenceId, onlyOpened);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> findByCompetenceUuid(String competenceUuid,
                                            boolean onlyOpened) {
        return groupDao.findByCompetenceUuid(competenceUuid, onlyOpened);
    }

    @Override
    @Transactional
    public Long create(String name, Date startDate, Date endDate,
                       Long competenceId) throws GroupManagerException {

        Competence competence = competenceDao.findById(Competence.class,
                competenceId);

        Group group = new Group();
        group.setName(name);
        group.setOpened(startDate);
        group.setClosed(endDate);
        group.setCompetence(competence);

        try {
            groupDao.save(group);
            LOGGER.info("Group was saved");
        } catch (Exception e) {
            LOGGER.error("cannot save group", e);
            throw new GroupManagerException("cannot save group", e);
        }
        return group.getId();
    }

    @Override
    @Transactional
    public void modify(final Long groupId, final String name, final Date start,
                       final Date end, final Long competenceId)
            throws GroupManagerException {

        Competence competence = competenceDao.findById(Competence.class,
                competenceId);
        Group group = groupDao.findById(Group.class, groupId);
        group.setName(name);
        group.setOpened(start);
        group.setClosed(end);
        group.setCompetence(competence);
        try {
            groupDao.update(group);
            LOGGER.info("Group was updated");
        } catch (Exception e) {
            LOGGER.error("cannot update group", e);
            throw new GroupManagerException("cannot update group", e);
        }
    }

    @Override
    @Transactional
    public void create(Group group) throws GroupManagerException {
        if (!validateGroup(group)) {
            LOGGER.warn("Group is not valid. Try another arguments");
            throw new GroupManagerException(
                    "Group is not valid. Try another arguments");
        }
        try {
            groupDao.save(group);
            LOGGER.info("Group was created");
        } catch (Exception e) {
            LOGGER.error("Cannot create group", e);
            throw new GroupManagerException("Cannot create group", e);
        }
    }

    @Override
    @Transactional
    public void addUser(Long userId, Long groupId) throws GroupManagerException {
        try {
            groupDao.addUser(userId, groupId);
            LOGGER.info("User was added");
        } catch (Exception e) {
            LOGGER.error("cannot add user to group", e);
            throw new GroupManagerException("cannot add user to group", e);
        }
    }

    @Override
    @Transactional
    public void addUser(String userUuid, String groupUuid)
            throws GroupManagerException {
        try {
            groupDao.addUser(userUuid, groupUuid);
            LOGGER.info("User was added");
        } catch (Exception e) {
            LOGGER.error("cannot add user to group", e);
            throw new GroupManagerException("cannot add user to group", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> users(Long groupId) {

        return groupDao.userList(groupId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> findUsersByGroupUuid(String groupUuid) {
        return groupDao.findUsersByGroupUuid(groupUuid);
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) throws GroupManagerException {
        Group toDelete = groupDao.findById(Group.class, groupId);
        try {
            groupDao.delete(toDelete);
            LOGGER.info("Group was deleted by id");
        } catch (Exception e) {
            LOGGER.error("cannot delete group", e);
            throw new GroupManagerException("cannot delete group", e);
        }
    }

    @Override
    @Transactional
    public void deleteByUuid(String groupUuid) throws GroupManagerException {
        Group group = groupDao.findByUuid(Group.class, groupUuid);
        removeAssociation(group);
        try {
            groupDao.delete(group);
            LOGGER.info("Group was deleted by uuid");
        } catch (Exception e) {
            LOGGER.error("cannot delete group", e);
            throw new GroupManagerException("cannot delete group", e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Group findGroupByGroupUuid(String uuid) {
        Group group = groupDao.findByUuid(Group.class, uuid);
        if (group == null) {
            LOGGER.error("User with such uuid doesn't exist.");
        } else {
            LOGGER.info("User was found");
        }
        return group;
    }

    @Override
    @Transactional
    public boolean validateGroup(Group group) {
        boolean valid = false;
        Group groupInDB = findGroupByGroupName(group.getName());
        if (groupInDB != null) {
            LOGGER.warn("Such group is existed in database");
            return false;
        }
        if (group.getName().length() > 3 && group.getName().length() < 30) {
            if (group.getClosed().after(group.getOpened())) {
                LOGGER.info("Group is valid");
                valid = true;
            }
        }
        return valid;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Group findGroupByGroupName(String groupName) {
        String singleQuery = "from Group where name like ?1";
        Group group = groupDao.findEntity(singleQuery, groupName);
        return group;
    }

    @Override
    @Transactional
    public void removeAssociation(Group group) {

        Competence competence = group.getCompetence();
        competence.getGroups().remove(group);

        Set<User> users = group.getUsers();
        for (User u : users) {
            u.getGroups().remove(group);
        }
        users.clear();
        group.setUsers(users);

        LOGGER.info("Group association was removed");
    }

}
