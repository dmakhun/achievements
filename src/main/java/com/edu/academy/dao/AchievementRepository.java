package com.edu.academy.dao;

import com.edu.academy.entity.Achievement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends CrudRepository<Achievement, Long> {

    List<Achievement> findByUserId(long userId);

    @Query("SELECT sum(at.points) from AchievementType at join at.achievements a where a.user.id = :userId")
    Optional<Long> findTotalAchievementPointsByUserId(@Param("userId") long userId);

}
