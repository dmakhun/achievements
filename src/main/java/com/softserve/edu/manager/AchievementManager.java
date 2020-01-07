package com.softserve.edu.manager;

import com.softserve.edu.exception.AchievementManagerException;

public interface AchievementManager {

    /**
     * Award user with achievement.
     */
    void awardUser(long userId, long achievementTypeId,
            String comment) throws AchievementManagerException;

}
