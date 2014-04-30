package com.softserve.edu.manager;

import java.util.List;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;

public interface RoleManager {

	/**
	 * Find users with specified role id.
	 * 
	 * @param roleId
	 *            Role id.
	 * @return List of users with current role.
	 */
	public List<User> findUsers(int roleId);

	/**
	 * 
	 * @param name
	 *            of role we need.
	 * @return id of current role
	 */
	public Long findRole(String roleName);

	/**
	 * @param roleUuid
	 * @return
	 */
	List<User> findUsersByRoleUuid(String roleUuid);

	/**
	 * @param id
	 * @return
	 */
	public Role findById(Long id);

	/**
	 * @param roleName
	 * @return
	 */
	public Role findRoleByRolename(String roleName);

	/**
	 * @param roleUuid
	 * @return
	 */
	public Role findRoleByUuid(String roleUuid);

	/**
	 * @return
	 */
	public List<Role> findAll();
}