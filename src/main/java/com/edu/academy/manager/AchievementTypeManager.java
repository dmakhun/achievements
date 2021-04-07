package com.edu.academy.manager;

import com.edu.academy.exception.AchievementTypeManagerException;

public interface AchievementTypeManager {

    void createAchievementType(String name, String points, long competenceId)
            throws AchievementTypeManagerException;


}
