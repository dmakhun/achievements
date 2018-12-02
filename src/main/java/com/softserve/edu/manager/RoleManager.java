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

    /**
     * @param roleName role we need.
     * @return id of current role
     */
    Long findRole(String roleName);

    /**
     *
     */
    List<User> findUsersByRoleUuid(String roleUuid);

    /**
     *
     */
    Role findById(Long id);

    /**
     *
     */
    Role findRoleByRolename(String roleName);

    /**
     *
     */
    Role findRoleByUuid(String roleUuid);

    /**
     *
     */
    List<Role> findAll();
}