package com.edu.academy.dao;

import com.edu.academy.entity.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    @Query("SELECT g from Group g where g.dateOpened > current_date and g.competence.id = :competenceId")
    List<Group> findPendingByCompetenceId(@Param("competenceId") Long competenceId);

    @Query("SELECT g from Group g where g.dateClosed > current_date and g.competence.id = :competenceId")
    List<Group> findOpenedByCompetenceId(@Param("competenceId") Long competenceId);

    @Query("SELECT g from Group g join g.users u where g.dateClosed > current_date and u.id = :userId")
    List<Group> findOpenedByUserId(@Param("userId") Long userId);

    Group findByName(String name);

    List<Group> findByUsers_Id(Long userId);

}
