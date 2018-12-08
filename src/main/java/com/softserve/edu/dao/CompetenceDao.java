package com.softserve.edu.dao;

import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import java.util.List;

public interface CompetenceDao extends GenericDao<Competence> {

    /**
     * Returns all groups in a certain competence.
     *
     * @return list of groups
     */
    List<Class> findGroupsByCompetenceId(int groupId);

    /**
     * Find competence with certain name.
     *
     * @param name of comp you need
     * @return competence
     */

    Competence findByName(String name);

    /**
     * Get list of competences, that user want attend to.
     */
    List<Competence> findCompetencesByUserId(final Long userId);

    /**
     * Get competences with loaded users to them.
     *
     * @return list of competences
     */
    List<Competence> findAllCompetences();

}
