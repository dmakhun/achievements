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

    @Autowired
    private CompetenceDao competenceDao;

    @Autowired
    private AchievementTypeDao achievementTypeDao;

    @Override
    @Transactional
    public AchievementType createAchievementType(Competence competence, String name, int points)
            throws AchievementTypeManagerException {
        AchievementType achievementType;
        try {
            achievementType = new AchievementType(competence, name, points);
            achievementTypeDao.save(achievementType);
            logger.info("Achievement type successfully created");
            return achievementType;
        } catch (Exception e) {
            logger.error("Could not createUser Achievement Type");
            throw new AchievementTypeManagerException("Could not createUser Achievement Type", e);
        }
    }

    @Override
    @Transactional
    public AchievementType createAchievementType(final String name, final int points,
            final long competenceId) throws AchievementTypeManagerException {
        Competence competence = competenceDao.findById(Competence.class, competenceId);
        if (competence == null) {
            logger.error(
                    "Could not createAchievementType achievement type. No competence with such ID");
            throw new AchievementTypeManagerException(
                    "Could not createAchievementType achievement type. No competence with such ID");
        }
        return createAchievementType(competence, name, points);
    }

    @Override
    @Transactional
    public AchievementType createAchievementTypeByUuid(final String name, final int points,
            final String competenceUuid) throws AchievementTypeManagerException {
        Competence competence = competenceDao.findByUuid(Competence.class,
                competenceUuid);
        if (competence == null) {
            logger.error(
                    "Could not createAchievementType achievement type. No competence with such UUID");
            throw new AchievementTypeManagerException(
                    "Could not createAchievementType achievement type. No competence with such UUID");
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
            logger.error("Could not deleteAchievementType achievement type");
            throw new AchievementTypeManagerException(
                    "Could not deleteAchievementType achievement type", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteAchievementType(final long achievementTypeId)
            throws AchievementTypeManagerException {

        AchievementType achievementType = achievementTypeDao.findById(
                AchievementType.class, achievementTypeId);

        if (achievementType == null) {
            logger.error("Could not find achievement type with such Id");
            throw new AchievementTypeManagerException(
                    "Could not find achievement type with such Id");
        }

        return deleteAchievementType(achievementType);
    }

    @Override
    @Transactional
    public boolean deleteAchievementType(final String uuid)
            throws AchievementTypeManagerException {
        AchievementType achievementType = achievementTypeDao
                .findByUuid(AchievementType.class, uuid);
        if (achievementType == null) {
            logger.error("Could not find achievement type with such Uuid");
            throw new AchievementTypeManagerException(
                    "Could not find achievement type with such Uuid");
        }
        return deleteAchievementType(achievementType);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AchievementType> achievementTypesList() {
        return achievementTypeDao.findAll(AchievementType.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AchievementType> findAchievements(final long competenceId) {
        return achievementTypeDao.findByCompetenceId(competenceId);
    }
}
