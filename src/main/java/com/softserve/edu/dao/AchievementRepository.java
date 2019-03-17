package com.softserve.edu.dao;

import com.softserve.edu.entity.Achievement;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AchievementRepository extends CrudRepository<Achievement, Long> {

    List<Achievement> findByUserId(long userId);

    @Query("SELECT sum(at.points) from AchievementType at join at.achievement a where a.user.id = :userId")
    Long findTotalAchievementPointsByUserId(@Param("userId") Long userId);

}
