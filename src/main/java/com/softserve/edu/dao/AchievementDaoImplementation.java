package com.softserve.edu.dao;

import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("achievementDao")
public class AchievementDaoImplementation extends
        GenericDaoImplementation<Achievement> implements AchievementDao {

    @Autowired
    UserDao userDao;

    @Override
    public List<Achievement> findByUserId(Long userId) {
        User user = entityManager.find(User.class, userId);
        List<Achievement> achievements = new ArrayList<>(user.getAchievements());
        for (Achievement achievement : achievements) {
            achievement.getAchievementType();
        }

        return achievements;
    }

    @Override
    public List<Achievement> findByUserUuid(String userUuid) {
        User user = userDao.findEntity(Achievement.GET_ACHIEVEMENT_FROM_USER,
                userUuid);

        return findEntityList(
                Achievement.GET_ACHIEVEMENT_FROM_LIST_ACHIEVEMENT, user.getId());
    }

}
