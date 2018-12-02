package com.softserve.edu.manager;

import com.softserve.edu.entity.Achievement;
import com.softserve.edu.exception.CompetenceManagerException;
import java.util.List;

public interface AchievementManager {

    /**
     * Award user with given achievement type.
     */
    void awardUser(long userId, long achievementTypeId,
            String comment) throws CompetenceManagerException;

    /**
     *
     */
    List<Achievement> findUserAchievements(Long userId);

    /**
     *
     */
    List<Achievement> findAchievementsByUserUuid(String achievementUuid);
}
