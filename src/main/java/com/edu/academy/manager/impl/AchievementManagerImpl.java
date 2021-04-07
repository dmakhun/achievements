package com.edu.academy.manager.impl;

import com.edu.academy.exception.AchievementManagerException;
import com.edu.academy.dao.AchievementRepository;
import com.edu.academy.dao.AchievementTypeRepository;
import com.edu.academy.dao.UserRepository;
import com.edu.academy.entity.Achievement;
import com.edu.academy.entity.AchievementType;
import com.edu.academy.entity.User;
import com.edu.academy.manager.AchievementManager;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("achievementManager")
public class AchievementManagerImpl implements AchievementManager {

    private static final Logger logger = LoggerFactory.getLogger(AchievementManagerImpl.class);

    @Autowired
    private AchievementTypeRepository achievementTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    @Transactional
    public void awardUser(long userId, long achievementTypeId, String comment)
            throws AchievementManagerException {
        Optional<AchievementType> achievementType = achievementTypeRepository
                .findById(achievementTypeId);
        if (!achievementType.isPresent()) {
            throw new IllegalArgumentException("No such AchievementType id.");
        }
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User with such id does not exist.");
        }

        try {
            achievementRepository.save(new Achievement(achievementType.get(), comment, user.get()));
        } catch (Exception e) {
            logger.error("Could not award achievement to user", e);
            throw new AchievementManagerException("Could not award achievement to user", e);
        }
    }

}
