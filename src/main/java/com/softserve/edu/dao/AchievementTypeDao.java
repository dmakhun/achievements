package com.softserve.edu.dao;

import com.softserve.edu.entity.AchievementType;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AchievementTypeDao extends CrudRepository<AchievementType, Long> {

    List<AchievementType> findByCompetenceId(Long competenceId);

}
