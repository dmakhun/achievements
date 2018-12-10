package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.CompetenceManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("competenceManager")
public class CompetenceManagerImpl implements CompetenceManager {

    private static final Logger logger = LoggerFactory.getLogger(CompetenceManagerImpl.class);

    @Autowired
    private CompetenceDao competenceDao;
    @Autowired
    private AchievementTypeDao achievementTypeDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findAllCompetences() {
        return competenceDao.findAllCompetences();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AchievementType> findAchievementsTypesByCompetenceId(Long competenceId) {
        return achievementTypeDao
                .findByCompetenceId(competenceId);
    }

    @Override
    @Transactional
    public Competence create(String name) throws CompetenceManagerException {
        try {
            Competence competence = new Competence(name, null);
            competenceDao.save(competence);
            logger.info("Competence created successfully");
            return competence;
        } catch (Exception e) {
            logger.error("Could not createAchievementType competence", e);
            throw new CompetenceManagerException("Could not createAchievementType competence",
                    e);

        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws CompetenceManagerException {

        Competence competence = competenceDao.findById(Competence.class, id);
        if (competence == null) {
            logger.error("Competence with such ID does not exist");
            throw new CompetenceManagerException(
                    "Competence with such ID does not exist");
        }
        try {
            competenceDao.delete(competence);
            logger.info("Competence removed successfully");
            return true;
        } catch (Exception e) {
            logger.error("Could not deleteAchievementType competence", e);
            throw new CompetenceManagerException("Could not deleteAchievementType competence",
                    e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findExcept(List<Competence> competencesToExclude) {
        Set<Competence> set = new HashSet<>(competenceDao.findAll(Competence.class));
        set.removeAll(competencesToExclude);
        return new ArrayList<>(set);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findByUserId(Long userId) {
        return competenceDao.findCompetencesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Competence findByID(Long competenceId) {
        return competenceDao.findById(Competence.class, competenceId);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((achievementTypeDao == null) ? 0 : achievementTypeDao
                .hashCode());
        result = prime * result
                + ((competenceDao == null) ? 0 : competenceDao.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CompetenceManagerImpl other = (CompetenceManagerImpl) obj;
        if (achievementTypeDao == null) {
            if (other.achievementTypeDao != null) {
                return false;
            }
        } else if (!achievementTypeDao.equals(other.achievementTypeDao)) {
            return false;
        }
        if (competenceDao == null) {
            return other.competenceDao == null;
        } else {
            return competenceDao.equals(other.competenceDao);
        }
    }

}
