package com.softserve.edu.dao;

import com.softserve.edu.entity.AccessRole;

public interface RoleDao extends GenericDao<AccessRole> {

    AccessRole findRoleByName(String roleName);
}
