package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.dao.GroupRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.manager.CompetenceManager;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("competenceManager")
public class CompetenceManagerImpl implements CompetenceManager {

    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findAllCompetences() {
        return competenceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Competence> findAvailable(User user) {
        List<Group> openedGroups = groupRepository.findOpenedByUserId(user.getId());
        List<Competence> userCompetences = competenceRepository
                .findByUsers_Id(user.getId());
        List<Competence> competencesToExclude = openedGroups.stream().map(Group::getCompetence)
                .collect(Collectors.toList());
        competencesToExclude.addAll(userCompetences);
        List<Competence> competences = competenceRepository.findAll();
        competences.removeAll(competencesToExclude);
        return competences;
    }

}
