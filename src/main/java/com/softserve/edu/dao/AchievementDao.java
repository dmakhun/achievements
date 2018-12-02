package com.softserve.edu.dao;

import com.softserve.edu.entity.Achievement;
import java.util.List;

public interface AchievementDao extends GenericDao<Achievement> {

    /**
     * List of all achievements of current user.
     *
     * @return list of users
     */
    List<Achievement> findAchievementsByUserId(long userId);

    /**
     *
     */
    List<Achievement> findAchievementsByUserUuid(String userUuiId);
}
