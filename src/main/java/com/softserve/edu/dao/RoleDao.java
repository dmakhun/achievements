package com.softserve.edu.dao;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;

import java.util.List;

public interface RoleDao extends GenericDao<Role> {

    /**
     * Find users with current role id.
     *
     * @param roleId
     * @return list of users
     */
    List<User> findUsers(int roleId);

    /**
     * Find users with current role name.
     *
     * @param roleName
     * @return list of users
     */
    Long findRoleId(String roleName);

    /**
     * @param roleUuid
     * @return
     */
    List<User> findUsersByRoleUuid(String roleUuid);

    /**
     * @param roleName
     * @return
     */
    Role findRoleByName(String roleName);
}
