package com.softserve.edu.manager;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;

import java.util.List;

public interface AchievementTypeManager {

    AchievementType create(final Competence competence, final String name, final int points) throws AchievementTypeManagerException;

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
    boolean delete(final long achievementTypeId)
            throws AchievementTypeManagerException;

    /**
     * Deletes achievement type by its uuid.
     *
     * @param uuid
     * @return
     * @throws AchievementTypeManagerException
     */
    boolean delete(final String uuid)
            throws AchievementTypeManagerException;

    List<AchievementType> achievementTypesList();

    List<AchievementType> findAchievements(final long idCompetence);

}
