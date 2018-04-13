package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.manager.AchievementManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("achievementManager")
public class AchievementManagerImpl implements AchievementManager {

    private static final Logger logger = LoggerFactory.getLogger(AchievementManagerImpl.class);

    @Autowired
    private AchievementTypeDao achievementTypeDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AchievementDao achievementDao;

    @Override
    @Transactional
    public void awardUser(long userId, long achievementTypeId, String comment) throws CompetenceManagerException {

        AchievementType achievementType = achievementTypeDao.findById(AchievementType.class, achievementTypeId);
        if (achievementType == null)
            throw new IllegalArgumentException("No such Achievement Type id.");
        User user = userDao.findById(User.class, userId);
        if (user == null) throw new IllegalArgumentException("User with such id does not exist.");

        Achievement achievement = new Achievement();
        achievement.setComment(comment);
        achievement.setAchievementType(achievementType);
        achievement.setUser(user);
        achievement.setCreated(new Date());
        try {
            achievementDao.save(achievement);
        } catch (Exception e) {
            logger.error("Could not award achievement to user", e);
            throw new CompetenceManagerException("Could not award achievement to user", e);
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
        return achievementDao.findAchievementsByUserUuid(userUuid);
    }

}
