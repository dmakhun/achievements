package com.softserve.edu.manager;

import com.softserve.edu.entity.Achievement;
import com.softserve.edu.exception.CompetenceManagerException;

import java.util.List;

public interface AchievementManager {

    /**
     * Award user with given achievement type.
     *
     * @param userId
     * @param achievementTypeId
     * @param comment
     * @throws CompetenceManagerException
     */
    void awardUser(long userId, long achievementTypeId,
                   String comment) throws CompetenceManagerException;

    /**
     * @param userId
     * @return
     */
    List<Achievement> findUserAchievements(Long userId);

    /**
     * @param achievementUuid
     * @return
     */
    List<Achievement> findAchievementsByUserUuid(String achievementUuid);
}
