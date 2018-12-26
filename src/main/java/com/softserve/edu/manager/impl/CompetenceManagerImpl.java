package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.manager.CompetenceManager;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("competenceManager")
public class CompetenceManagerImpl implements CompetenceManager {

    @Autowired
    private CompetenceRepository competenceRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findAllCompetences() {
        return (List<Competence>) competenceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findExcept(List<Competence> competencesToExclude) {
        List<Competence> competences = new ArrayList<>();
        competenceRepository.findAll().forEach(competences::add);
        competences.removeAll(competencesToExclude);
        return competences;
    }

}
