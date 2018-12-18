package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.dao.GroupRepository;
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
public class GroupManagerImpl implements GroupManager {

    private static final Logger logger = LoggerFactory
            .getLogger(GroupManagerImpl.class);

    @Autowired
    GroupDao groupDao;

    @Autowired
    CompetenceDao competenceDao;

    @Autowired
    GroupRepository groupRepository;

    @Override
    @Transactional
    public Long create(String name, Date startDate, Date endDate,
            Long competenceId) {

        Competence competence = competenceDao.findById(Competence.class,
                competenceId);

        Group group = new Group();
        group.setName(name);
        group.setDateOpened(startDate);
        group.setDateClosed(endDate);
        group.setCompetence(competence);
        groupRepository.save(group);
        return group.getId();
    }

    @Override
    @Transactional
    public void modify(final Long groupId, final String name, final Date start,
            final Date end, final Long competenceId)
            throws GroupManagerException {

        Competence competence = competenceDao.findById(Competence.class,
                competenceId);
        Group group = groupRepository.findById(groupId).get();
        group.setName(name);
        group.setDateOpened(start);
        group.setDateClosed(end);
        group.setCompetence(competence);
        try {
            groupRepository.save(group);
            logger.info("Group was updated");
        } catch (Exception e) {
            logger.error("cannot update a group", e);
            throw new GroupManagerException("cannot update a group", e);
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
            groupRepository.save(group);
            logger.info("Group was created");
        } catch (Exception e) {
            logger.error("Cannot create a group", e);
            throw new GroupManagerException("Cannot create a group", e);
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
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> users(Long groupId) {

        return groupDao.userList(groupId);
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) throws GroupManagerException {
        Group group = groupRepository.findById(groupId).get();
        removeAssociation(group);
        try {
            groupRepository.delete(group);
            logger.info("Group was deleted by id");
        } catch (Exception e) {
            logger.error("cannot delete a group", e);
            throw new GroupManagerException("cannot delete a group", e);
        }
    }

    @Override
    @Transactional
    public boolean validateGroup(Group group) {
        boolean isValid = false;
        Group existingGroup = groupRepository.findByName(group.getName());
        if (existingGroup != null) {
            logger.warn("Such group exists already", existingGroup.getName());
            return false;
        }
        if (group.getName().length() > 3 && group.getName().length() < 30) {
            if (group.getDateClosed().after(group.getDateOpened())) {
                logger.info("Group is valid");
                isValid = true;
            }
        }
        return isValid;
    }

    @Override
    @Transactional
    public void removeAssociation(Group group) {

        Competence competence = group.getCompetence();
        competence.getGroups().remove(group);

        Set<User> users = group.getUsers();
        for (User user : users) {
            user.getaGroups().remove(group);
        }
        users.clear();
        group.setUsers(users);

        logger.info("Group association was removed");
    }

}
