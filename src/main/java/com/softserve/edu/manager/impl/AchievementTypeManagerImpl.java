package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.AchievementTypeManager;
import java.util.List;
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
    public static final String ACHIEVEMENT_TYPE_NO_COMPETENCE_UUID_ERROR = "Could not createAchievementType achievement type. No competence with such UUID";
    public static final String ACHIEVEMENT_TYPE_DELETE_ERROR = "Could not delete AchievementType";
    public static final String ACHIEVEMENT_TYPE_CANT_FIND_ERROR = "Could not find achievement type with such Id";
    public static final String ACHIEVEMENT_TYPE_CANT_FIND_UUID_ERROR = "Could not find achievement type with such Uuid";

    @Autowired
    private CompetenceDao competenceDao;

    @Autowired
    private AchievementTypeDao achievementTypeDao;

    @Override
    @Transactional
    public AchievementType createAchievementType(Competence competence, String name, int points)
            throws AchievementTypeManagerException {
        try {
            AchievementType achievementType = new AchievementType(competence, name, points);
            achievementTypeDao.save(achievementType);
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
        Competence competence = competenceDao.findById(Competence.class, competenceId);
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
            achievementTypeDao.delete(achievementType);
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

        AchievementType achievementType = achievementTypeDao.findById(
                AchievementType.class, achievementTypeId);

        if (achievementType == null) {
            logger.error(ACHIEVEMENT_TYPE_CANT_FIND_ERROR);
            throw new AchievementTypeManagerException(ACHIEVEMENT_TYPE_CANT_FIND_ERROR);
        }
        return deleteAchievementType(achievementType);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AchievementType> achievementTypesList() {
        return achievementTypeDao.findAll(AchievementType.class);
    }

}
