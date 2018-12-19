package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CompetenceRepository extends CrudRepository<Competence, Long> {

    Competence findByName(String name);

    List<Competence> findByUsers_Id(Long userId);
}
