package com.softserve.edu.dao;

import com.softserve.edu.entity.Achievement;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AchievementDao extends CrudRepository<Achievement, Long> {

    List<Achievement> findAchievementsByUserId(long userId);

}
