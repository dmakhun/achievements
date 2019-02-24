package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.dao.GroupRepository;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.manager.GroupManager;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("groupManager")
public class GroupManagerImpl implements GroupManager {

    private static final Logger logger = LoggerFactory.getLogger(GroupManagerImpl.class);

    @Autowired
    CompetenceRepository competenceRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public Long create(String name, LocalDate startDate, LocalDate endDate, Long competenceId) {

        Competence competence = competenceRepository.findById(competenceId).get();

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
    public void modify(Long groupId, String name, LocalDate startDate,
            LocalDate endDate, Long competenceId) throws GroupManagerException {

        Competence competence = competenceRepository.findById(competenceId).get();
        Group group = groupRepository.findById(groupId).get();
        group.setName(name);
        group.setDateOpened(startDate);
        group.setDateClosed(endDate);
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
            User user = userRepository.findById(userId).get();
            Group group = groupRepository.findById(groupId).get();
            user.addGroup(group);
            logger.info("User was added");
        } catch (Exception e) {
            logger.error("cannot add user to group ", e);
            throw new GroupManagerException("cannot add user to group", e);
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
            if (group.getDateClosed().isAfter(group.getDateOpened())) {
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
            user.getGroups().remove(group);
        }
        group.setUsers(Collections.emptySet());

        logger.info("Group association was removed");
    }

}
