package com.edu.academy.dao;

import com.edu.academy.entity.Competence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {

    Competence findByName(String name);

    List<Competence> findByUsers_Id(Long userId);

    @Query("SELECT c from Competence c join c.groups g where g.dateClosed > current_date")
    List<Competence> findWithOpenedGroups();
}
