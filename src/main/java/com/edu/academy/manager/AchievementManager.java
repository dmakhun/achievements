package com.edu.academy.manager;

import com.edu.academy.exception.AchievementManagerException;

public interface AchievementManager {

    /**
     * Award user with achievement.
     */
    void awardUser(long userId, long achievementTypeId,
                   String comment) throws AchievementManagerException;

}
