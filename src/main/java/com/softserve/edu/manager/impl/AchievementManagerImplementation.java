package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.AchievementManager;
import com.softserve.edu.util.AngryThrower;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("achievementManager")
public class AchievementManagerImplementation implements AchievementManager {

    private static final Logger LOGGER = Logger
            .getLogger(AchievementManagerImplementation.class);

    @Autowired
    AchievementTypeDao achievementTypeDao;

    @Autowired
    UserDao userDao;

    @Autowired
    AchievementDao achievementDao;

    @Override
    @Transactional
    public void awardUser(long userId, long achievementTypeId,
                          String comment) throws CompetenceManagerException {

        AchievementType achievementType = achievementTypeDao.findById(
                AchievementType.class, achievementTypeId);
        AngryThrower.ifNull(achievementType, "No such achievement type id.");

        User user = userDao.findById(User.class, userId);
        AngryThrower.ifNull(user, "User with such id does not exist.");

        Achievement achievement = new Achievement();
        achievement.setComment(comment);
        achievement.setAchievementType(achievementType);
        achievement.setUser(user);
        achievement.setCreated(new Date());
        try {
            achievementDao.save(achievement);
        } catch (Exception e) {
            LOGGER.error("Could not createAchievementType achievement", e);
            throw new CompetenceManagerException(
                    "Could not createAchievementType achievement", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Achievement> findUserAchievements(Long userId) {
        return achievementDao.findAchievementsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Achievement> findAchievementsByUserUuid(String userUuid) {
        return achievementDao.findByUserUuid(userUuid);
    }

}
