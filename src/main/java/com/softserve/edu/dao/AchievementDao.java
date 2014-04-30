package com.softserve.edu.dao;

import java.util.List;

import com.softserve.edu.entity.Achievement;

public interface AchievementDao extends GenericDao<Achievement> {

	/**
	 * List of all achievements of current user.
	 * 
	 * @param userId
	 * @return list of users
	 */
	public List<Achievement> findByUserId(Long userId);

	/**
	 * @param userUuiId
	 * @return
	 */
	public List<Achievement> findByUserUuid(String userUuiId);
}
