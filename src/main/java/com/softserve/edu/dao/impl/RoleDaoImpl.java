package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.entity.AccessRole;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoImpl<AccessRole>
        implements RoleDao {

    @Override
    public AccessRole findRoleByName(String roleName) {
        return findEntity(AccessRole.FIND_ROLE_BY_NAME, roleName);
    }

}
