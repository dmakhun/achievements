package com.edu.academy.dao;

import com.edu.academy.entity.AchievementType;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementTypeRepository extends JpaRepository<AchievementType, Long> {

    List<AchievementType> findByCompetenceId(Long competenceId);

    void deleteById(Long achievementTypeId);

}
