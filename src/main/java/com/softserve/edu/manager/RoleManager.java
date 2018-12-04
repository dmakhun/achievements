package com.softserve.edu.manager;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import java.util.List;

public interface RoleManager {

    /**
     * Find users with specified role id.
     *
     * @param roleId Role id.
     * @return List of users with current role.
     */
    List<User> findUsers(int roleId);

    Role findById(Long id);

    Role findRoleByName(String roleName);

}