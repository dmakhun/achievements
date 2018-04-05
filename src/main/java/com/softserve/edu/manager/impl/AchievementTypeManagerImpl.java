package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.AchievementTypeManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("achievementTypeManager")
public class AchievementTypeManagerImpl implements
        AchievementTypeManager {

    public static final Logger LOGGER = Logger
            .getLogger(AchievementTypeManagerImpl.class);

    @Autowired
    private CompetenceDao competenceDao;

    @Autowired
    private AchievementTypeDao achievementTypeDao;


    public AchievementType create(final Competence competence, final String name, final int points) throws AchievementTypeManagerException {
        AchievementType achievementType;
        try {
            achievementType = new AchievementType().setName(name)
                    .setPoints(points).setCompetence(competence);

            achievementTypeDao.save(achievementType);
            LOGGER.info("Achievemnt type successfully created");
            return achievementType;
        } catch (Exception e) {
            LOGGER.error("Could not create achievement type");
            throw new AchievementTypeManagerException(
                    "Could not create achievement type", e);
        }
    }

    @Override
    @Transactional
    public AchievementType create(final String name, final int points,
                                  final long competenceId) throws AchievementTypeManagerException {
        Competence competence = competenceDao.findById(Competence.class,
                competenceId);
        if (competence == null) {
            LOGGER.error("Could not create achievement type. No competence with such ID");
            throw new AchievementTypeManagerException(
                    "Could not create achievement type. No competence with such ID");
        }
        return create(competence, name, points);
    }

    @Override
    @Transactional
    public AchievementType create(final String name, final int points,
                                  final String competenceUuid) throws AchievementTypeManagerException {
        Competence competence = competenceDao.findByUuid(Competence.class,
                competenceUuid);
        if (competence == null) {
            LOGGER.error("Could not create achievement type. No competence with such UUID");
            throw new AchievementTypeManagerException(
                    "Could not create achievement type. No competence with such UUID");
        }
        return create(competence, name, points);
    }

    @Override
    @Transactional
    public boolean delete(final long achievementTypeId)
            throws AchievementTypeManagerException {

        AchievementType achievementType = achievementTypeDao.findById(
                AchievementType.class, achievementTypeId);

        if (achievementType == null) {
            LOGGER.error("Could not find achievement type with such ID");
            throw new AchievementTypeManagerException(
                    "Could not find achievement type with such ID");
        }

        try {
            achievementTypeDao.delete(achievementType);
            LOGGER.info("Achievement type successfully deleted");
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not delete achievement type");
            throw new AchievementTypeManagerException(
                    "Could not delete achievement type", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(final String uuid)
            throws AchievementTypeManagerException {
        AchievementType achievementType = achievementTypeDao.findByUuid(
                AchievementType.class, uuid);

        if (achievementType == null) {
            LOGGER.error("Could not find achievement type with such UUID");
            throw new AchievementTypeManagerException(
                    "Could not find achievement type with such UUID");
        }

        try {
            achievementTypeDao.delete(achievementType);
            LOGGER.info("Achievemnt type successfully deleted");

            return true;
        } catch (Exception e) {
            LOGGER.error("Could not delete achievement type");
            throw new AchievementTypeManagerException(
                    "Could not delete achievement type", e);
        }
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
