package com.softserve.edu.dao;

import com.softserve.edu.entity.AchievementType;

import java.util.List;

public interface AchievementTypeDao extends GenericDao<AchievementType> {

    /**
     * Return achievement types of specific competence.
     *
     * @param competenceId id
     * @return list of achievement types
     */
    List<AchievementType> findByCompetenceId(Long competenceId);

    /**
     * @param competenceUuid uuid
     * @return
     */
    List<AchievementType> findByCompetenceUuid(String competenceUuid);


}
