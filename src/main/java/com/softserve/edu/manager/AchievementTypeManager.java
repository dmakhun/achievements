package com.softserve.edu.manager;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;

public interface AchievementTypeManager {

    AchievementType createAchievementType(final Competence competence, final String name,
            final int points) throws AchievementTypeManagerException;

    AchievementType createAchievementType(String name, int points, long competenceId)
            throws AchievementTypeManagerException;

    boolean deleteAchievementType(AchievementType achievementType)
            throws AchievementTypeManagerException;

    boolean deleteAchievementType(long achievementTypeId)
            throws AchievementTypeManagerException;

    Iterable<AchievementType> achievementTypesList();

}
