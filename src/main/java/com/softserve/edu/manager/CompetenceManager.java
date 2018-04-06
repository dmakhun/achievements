package com.softserve.edu.manager;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.CompetenceManagerException;

import java.util.List;

public interface CompetenceManager {

    /**
     * returns all groups in a certain competence.
     *
     * @param CompetenceId
     * @return
     */
    List<Group> findGroups(int CompetenceId);

    /**
     * Finds all groups of a specific competence by its uuid
     *
     * @param competenceUuid
     * @return
     */
    List<Group> findGroupsByCompetenceUuid(final String competenceUuid);

    /**
     * @return all list of competenceies.
     */
    List<Competence> findAllCompetences();

    /**
     * return all type of achievements in some competence.
     *
     * @param idCompetence
     * @return List<AchievementType>
     */
    List<AchievementType> findTypesOfAchievements(Long idCompetence);

    /**
     * @param conpetenceUuid
     * @return
     */
    List<AchievementType> findAchievementTypesByCompetenceUuid(
            String conpetenceUuid);

    /**
     * Get list of competences but those, that are given.
     *
     * @param buts
     * @return
     */
    List<Competence> listWithout(List<Competence> buts);

    /**
     * Get list of competences, that user want attend to.
     *
     * @param userId
     * @return
     */
    List<Competence> findByUser(final Long userId);

    /**
     * Finds the list of the competences of the specific user by user's UUID.
     *
     * @param userUuid
     * @return list of competences
     */
    List<Competence> findByUserUuid(final String userUuid);

    /**
     * Creates a new competence with current name.
     *
     * @param name of competence
     * @return competence
     * @throws CompetenceManagerException
     */
    Competence create(String name) throws CompetenceManagerException;

    /**
     * Deletes competence.
     *
     * @param id of competence
     * @return boolean deleteAchievementType
     * @throws CompetenceManagerException
     */
    boolean delete(Long id) throws CompetenceManagerException;

    /**
     * Deletes competence.
     *
     * @param uuid of competence
     * @return boolean deleteAchievementType
     * @throws CompetenceManagerException
     */
    boolean deleteByUuid(String uuid) throws CompetenceManagerException;

    /**
     * @param objectId
     * @return
     */
    Competence findByID(Long objectId);

    /**
     * @param name
     * @return
     */
    boolean validateCompetenceName(String name);

}