package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends GenericDao<User> {

    /**
     * Adds user and competence to the attending table.
     *
     * @param user - user you want to attend
     * @param competence - competence you want to attend user
     */
    void attendUserToCompetence(User user, Competence competence);

    /**
     * Find concrete user by his full username.
     *
     * @param username Username.
     * @return User or null.
     */
    User findByUsername(final String username);

    /**
     * Find concrete user by his email.
     *
     * @param email Email.
     * @return User or null.
     */
    User findByEmail(final String email);

    /**
     * Find all groups, that user is attending.
     */
    List<Group> findGroups(Long userId, boolean onlyOpened);

    /**
     *
     */
    void removeUserToCompetence(User user, Competence competence);

    List<User> findByRole(String role);

}
