package com.softserve.edu.manager;

import com.softserve.edu.entity.Achievement;
import com.softserve.edu.exception.CompetenceManagerException;

import java.util.List;

/**
 * @author Nazar
 */
public interface AchievementManager {

    /**
     * Award user with given achievement type.
     *
     * @param userId
     * @param achievementTypeId
     * @param comment
     * @throws CompetenceManagerException
     */
    public void awardUser(long userId, long achievementTypeId,
                          String comment) throws CompetenceManagerException;

    /**
     * @param userId
     * @return
     */
    public List<Achievement> findUserAchievements(Long userId);

    /**
     * @param achievementUuid
     * @return
     */
    public List<Achievement> findAchievementsByUserUuid(String achievementUuid);
}
