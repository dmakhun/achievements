package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("competenceDao")
public class CompetenceDaoImpl extends GenericDaoImpl<Competence> implements CompetenceDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Competence findByName(String name) {
        return findEntity(Competence.FIND_COMPETENCE_BY_NAME, name);
    }

    @Override
    public List<Competence> findCompetencesByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        return new ArrayList<>(user.getCompetences());
    }

    @Override
    public List<Competence> findAllCompetences() {
        return findAll(Competence.class);
    }

}
