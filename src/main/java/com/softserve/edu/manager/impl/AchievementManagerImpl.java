package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementRepository;
import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.AchievementManagerException;
import com.softserve.edu.manager.AchievementManager;
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
