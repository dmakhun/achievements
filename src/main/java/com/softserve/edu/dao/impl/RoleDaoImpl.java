package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoImpl<Role>
        implements RoleDao {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findUsers(int roleId) {
        return userDao.findEntityList(Role.FIND_USERS_BY_ROLE_ID, roleId);
    }

    @Override
    public Long findRoleId(String roleName) {
        return findRoleByName(roleName).getId();
    }

    @Override
    public Role findRoleByName(String roleName) {
        return findEntity(Role.FIND_ROLE_BY_NAME, roleName);
    }

    @Override
    public List<User> findUsersByRoleUuid(String roleUuid) {
        return userDao.findEntityList(Role.FIND_USERS_BY_ROLE_UUID, roleUuid);
    }

}
