package com.softserve.edu.manager;

import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import java.util.List;
import java.util.Set;

public interface UserManager {

    /**
     * Create new user, filling in lots of data.
     *
     * @param user@throws UserManagerException
     */
    User createUser(User user) throws UserManagerException;


    /**
     * Modify existing user.
     * <p>
     * If passed parameter is null or empty, that field will be not updated.
     *
     * @param userId User's id, that we want to modify.
     * @param name New name.
     * @param surname New surname.
     * @param username New username.
     * @param password New password.
     * @param email New email.
     * @param roleId New role id.
     */
    User updateUser(Long userId, String name,
            String surname, String username, String password,
            String email, Long roleId) throws UserManagerException;

    /**
     * Modify existing user.
     * <p>
     * If passed parameter is null or empty, that field will be not updated.
     *
     * @param user New User.
     */
    void updateUser(User user) throws UserManagerException;

    /**
     * Delete user by given Id.
     *
     * @param id User id.
     * @return result.
     */
    void deleteById(Long id) throws UserManagerException;

    /**
     * Find user by his ID.
     *
     * @param id User id.
     * @return User or null.
     */
    User findById(Long id) throws UserManagerException;

    /**
     * Find user by his full username.
     *
     * @param username Username.
     * @return User or null.
     */
    User findByUsername(String username);

    /**
     * Find user by his email.
     *
     * @param email Email.
     * @return User or null.
     */
    User findByEmail(String email);

    /**
     * Find all groups user attending to.
     *
     * @param userId User id.
     * @param onlyOpened Select only those groups, that are opened (aka not closed) in the current
     * moment.
     */
    List<Class> findGroups(Long userId, boolean onlyOpened);

    /**
     * Attend user to specific competence.
     */
    void appendCompetence(Long userId, Long competenceId) throws UserManagerException;

    /**
     * Removes user from attending to some competence
     *
     * @param userId - user you want to remove
     * @param competenceId - competence Id from which you want to remove user\'s attend
     */
    void removeUserToCompetence(Long userId,
            Long competenceId) throws UserManagerException;

    Set<String> findActiveNameGroups(String username);

    List<User> findAllUsers();

    boolean isUsernameExists(String username);

    boolean isEmailExists(String email);

    void removeAssociations(User user);

    Long getTotalPoints(User user);

    List<User> findAllManagers();

}
