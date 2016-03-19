package com.softserve.edu.manager;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.CompetenceManagerException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("competenceManager")
public class CompetenceManagerImplementation implements CompetenceManager {

    private static final Logger LOGGER = Logger
            .getLogger(CompetenceManagerImplementation.class);

    @Autowired
    CompetenceDao competenceDao;
    @Autowired
    AchievementTypeDao achievementTypeDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> findGroups(int CompetenceId) {
        return competenceDao.showGroups(CompetenceId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> findGroupsByCompetenceUuid(String competenceUuid) {
        return competenceDao.findGroupsByCompetenceUuid(competenceUuid);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findAllCompetences() {
        return competenceDao.findAllCompetences();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AchievementType> findTypesOfAchievements(Long idCompetence) {

        List<AchievementType> achievementTypes = achievementTypeDao
                .findByCompetenceId(idCompetence);

        return achievementTypes;
    }

    @Override
    @Transactional
    public Competence create(String name) throws CompetenceManagerException {
        Competence competence;
        try {
            competence = new Competence();
            competence.setName(name);
            competenceDao.save(competence);
            LOGGER.info("Competence created successfully");
            return competence;
        } catch (Exception e) {
            LOGGER.error("Could not create competence", e);
            throw new CompetenceManagerException("Could not create competence",
                    e);

        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws CompetenceManagerException {

        Competence competence = competenceDao.findById(Competence.class, id);
        if (competence == null) {
            LOGGER.error("Competence with such ID does not exist");
            throw new CompetenceManagerException(
                    "Competence with such ID does not exist");
        }
        try {
            competenceDao.delete(competence);
            LOGGER.info("Competence removed successfully");
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not delete competence", e);
            throw new CompetenceManagerException("Could not delete competence",
                    e);
        }
    }

    @Override
    @Transactional
    public boolean deleteByUuid(String uuid) throws CompetenceManagerException {

        Competence competence = competenceDao
                .findByUuid(Competence.class, uuid);
        if (competence == null) {
            LOGGER.error("Competence with such UUID does not exist");
            throw new CompetenceManagerException(
                    "Competence with such UUID does not exist");
        }
        try {
            competenceDao.delete(competence);
            LOGGER.info("Competence removed successfully");
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not delete competence", e);
            throw new CompetenceManagerException("Could not delete competence",
                    e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> listWithout(List<Competence> buts) {
        Set<Competence> but = new HashSet<>();
        but.addAll(buts);
        Set<Competence> set = new HashSet<>();
        set.addAll(competenceDao.findAll(Competence.class));
        set.removeAll(but);
        return new ArrayList<>(set);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findByUser(Long userId) {
        return competenceDao.findCompetencesByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findByUserUuid(String userUuid) {
        return competenceDao.findByUserUuid(userUuid);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Competence findByID(Long objectId) {
        return competenceDao.findById(Competence.class, objectId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AchievementType> findAchievementTypesByCompetenceUuid(
            String conpetenceUuid) {

        List<AchievementType> achievementTypes = achievementTypeDao
                .findByCompetenceUuid(conpetenceUuid);

        return achievementTypes;
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CompetenceManagerImplementation other = (CompetenceManagerImplementation) obj;
        if (achievementTypeDao == null) {
            if (other.achievementTypeDao != null)
                return false;
        } else if (!achievementTypeDao.equals(other.achievementTypeDao))
            return false;
        if (competenceDao == null) {
            if (other.competenceDao != null)
                return false;
        } else if (!competenceDao.equals(other.competenceDao))
            return false;
        return true;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean validateCompetenceName(String name) {
        if (competenceDao.findByName(name) != null) {
            return false;
        }
        return true;
    }

}
