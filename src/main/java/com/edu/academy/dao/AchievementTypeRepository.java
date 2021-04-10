package com.edu.academy.dao;

import com.edu.academy.entity.AchievementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementTypeRepository extends JpaRepository<AchievementType, Long> {

    List<AchievementType> findByCompetenceId(Long competenceId);

    void deleteById(Long achievementTypeId);

}
