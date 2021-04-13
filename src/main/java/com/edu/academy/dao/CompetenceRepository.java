package com.edu.academy.dao;

import com.edu.academy.entity.Competence;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenceRepository extends CrudRepository<Competence, Long> {

    Competence findByName(String name);

    List<Competence> findByUsers_Id(Long userId);

    @Query("SELECT c from Competence c join c.groups g where g.dateClosed > current_date")
    List<Competence> findWithOpenedGroups();
}
