package com.softserve.edu.manager;

import com.softserve.edu.entity.Achievement;
import com.softserve.edu.exception.AchievementManagerException;
import java.util.List;

public interface AchievementManager {

    /**
     * Award user with achievement.
     */
    void awardUser(long userId, long achievementTypeId,
            String comment) throws AchievementManagerException;

    List<Achievement> findUserAchievementsByUserId(Long userId);
}
