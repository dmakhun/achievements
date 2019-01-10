package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.AchievementTypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("achievementTypeManager")
public class AchievementTypeManagerImpl implements AchievementTypeManager {

    public static final Logger logger = LoggerFactory.getLogger(AchievementTypeManagerImpl.class);

    public static final String ACHIEVEMENT_TYPE_ERROR = "Could not create AchievementType";

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private AchievementTypeRepository achievementTypeRepository;

    @Override
    @Transactional
    public AchievementType createAchievementType(Competence competence, String name, int points)
            throws AchievementTypeManagerException {
        try {
            AchievementType achievementType = new AchievementType(competence, name, points);
            achievementTypeRepository.save(achievementType);
            logger.info("Achievement type successfully created");
            return achievementType;
        } catch (Exception e) {
            logger.error(ACHIEVEMENT_TYPE_ERROR, e);
            throw new AchievementTypeManagerException(ACHIEVEMENT_TYPE_ERROR, e);
        }
    }

    @Override
    @Transactional
    public AchievementType createAchievementType(String name, int points, long competenceId)
            throws AchievementTypeManagerException {
        return createAchievementType(competenceRepository.findById(competenceId).get(), name,
                points);
    }

}
