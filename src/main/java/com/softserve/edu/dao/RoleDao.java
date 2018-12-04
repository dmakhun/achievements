package com.softserve.edu.dao;

import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.entity.User;
import java.util.List;

public interface RoleDao extends GenericDao<AccessRole> {

    /**
     * Find users with current role id.
     *
     * @return list of users
     */
    List<User> findUsersById(int roleId);

    AccessRole findRoleByName(String roleName);
}
