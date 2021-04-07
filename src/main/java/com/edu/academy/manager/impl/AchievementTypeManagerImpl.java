package com.edu.academy.manager.impl;

import com.edu.academy.exception.AchievementTypeManagerException;
import com.edu.academy.dao.AchievementTypeRepository;
import com.edu.academy.dao.CompetenceRepository;
import com.edu.academy.entity.AchievementType;
import com.edu.academy.entity.Competence;
import com.edu.academy.manager.AchievementTypeManager;
import java.util.Optional;
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
    public void createAchievementType(String name, String points, long competenceId)
            throws AchievementTypeManagerException {
        try {
            Optional<Competence> competence = competenceRepository.findById(competenceId);
            if (competence.isPresent()) {
                AchievementType achievementType = new AchievementType(competence.get(), name,
                        Integer.parseInt(points));
                achievementTypeRepository.save(achievementType);
                logger.info("Achievement type successfully created");
            }
        } catch (Exception e) {
            logger.error(ACHIEVEMENT_TYPE_ERROR, e);
            throw new AchievementTypeManagerException(ACHIEVEMENT_TYPE_ERROR, e);
        }
    }
}
