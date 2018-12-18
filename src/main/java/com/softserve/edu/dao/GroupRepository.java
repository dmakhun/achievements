package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends CrudRepository<Group, Long> {

    @Query("SELECT g from Group g where g.dateOpened > current_date and g.competence.id = ?1")
    List<Group> findPendingByCompetenceId(@Param("competenceId") Long competenceId);

    @Query("SELECT g from Group g where g.dateClosed > current_date and g.competence.id = ?1")
    List<Group> findOpenedByCompetenceId(@Param("competenceId") Long competenceId);

    Group findByName(String name);

}
