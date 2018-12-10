package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import java.util.List;

public interface CompetenceDao extends GenericDao<Competence> {

    Competence findByName(String name);

    List<Competence> findCompetencesByUserId(Long userId);

    List<Competence> findAllCompetences();

}
