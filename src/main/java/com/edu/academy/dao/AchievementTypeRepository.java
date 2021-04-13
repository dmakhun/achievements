package com.edu.academy.dao;

import com.edu.academy.entity.AchievementType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementTypeRepository extends CrudRepository<AchievementType, Long> {

    List<AchievementType> findByCompetenceId(Long competenceId);

    void deleteById(Long achievementTypeId);

}
