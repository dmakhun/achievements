package com.softserve.edu.dao.impl;

import static java.util.stream.Collectors.toSet;

import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User>
        implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private GroupDao groupDao;

    @Override
    public User findById(java.lang.Class userClass, long id) {
        return super.findById(userClass, id);
    }

    @Override
    public void attendUserToCompetence(User user, Competence competence) {
        user.setCompetences(Stream.of(competence).collect(toSet()));
    }

    @Override
    public void removeUserToCompetence(User user, Competence competence) {
        user.getCompetences().remove(competence);
        competence.getUsers().remove(user);
    }

    @Override
    public User findByUsername(String username) {
        try {
            if (StringUtils.isEmpty(username)) {
                return null;
            }
            return findEntity(User.FIND_USER_BY_USERNAME, username);
        } catch (Exception e) {
            logger.error("Error finding user by it's username", e);
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return findEntity(User.FIND_USER_BY_EMAIL, email);
    }

    @Override
    public List<Group> findGroups(Long userId, boolean onlyOpened) {
        if (onlyOpened) {
            return groupDao.findEntityList(User.FIND_ONLY_OPENED_GROUPS, userId, new Date());
        }
        return new ArrayList<>(findById(User.class, userId).getaGroups());
    }

    @Override
    public List<User> findByRole(String role) {
        return findEntityList(User.FIND_ALL_USERS_BY_ROLE, roleDao.findRoleByName(role).getId());
    }
}
