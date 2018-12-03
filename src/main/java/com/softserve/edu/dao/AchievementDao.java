package com.softserve.edu.dao;

import com.softserve.edu.entity.Achievement;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementDao extends CrudRepository<Achievement, Long> {

    @Query("select a from Achievement a where a.user.id = :userId")
    List<Achievement> findAchievementsByUserId(long userId);
}
