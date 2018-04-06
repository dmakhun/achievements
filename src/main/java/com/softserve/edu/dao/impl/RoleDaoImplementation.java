package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roleDao")
public class RoleDaoImplementation extends GenericDaoImplementation<Role>
        implements RoleDao {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findUsers(int roleId) {
        return userDao.findEntityList(Role.FIND_USERS_BY_ROLE_ID, roleId);
    }

    @Override
    public Long findRole(String roleName) {
        return this.findEntity(Role.FIND_ROLE_BY_NAME, roleName).getId();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return this.findEntity(Role.FIND_ROLE_BY_ROLE_NAME, roleName);
    }

    @Override
    public List<User> findUsersByRoleUuid(String roleUuid) {
        if (roleUuid == null || roleUuid.isEmpty()) {
            return null;
        }
        return userDao.findEntityList(Role.FIND_USERS_BY_ROLE_UUID, roleUuid);
    }

}
