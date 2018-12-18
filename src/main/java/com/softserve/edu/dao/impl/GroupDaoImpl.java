package com.softserve.edu.dao.impl;

import static java.util.stream.Collectors.toSet;

import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDaoImpl extends GenericDaoImpl<Group> implements GroupDao {

    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(Long userId, Long groupId) {
        User user = userDao.findById(User.class, userId);
        Group group = findById(Group.class, groupId);
        group.setUsers(Stream.of(user).collect(toSet()));
    }

    @Override
    public List<User> userList(Long groupId) {
        return new ArrayList<>(findById(Group.class, groupId).getUsers());
    }

}
