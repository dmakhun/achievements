package com.softserve.edu.manager;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.exception.AchievementTypeManagerException;

import java.util.List;

public interface AchievementTypeManager {

    /**
     * Create new achievement type.
     *
     * @param name
     * @param points
     * @param competenceId
     * @throws AchievementTypeManagerException
     */
    AchievementType create(String name, int points, long competenceId)
            throws AchievementTypeManagerException;

    /**
     * Create new achievement type.
     *
     * @param name
     * @param points
     * @param competenceUuid
     * @throws AchievementTypeManagerException
     */
    AchievementType create(final String name, final int points, final String competenceUuid) throws AchievementTypeManagerException;

    /**
     * Delete existing achievement type by its id.
     *
     * @param achievementTypeId Achievement type id.
     * @throws AchievementTypeManagerException
     */
    boolean deleteById(final long achievementTypeId)
            throws AchievementTypeManagerException;

    /**
     * Deletes achievement type by its uuid.
     *
     * @param uuid
     * @return
     * @throws AchievementTypeManagerException
     */
    boolean deleteByUuid(final String uuid)
            throws AchievementTypeManagerException;

    /**
     * @return
     */
    List<AchievementType> achievementTypeList();

    /**
     * @param idCompetence
     * @return
     */
    List<AchievementType> findAchievements(Long idCompetence);

}
