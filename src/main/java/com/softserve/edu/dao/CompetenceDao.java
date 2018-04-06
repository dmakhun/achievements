package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;

import java.util.List;

public interface CompetenceDao extends GenericDao<Competence> {

    /**
     * Returns all groups in a certain competence.
     *
     * @param groupId
     * @return list of groups
     */
    List<Group> showGroups(int groupId);

    /**
     * Finds all groups of a specific competence
     *
     * @param competenceUuid
     * @return
     */
    List<Group> findGroupsByCompetenceUuid(final String competenceUuid);

    /**
     * Find competence with certain name.
     *
     * @param name of comp you need
     * @return competence
     */

    Competence findByName(String name);

    /**
     * Get list of competences, that user want attend to.
     *
     * @param userId
     * @return
     */
    List<Competence> findCompetencesByUserId(final Long userId);

    /**
     * Finds the list of the competences of the specific user by user's UUID.
     *
     * @param userUuid
     * @return
     */
    List<Competence> findByUserUuid(final String userUuid);

    /**
     * Get competences with loaded users to them.
     *
     * @return list of competences
     */
    List<Competence> findAllCompetences();

}
