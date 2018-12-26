package com.softserve.edu.manager;

import com.softserve.edu.entity.Competence;
import java.util.List;

public interface CompetenceManager {

    /**
     * @return all list of competenceies.
     */
    List<Competence> findAllCompetences();

    /**
     * Get list of competences but those, that are given.
     */
    List<Competence> findExcept(List<Competence> buts);

}