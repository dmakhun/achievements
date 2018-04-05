package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import com.softserve.edu.manager.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("roleManager")
public class RoleManagerImplementation implements RoleManager {
    @Autowired
    RoleDao roleDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> findUsers(int roleId) {

        return roleDao.findUsers(roleId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Long findRole(String roleName) {

        return roleDao.findRole(roleName);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Role findRoleByRolename(String roleName) {

        return roleDao.findRoleByRolename(roleName);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> findUsersByRoleUuid(String roleUuid) {
        return roleDao.findUsersByRoleUuid(roleUuid);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Role findById(Long id) {
        return roleDao.findById(Role.class, id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Role findRoleByUuid(String roleUuid) {
        return roleDao.findByUuid(Role.class, roleUuid);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Role> findAll() {
        return roleDao.findAll(Role.class);
    }
}