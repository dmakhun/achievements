package com.softserve.edu.manager;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.User;
import java.util.List;

public interface CompetenceManager {

    List<Competence> findAllCompetences();

    /**
     * Find competences that given user can take.
     * @param user
     */
    List<Competence> findAvailable(User user);

}