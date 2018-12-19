package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.CompetenceManager;
import java.util.ArrayList;
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
    private CompetenceRepository competenceRepository;
    @Autowired
    private AchievementTypeRepository achievementTypeRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findAllCompetences() {
        return (List<Competence>) competenceRepository.findAll();
    }

    @Override
    @Transactional
    public Competence create(String name) throws CompetenceManagerException {
        try {
            Competence competence = new Competence(name, null);
            competenceRepository.save(competence);
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

        Competence competence = competenceRepository.findById(id).get();
        if (competence == null) {
            logger.error("Competence with such ID does not exist");
            throw new CompetenceManagerException(
                    "Competence with such ID does not exist");
        }
        try {
            competenceRepository.delete(competence);
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
        Set<Competence> set = (Set<Competence>) competenceRepository.findAll();
        set.removeAll(competencesToExclude);
        return new ArrayList<>(set);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((achievementTypeRepository == null) ? 0 : achievementTypeRepository
                .hashCode());
        result = prime * result
                + ((competenceRepository == null) ? 0 : competenceRepository.hashCode());
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
        if (achievementTypeRepository == null) {
            if (other.achievementTypeRepository != null) {
                return false;
            }
        } else if (!achievementTypeRepository.equals(other.achievementTypeRepository)) {
            return false;
        }
        if (competenceRepository == null) {
            return other.competenceRepository == null;
        } else {
            return competenceRepository.equals(other.competenceRepository);
        }
    }

}
