package com.softserve.edu.dao;

import com.softserve.edu.entity.AchievementType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementTypeRepository extends JpaRepository<AchievementType, Long> {

    List<AchievementType> findByCompetenceId(Long competenceId);

    void deleteById(Long achievementTypeId);

}
