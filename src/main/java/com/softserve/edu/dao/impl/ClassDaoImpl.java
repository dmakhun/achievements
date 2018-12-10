package com.softserve.edu.dao.impl;

import static java.util.stream.Collectors.toSet;

import com.softserve.edu.dao.ClassDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class ClassDaoImpl extends GenericDaoImpl<Class> implements ClassDao {

    @Autowired
    private UserDao userDao;

    public List<Class> findGroupsToBeOpened() {
        return findEntityList(Class.SHOW_GROUPS_OPENED_IN_FUTURE, new Date());

    }

    @Override
    public List<Class> findGroupsToBeOpened(Long competenceId) {
        return findEntityList(
                Class.SHOW_GROUPS_OPENED_IN_FUTURE_BY_COMPETENCE, new Date(),
                competenceId);
    }

    @Override
    public List<Class> findByCompetence(Long competenceId, boolean onlyOpened) {
        if (onlyOpened) {
            return findEntityList(Class.FIND_ONLY_OPENED_GROUPS,
                    competenceId, new Date());
        }
        return findEntityList(Class.FIND_GROUPS, competenceId);
    }

    @Override
    public void addUser(Long userId, Long groupId) {
        User user = userDao.findById(User.class, userId);
        Class aClass = findById(Class.class, groupId);
        aClass.setUsers(Stream.of(user).collect(toSet()));
    }

    @Override
    public List<User> userList(Long groupId) {
        return new ArrayList<>(findById(Class.class, groupId).getUsers());
    }

    @Override
    public Class findGroupByName(String name) {
        return findEntity(Class.GET_GROUP_BY_NAME, name);
    }
}
