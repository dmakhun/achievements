package com.softserve.edu.manager;

import com.softserve.edu.entity.Group;
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
    User updateUser(final Long userId, final String name,
            final String surname, final String username, final String password,
            final String email, final Long roleId) throws UserManagerException;

    /**
     * Modify existing user.
     * <p>
     * If passed parameter is null or empty, that field will be not updated.
     *
     * @param user New User.
     */
    void updateUser(User user) throws UserManagerException;

    /**
     * Modify existing user.
     * <p>
     * If passed parameter is null or empty, that field will be not updated.
     *
     * @param userUuid User's uuid, that we want to modify.
     */
    User updateUser(final String userUuid, User user) throws UserManagerException;

    /**
     * Delete user by given Id.
     *
     * @param id User id.
     * @return result.
     */
    void deleteById(final Long id) throws UserManagerException;

    /**
     * Find user by his ID.
     *
     * @param id User id.
     * @return User or null.
     */
    User findById(final Long id) throws UserManagerException;

    /**
     * Find user by his ID.
     *
     * @return User or null.
     */
    User findByUuid(final String uuid);

    /**
     * Find user by his full username.
     *
     * @param username Username.
     * @return User or null.
     */
    User findByUsername(final String username);

    /**
     * Find user by his email.
     *
     * @param email Email.
     * @return User or null.
     */
    User findByEmail(final String email);

    /**
     * Find all groups user attending to.
     *
     * @param userId User id.
     * @param onlyOpened Select only those groups, that are opened (aka not closed) in the current
     * moment.
     */
    List<Group> findGroups(final Long userId, final boolean onlyOpened);

    /**
     * Attend user to specific competence.
     */
    void appendCompetence(final Long userId, final Long competenceId) throws UserManagerException;

    /**
     * Removes user from attending to some competence
     *
     * @param userId - user you want to remove
     * @param competenceId - competence Id from which you want to remove user\'s attend
     */
    void removeUserToCompetence(final Long userId,
            final Long competenceId) throws UserManagerException;

    /**
     * @return Set of name of all active groups for user with username
     */
    Set<String> findActiveNameGroups(String username);

    /**
     * @return list of all users
     */
    List<User> findAllUsers();

    /**
     * check if user exists in database
     *
     * @return boolean;
     * @nsosntc
     */
    boolean existUserName(String username);


    /**
     * check if email exists in database
     *
     * @return boolean;
     * @nsosntc
     */
    boolean existEmail(String email);

    /**
     *
     */
    void deleteByUuid(String userUuid) throws UserManagerException;

    /**
     *
     */
    void appendCompetence(String userUuid, String competenceUuid) throws UserManagerException;


    /**
     *
     */
    void removeUserToCompetence(String userUuid, String competenceUuid)
            throws UserManagerException;

    /**
     *
     */
    void removeAssociations(User user);


    Long sumOfPoints(User user);


    List<User> findAllManagers();

}
