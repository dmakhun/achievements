package com.softserve.edu.manager;

import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.entity.User;
import java.util.List;

public interface RoleManager {

    /**
     * Find users with specified role id.
     *
     * @param roleId AccessRole id.
     * @return List of users with current role.
     */
    List<User> findUsers(int roleId);

    AccessRole findById(Long id);

    AccessRole findRoleByName(String roleName);

}