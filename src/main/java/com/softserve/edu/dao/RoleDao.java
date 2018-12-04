package com.softserve.edu.dao;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import java.util.List;

public interface RoleDao extends GenericDao<Role> {

    /**
     * Find users with current role id.
     *
     * @return list of users
     */
    List<User> findUsersById(int roleId);

    Role findRoleByName(String roleName);
}
