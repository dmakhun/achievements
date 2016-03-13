package com.softserve.edu.manager;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

public interface UserManager {

    /**
     * Create new user, filling in lots of data.
     *
     * @param name     First name.
     * @param surname  Second name.
     * @param username User unique name.
     * @param password Desired password.
     * @param email    Unique e-mail.
     * @param roleId   Role, null for no role.
     * @throws InvalidValueException    - (some of) passed parameters are broken.
     * @throws NoSuchAlgorithmException - unhandled in hash exception.
     * @throws UserManagerException
     * @throws Exception
     * @author vkudrtc
     */
    public User create(final String name, final String surname,
                       final String username, final String password, final String email,
                       final Long roleId) throws UserManagerException;


    /**
     * Modify existing user.
     * <p>
     * If passed parameter is null or empty, that field will be not updated.
     *
     * @param userId   User's id, that we want to modify.
     * @param name     New name.
     * @param surname  New surname.
     * @param username New username.
     * @param password New password.
     * @param email    New email.
     * @param roleId   New role id.
     * @throws InvalidValueException
     * @throws NoSuchAlgorithmException
     * @throws UserManagerException
     * @author vkudrtc
     */
    public User update(final Long userId, final String name,
                       final String surname, final String username, final String password,
                       final String email, final Long roleId) throws UserManagerException;

    /**
     * Modify existing user.
     * <p>
     * If passed parameter is null or empty, that field will be not updated.
     *
     * @param User New User.
     * @throws InvalidValueException
     * @throws NoSuchAlgorithmException
     * @throws UserManagerException
     * @author vkudrtc
     */
    public void update(User user) throws UserManagerException;

    /**
     * Modify existing user.
     * <p>
     * If passed parameter is null or empty, that field will be not updated.
     *
     * @param userId   User's uuid, that we want to modify.
     * @param name     New name.
     * @param surname  New surname.
     * @param username New username.
     * @param password New password.
     * @param email    New email.
     * @param roleId   New role uuid.
     * @throws InvalidValueException
     * @throws NoSuchAlgorithmException
     * @throws UserManagerException
     * @author vkudrtc
     */
    public User update(final String userUuid, final String name,
                       final String surname, final String username, final String password,
                       final String email, final String roleUuid) throws UserManagerException;

    /**
     * Delete user by given Id.
     *
     * @param id User id.
     * @return result.
     * @throws UserManagerException
     * @author vkudrtc
     */
    public void deleteById(final Long id) throws UserManagerException;

    /**
     * Find user by his ID.
     *
     * @param id User id.
     * @return User or null.
     * @throws UserManagerException
     * @author vkudrtc
     */
    public User findById(final Long id) throws UserManagerException;

    /**
     * Find user by his ID.
     *
     * @param id User id.
     * @return User or null.
     * @author vkrettc
     */
    public User findByUuid(final String uuid);

    /**
     * Find user by his full username.
     *
     * @param username Username.
     * @return User or null.
     * @throws UserManagerException
     * @author vkudrtc
     */
    public User findByUsername(final String username);

    /**
     * Find user by his email.
     *
     * @param email Email.
     * @return User or null.
     * @author vkudrtc
     */
    public User findByEmail(final String email);

    /**
     * Find all groups user attending to.
     *
     * @param userId     User id.
     * @param onlyOpened Select only those groups, that are opened (aka not closed) in
     *                   the current moment.
     * @return
     */
    public List<Group> findGroups(final Long userId, final boolean onlyOpened);

    /**
     * Attend user to specific competence.
     *
     * @param userId
     * @param competenceId
     * @throws UserManagerException
     */
    public void attendCompetence(final Long userId, final Long competenceId) throws UserManagerException;

    /**
     * Removes user from attending to some competence
     *
     * @param user       - user you want to remove
     * @param competence - competence from which you want to remove user\'s attend
     * @throws UserManagerException
     */
    public void removeUserToCompetence(final Long userId,
                                       final Long competenceId) throws UserManagerException;

    /**
     * @param username
     * @return Set of name of all active groups for user with username
     */
    public Set<String> findActiveNameGroups(String username);

    /**
     * @return list of all users
     */
    public List<User> findAllUsers();

    /**
     * check if user exists in database
     *
     * @param username
     * @return boolean;
     * @nsosntc
     */
    public boolean existUserName(String userName);

    /**
     * Create new user
     *
     * @param User
     * @throws InvalidValueException    - (some of) passed parameters are broken.
     * @throws NoSuchAlgorithmException - unhandled in hash exception.
     * @throws UserManagerException
     * @author nsosntc
     */
    public void create(User user) throws UserManagerException;

    /**
     * check if email exists in database
     *
     * @param email
     * @return boolean;
     * @nsosntc
     */
    public boolean existEmail(String email);

    /**
     * @param userUuid
     * @throws UserManagerException
     */
    public void deleteByUuid(String userUuid) throws UserManagerException;

    /**
     * @param userUuid
     * @param competenceUuid
     * @throws UserManagerException
     */
    public void attendCompetence(String userUuid, String competenceUuid) throws UserManagerException;


    /**
     * @param userUuid
     * @param competenceUuid
     * @throws UserManagerException
     */
    void removeUserToCompetence(String userUuid, String competenceUuid)
            throws UserManagerException;

    /**
     * @param user
     */
    void removeAssociation(User user);


    public Long sumOfPoints(User user);


    List<User> findAllManagers();

}
