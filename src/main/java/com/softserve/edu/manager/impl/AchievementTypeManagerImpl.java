package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.AchievementTypeManager;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("achievementTypeManager")
public class AchievementTypeManagerImpl implements
        AchievementTypeManager {

    public static final Logger logger = LoggerFactory
            .getLogger(AchievementTypeManagerImpl.class);
    public static final String ACHIEVEMENT_TYPE_ERROR = "Could not create AchievementType";
    public static final String ACHIEVEMENT_TYPE_NO_COMPETENCE_ERROR = "Could not create AchievementType. No competence with such ID";
    public static final String ACHIEVEMENT_TYPE_DELETE_ERROR = "Could not delete AchievementType";
    public static final String ACHIEVEMENT_TYPE_CANT_FIND_ERROR = "Could not find achievement type with such Id";

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
        Competence competence = competenceRepository.findById(competenceId).get();
        if (competence == null) {
            logger.error(ACHIEVEMENT_TYPE_NO_COMPETENCE_ERROR);
            throw new AchievementTypeManagerException(ACHIEVEMENT_TYPE_NO_COMPETENCE_ERROR);
        }
        return createAchievementType(competence, name, points);
    }

    @Override
    public boolean deleteAchievementType(AchievementType achievementType)
            throws AchievementTypeManagerException {
        try {
            achievementTypeRepository.delete(achievementType);
            logger.info("Achievement type successfully deleted");
            return true;
        } catch (Exception e) {
            logger.error(ACHIEVEMENT_TYPE_DELETE_ERROR, e);
            throw new AchievementTypeManagerException(ACHIEVEMENT_TYPE_DELETE_ERROR, e);
        }
    }

    @Override
    @Transactional
    public boolean deleteAchievementType(long achievementTypeId)
            throws AchievementTypeManagerException {

        Optional<AchievementType> achievementType = achievementTypeRepository
                .findById(achievementTypeId);

        if (!achievementType.isPresent()) {
            logger.error(ACHIEVEMENT_TYPE_CANT_FIND_ERROR);
            throw new AchievementTypeManagerException(ACHIEVEMENT_TYPE_CANT_FIND_ERROR);
        }
        return deleteAchievementType(achievementType.get());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Iterable<AchievementType> achievementTypesList() {
        return achievementTypeRepository.findAll();
    }

}
