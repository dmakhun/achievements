package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends CrudRepository<Group, Long> {

    @Query("SELECT g from Group g where g.opened > current_date and g.competence.id = :competenceId")
    List<Group> findPendingByCompetenceId(@Param("competenceId") Long competenceId);

    @Query("SELECT g from Group g where g.closed > current_date and g.competence.id = :competenceId")
    List<Group> findOpenedByCompetenceId(@Param("competenceId") Long competenceId);

    @Query("SELECT g from Group g join g.users u where g.closed > current_date and u.id = :userId")
    List<Group> findOpenedByUserId(@Param("userId") Long userId);

    Group findByName(String name);

    List<Group> findByUsers_Id(Long userId);

}
