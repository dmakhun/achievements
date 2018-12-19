package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.manager.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("roleManager")
public class RoleManagerImplementation implements RoleManager {

    @Autowired
    RoleDao roleDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AccessRole findRoleByName(String roleName) {
        return roleDao.findRoleByName(roleName);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AccessRole findById(Long id) {
        return roleDao.findById(AccessRole.class, id);
    }

}