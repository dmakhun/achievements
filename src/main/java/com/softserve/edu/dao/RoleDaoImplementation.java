package com.softserve.edu.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;

@Repository("roleDao")
public class RoleDaoImplementation extends GenericDaoImplementation<Role>
		implements RoleDao {

	@Autowired
	UserDao userDao;

	@Override
	public List<User> findUsers(int roleId) {
		return userDao.findEntityList(Role.FIND_USERS_BY_ROLE_ID, roleId);
	}

	@Override
	public Long findRole(String roleName) {
		return this.findEntity(Role.FIND_ROLE_BY_NAME, roleName).getId();
	}

	@Override
	public Role findRoleByRolename(String roleName) {
		return this.findEntity(Role.FIND_ROLE_BY_ROLE_NAME, roleName);
	}

	@Override
	public List<User> findUsersByRoleUuid(String roleUuid) {
		if (roleUuid == null || roleUuid.isEmpty()) {
			return null;
		}
		return userDao.findEntityList(Role.FIND_USERS_BY_ROLE_UUID, roleUuid);
	}

}
