package com.edu.academy.manager;

import com.edu.academy.entity.Competence;
import com.edu.academy.entity.User;

import java.util.List;

public interface CompetenceManager {

    List<Competence> findAllCompetences();

    /**
     * Find competences that given user can take.
     *
     * @param user
     */
    List<Competence> findAvailable(User user);

}