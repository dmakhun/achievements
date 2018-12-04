package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.manager.GroupManager;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("groupManager")
public class GroupManagerImplementation implements GroupManager {

    private static final Logger logger = LoggerFactory
            .getLogger(GroupManagerImplementation.class);

    @Autowired
    GroupDao groupDao;

    @Autowired
    CompetenceDao competenceDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> inFuture() {
        return groupDao.findGroupsToBeOpened();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> inFuture(Long competenceId) {
        return groupDao.findGroupsToBeOpened(competenceId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> findAllByCompetenceId(Long competenceId, boolean onlyOpened) {
        return groupDao.findByCompetence(competenceId, onlyOpened);
    }

    @Override
    @Transactional
    public Long create(String name, Date startDate, Date endDate,
            Long competenceId) throws GroupManagerException {

        Competence competence = competenceDao.findById(Competence.class,
                competenceId);

        Group group = new Group();
        group.setName(name);
        group.setDateOpened(startDate);
        group.setDateClosed(endDate);
        group.setCompetence(competence);

        try {
            groupDao.save(group);
            logger.info("Group was saved");
        } catch (Exception e) {
            logger.error("cannot save group", e);
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
        group.setDateOpened(start);
        group.setDateClosed(end);
        group.setCompetence(competence);
        try {
            groupDao.update(group);
            logger.info("Group was updated");
        } catch (Exception e) {
            logger.error("cannot updateUser group", e);
            throw new GroupManagerException("cannot updateUser group", e);
        }
    }

    @Override
    @Transactional
    public void create(Group group) throws GroupManagerException {
        if (!validateGroup(group)) {
            logger.warn("Group is not valid. Try another arguments");
            throw new GroupManagerException(
                    "Group is not valid. Try another arguments");
        }
        try {
            groupDao.save(group);
            logger.info("Group was created");
        } catch (Exception e) {
            logger.error("Cannot createAchievementType group", e);
            throw new GroupManagerException("Cannot createAchievementType group", e);
        }
    }

    @Override
    @Transactional
    public void addUser(Long userId, Long groupId) throws GroupManagerException {
        try {
            groupDao.addUser(userId, groupId);
            logger.info("User was added");
        } catch (Exception e) {
            logger.error("cannot add user to group", e);
            throw new GroupManagerException("cannot add user to group", e);
        }
    }

    @Override
    @Transactional
    public void addUser(String userUuid, String groupUuid)
            throws GroupManagerException {
        try {
            groupDao.addUser(userUuid, groupUuid);
            logger.info("User was added");
        } catch (Exception e) {
            logger.error("cannot add user to group", e);
            throw new GroupManagerException("cannot add user to group", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> users(Long groupId) {

        return groupDao.userList(groupId);
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) throws GroupManagerException {
        Group group = groupDao.findById(Group.class, groupId);
        removeAssociation(group);
        try {
            groupDao.delete(group);
            logger.info("Group was deleted by id");
        } catch (Exception e) {
            logger.error("cannot deleteAchievementType group", e);
            throw new GroupManagerException("cannot deleteAchievementType group", e);
        }
    }

    @Override
    @Transactional
    public boolean validateGroup(Group group) {
        boolean valid = false;
        Group groupInDB = findGroupByGroupName(group.getName());
        if (groupInDB != null) {
            logger.warn("Such group is existed in database");
            return false;
        }
        if (group.getName().length() > 3 && group.getName().length() < 30) {
            if (group.getDateClosed().after(group.getDateOpened())) {
                logger.info("Group is valid");
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

        logger.info("Group association was removed");
    }

}
