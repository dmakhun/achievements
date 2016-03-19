package com.softserve.edu.dao;

import com.softserve.edu.entity.Achievement;

import java.util.List;

public interface AchievementDao extends GenericDao<Achievement> {

    /**
     * List of all achievements of current user.
     *
     * @param userId
     * @return list of users
     */
    public List<Achievement> findAchievementsByUserId(Long userId);

    /**
     * @param userUuiId
     * @return
     */
    public List<Achievement> findByUserUuid(String userUuiId);
}
