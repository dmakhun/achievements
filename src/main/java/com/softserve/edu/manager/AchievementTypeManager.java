package com.softserve.edu.manager;

import java.util.List;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.exception.AchievementTypeManagerException;

public interface AchievementTypeManager {

	/**
	 * Create new achievement type.
	 * 
	 * @param name
	 * @param points
	 * @param competence_id
	 * @throws AchievementTypeManagerException
	 */
	public AchievementType create(String name, int points, long competenceId)
			throws AchievementTypeManagerException;

	/**
	 * Create new achievement type.
	 * 
	 * @param name
	 * @param points
	 * @param competenceUuid
	 * @throws AchievementTypeManagerException
	 */
	public AchievementType create(final String name, final int points,
			final String competenceUuid) throws AchievementTypeManagerException;

	/**
	 * Delete existing achievement type by its id.
	 * 
	 * @param achievementTypeId
	 *            Achievement type id.
	 * @throws AchievementTypeManagerException
	 */
	public boolean deleteById(final long achievementTypeId)
			throws AchievementTypeManagerException;

	/**
	 * Deletes achievement type by its uuid.
	 * 
	 * @param uuid
	 * @return
	 * @throws AchievementTypeManagerException
	 */
	public boolean deleteByUuid(final String uuid)
			throws AchievementTypeManagerException;

	/**
	 * Get all achievement types.
	 * 
	 * @return FIXME: some shit that need to be deleted.
	 */

	/**
	 * @return
	 */
	public List<AchievementType> achievementTypeList();

	/**
	 * @param idCompetence
	 * @return
	 */
	public List<AchievementType> findAchievements(Long idCompetence);

}
