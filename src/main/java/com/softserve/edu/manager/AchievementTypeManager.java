package com.softserve.edu.manager;

import com.softserve.edu.exception.AchievementTypeManagerException;

public interface AchievementTypeManager {

    void createAchievementType(String name, String points, long competenceId)
            throws AchievementTypeManagerException;


}
