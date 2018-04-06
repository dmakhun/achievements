package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao extends GenericDao<User> {

    /**
     * Adds user and competence to the attending table.
     *
     * @param user       - user you want to attend
     * @param competence - competence you want to attend user
     */
    void attendUserToCompetence(User user, Competence competence);

    /**
     * Find concrete user by his full username.
     *
     * @param username Username.
     * @return User or null.
     * @author vkudrtc
     */
    User findByUsername(final String username);

    /**
     * Find concrete user by his email.
     *
     * @param email Email.
     * @return User or null.
     * @author vkudrtc
     */
    User findByEmail(final String email);

    /**
     * Find all groups, that user is attending.
     *
     * @param userId
     * @return
     */
    List<Group> findGroups(Long userId, boolean onlyOpened);

    /**
     * Method for counting all managers in DB.
     *
     * @return number of managers
     */
    Long countManagers();

    /**
     * @param user
     * @param competence
     */
    void removeUserToCompetence(User user, Competence competence);

    /**
     * Find all points for user
     *
     * @param user
     * @return Long
     */
    Long sumOfPoints(User user);

    List<User> findAllManagers();

}
