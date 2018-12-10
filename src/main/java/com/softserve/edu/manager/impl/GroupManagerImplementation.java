package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.ClassDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
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
    ClassDao classDao;

    @Autowired
    CompetenceDao competenceDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Class> inFuture() {
        return classDao.findGroupsToBeOpened();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Class> inFuture(Long competenceId) {
        return classDao.findGroupsToBeOpened(competenceId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Class> findAllByCompetenceId(Long competenceId, boolean onlyOpened) {
        return classDao.findByCompetence(competenceId, onlyOpened);
    }

    @Override
    @Transactional
    public Long create(String name, Date startDate, Date endDate,
            Long competenceId) throws GroupManagerException {

        Competence competence = competenceDao.findById(Competence.class,
                competenceId);

        Class aClass = new Class();
        aClass.setName(name);
        aClass.setDateOpened(startDate);
        aClass.setDateClosed(endDate);
        aClass.setCompetence(competence);

        try {
            classDao.save(aClass);
            logger.info("Class was saved");
        } catch (Exception e) {
            logger.error("cannot save aClass", e);
            throw new GroupManagerException("cannot save aClass", e);
        }
        return aClass.getId();
    }

    @Override
    @Transactional
    public void modify(final Long groupId, final String name, final Date start,
            final Date end, final Long competenceId)
            throws GroupManagerException {

        Competence competence = competenceDao.findById(Competence.class,
                competenceId);
        Class aClass = classDao.findById(Class.class, groupId);
        aClass.setName(name);
        aClass.setDateOpened(start);
        aClass.setDateClosed(end);
        aClass.setCompetence(competence);
        try {
            classDao.update(aClass);
            logger.info("Class was updated");
        } catch (Exception e) {
            logger.error("cannot updateUser aClass", e);
            throw new GroupManagerException("cannot updateUser aClass", e);
        }
    }

    @Override
    @Transactional
    public void create(Class aClass) throws GroupManagerException {
        if (!validateGroup(aClass)) {
            logger.warn("Class is not valid. Try another arguments");
            throw new GroupManagerException(
                    "Class is not valid. Try another arguments");
        }
        try {
            classDao.save(aClass);
            logger.info("Class was created");
        } catch (Exception e) {
            logger.error("Cannot createAchievementType aClass", e);
            throw new GroupManagerException("Cannot createAchievementType aClass", e);
        }
    }

    @Override
    @Transactional
    public void addUser(Long userId, Long groupId) throws GroupManagerException {
        try {
            classDao.addUser(userId, groupId);
            logger.info("User was added");
        } catch (Exception e) {
            logger.error("cannot add user to group", e);
            throw new GroupManagerException("cannot add user to group", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> users(Long groupId) {

        return classDao.userList(groupId);
    }

    @Override
    @Transactional
    public void deleteById(Long groupId) throws GroupManagerException {
        Class aClass = classDao.findById(Class.class, groupId);
        removeAssociation(aClass);
        try {
            classDao.delete(aClass);
            logger.info("Class was deleted by id");
        } catch (Exception e) {
            logger.error("cannot deleteAchievementType aClass", e);
            throw new GroupManagerException("cannot deleteAchievementType aClass", e);
        }
    }

    @Override
    @Transactional
    public boolean validateGroup(Class aClass) {
        boolean valid = false;
        Class classInDB = findGroupByGroupName(aClass.getName());
        if (classInDB != null) {
            logger.warn("Such aClass is existed in database");
            return false;
        }
        if (aClass.getName().length() > 3 && aClass.getName().length() < 30) {
            if (aClass.getDateClosed().after(aClass.getDateOpened())) {
                logger.info("Class is valid");
                valid = true;
            }
        }
        return valid;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Class findGroupByGroupName(String groupName) {
        String singleQuery = "from Class where name like ?1";
        Class aClass = classDao.findEntity(singleQuery, groupName);
        return aClass;
    }

    @Override
    @Transactional
    public void removeAssociation(Class aClass) {

        Competence competence = aClass.getCompetence();
        competence.getClasses().remove(aClass);

        Set<User> users = aClass.getUsers();
        for (User u : users) {
            u.getaClasses().remove(aClass);
        }
        users.clear();
        aClass.setUsers(users);

        logger.info("Class association was removed");
    }

}
