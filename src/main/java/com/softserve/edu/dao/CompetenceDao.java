package com.softserve.edu.dao;

import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import java.util.List;

public interface CompetenceDao extends GenericDao<Competence> {

    List<Class> findGroupsByCompetenceId(int competenceId);

    Competence findByName(String name);

    /**
     * Get list of competences, that user wants to attend to.
     */
    List<Competence> findCompetencesByUserId(Long userId);

    /**
     * Get competences with loaded users to them.
     *
     * @return list of competences
     */
    List<Competence> findAllCompetences();

}
