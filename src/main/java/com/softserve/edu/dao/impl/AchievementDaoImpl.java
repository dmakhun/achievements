package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("achievementDao")
public class AchievementDaoImpl extends
        GenericDaoImpl<Achievement> implements AchievementDao {

    @Autowired
    private UserDao userDao;

    @Override
    public List<Achievement> findAchievementsByUserId(long userId) {
        User user = entityManager.find(User.class, userId);
        return new ArrayList<>(user.getAchievements());
    }

    @Override
    public List<Achievement> findAchievementsByUserUuid(String userUuid) {
        User user = userDao.findEntity(Achievement.GET_ACHIEVEMENT_FROM_USER,
                userUuid);

        return findEntityList(
                Achievement.GET_ACHIEVEMENT_FROM_LIST_ACHIEVEMENT, user.getId());
    }

}
