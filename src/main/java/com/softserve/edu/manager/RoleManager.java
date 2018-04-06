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
     * @param roleUuid
     * @return
     */
    List<User> findUsersByRoleUuid(String roleUuid);

    /**
     * @param id
     * @return
     */
    Role findById(Long id);

    /**
     * @param roleName
     * @return
     */
    Role findRoleByRolename(String roleName);

    /**
     * @param roleUuid
     * @return
     */
    Role findRoleByUuid(String roleUuid);

    /**
     * @return
     */
    List<Role> findAll();
}