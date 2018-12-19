package com.softserve.edu.manager;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.CompetenceManagerException;
import java.util.List;

public interface CompetenceManager {

    /**
     * @return all list of competenceies.
     */
    List<Competence> findAllCompetences();

    /**
     * return all types of achievements in some competence.
     *
     * @return List<AchievementType>
     */
    List<AchievementType> findAchievementsTypesByCompetenceId(Long competenceId);

    /**
     * Get list of competences but those, that are given.
     */
    List<Competence> findExcept(List<Competence> buts);

    /**
     * Creates a new competence with current name.
     *
     * @param name of competence
     * @return competence
     */
    Competence create(String name) throws CompetenceManagerException;

    boolean delete(Long id) throws CompetenceManagerException;

    Competence findByID(Long competenceId);

}