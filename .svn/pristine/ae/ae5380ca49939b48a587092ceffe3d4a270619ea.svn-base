package com.softserve.edu.dao;

import java.util.List;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;

public interface RoleDao extends GenericDao<Role> {

	/**
	 * Find users with current role id.
	 * 
	 * @param role
	 *            id
	 * @return list of users
	 */
	public List<User> findUsers(int roleId);

	/**
	 * Find users with current role name.
	 * 
	 * @param role
	 *            name
	 * 
	 * @return list of users
	 */
	public Long findRole(String roleName);

	/**
	 * @param roleUuid
	 * @return
	 */
	public List<User> findUsersByRoleUuid(String roleUuid);

	/**
	 * @param roleName
	 * @return
	 */
	public Role findRoleByRolename(String roleName);
}
