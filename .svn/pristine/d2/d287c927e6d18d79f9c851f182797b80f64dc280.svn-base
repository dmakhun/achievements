package com.softserve.edu.manager;

import java.util.List;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.CompetenceManagerException;

public interface CompetenceManager {

	/**
	 * returns all groups in a certain competence.
	 * 
	 * @param CompetenceId
	 * @return
	 */
	public List<Group> findGroups(int CompetenceId);
	
	/**
	 * Finds all groups of a specific competence by its uuid
	 * @param competenceUuid
	 * @return
	 */
	public List<Group> findGroupsByCompetenceUuid(final String competenceUuid);

	/**
	 * 
	 * @return all list of competenceies.
	 */
	public List<Competence> findAllCompetences();

	/**
	 * return all type of achievements in some competence.
	 * 
	 * @param idCompetence
	 * @return List<AchievementType>
	 */
	public List<AchievementType> findTypesOfAchievements(Long idCompetence);

	/**
	 * @param conpetenceUuid
	 * @return
	 */
	public List<AchievementType> findAchievementTypesByCompetenceUuid(
			String conpetenceUuid);

	/**
	 * Get list of competences but those, that are given.
	 * 
	 * @param buts
	 * @return
	 */
	public List<Competence> listWithout(List<Competence> buts);

	/**
	 * Get list of competences, that user want attend to.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Competence> findByUser(final Long userId);

	/**
	 * Finds the list of the competences of the specific user by user's UUID.
	 * 
	 * @param userUuid
	 * @return list of competences
	 */
	public List<Competence> findByUserUuid(final String userUuid);

	/**
	 * Creates a new competence with current name.
	 * 
	 * @param name
	 *            of competence
	 * @return competence
	 * @throws CompetenceManagerException 
	 */
	public Competence create(String name) throws CompetenceManagerException;

	/**
	 * Deletes competence.
	 * 
	 * @param id
	 *            of competence
	 * @return boolean delete
	 * @throws CompetenceManagerException 
	 */
	public boolean delete(Long id) throws CompetenceManagerException;
	
	/**
	 * Deletes competence.
	 * 
	 * @param uuid
	 *            of competence
	 * @return boolean delete
	 * @throws CompetenceManagerException 
	 */
	public boolean deleteByUuid(String uuid) throws CompetenceManagerException;

	/**
	 * @param objectId
	 * @return
	 */
	public Competence findByID(Long objectId);

	/**
	 * @param name
	 * @return
	 */
	public boolean validateCompetenceName(String name);

}