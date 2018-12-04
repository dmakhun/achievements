package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoImpl<AccessRole>
        implements RoleDao {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findUsersById(int roleId) {
        return userDao.findEntityList(AccessRole.FIND_USERS_BY_ROLE_ID, roleId);
    }

    @Override
    public AccessRole findRoleByName(String roleName) {
        return findEntity(AccessRole.FIND_ROLE_BY_NAME, roleName);
    }

}
