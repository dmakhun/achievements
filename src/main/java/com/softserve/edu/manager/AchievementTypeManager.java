package com.softserve.edu.manager;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;

public interface AchievementTypeManager {

    AchievementType createAchievementType(Competence competence, String name,
            int points) throws AchievementTypeManagerException;

    AchievementType createAchievementType(String name, int points, long competenceId)
            throws AchievementTypeManagerException;

}
