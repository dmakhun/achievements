package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.Competence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("competenceDao")
public class CompetenceDaoImpl extends GenericDaoImpl<Competence> implements CompetenceDao {

    @Autowired
    private UserRepository userRepository;

}
