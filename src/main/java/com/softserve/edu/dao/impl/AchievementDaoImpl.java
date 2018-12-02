package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("achievementDao")
public class AchievementDaoImpl extends GenericDaoImpl<Achievement> implements AchievementDao {

    @Autowired
    private UserDao userDao;

    @Override
    public List<Achievement> findAchievementsByUserId(long userId) {
        User user = userDao.findById(User.class, userId);
        return new ArrayList<>(user.getAchievements());
    }

    @Override
    public List<Achievement> findAchievementsByUserUuid(String userUuid) {
        User user = userDao.findByUuid(User.class, userUuid);
        return new ArrayList<>(user.getAchievements());
    }
}
