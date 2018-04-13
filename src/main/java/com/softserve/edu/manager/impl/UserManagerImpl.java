package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.UserManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.softserve.edu.util.Constants.FIELD_MAX_LENGTH;

@Service("userManager")
public class UserManagerImpl implements UserManager {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AchievementDao achievementDao;
    @Autowired
    private CompetenceDao competenceDao;
    @Autowired
    private StandardPasswordEncoder passwordEncoder;

    private static final String USER_UPDATE_ERROR = "Could not update User.";
    private static final String USER_SAVE_ERROR = "User cannot be created.";
    private static final String FIELDS_VALIDATION_ERROR = "Fields didn't validate.";
    private static final String ROLE_DOES_NOT_EXIST = "Role does not exist.";
    private static final Logger logger = Logger.getLogger(UserManagerImpl.class);
    /**
     * Pattern that covers almost all of valid emails.
     */
    private static final String PATTERN_EMAIL = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
    /**
     * Username pattern, that we pretend to define.
     */
    private static final String PATTERN_USERNAME = "^[a-zA-Z0-9\\.\\-_]{3,50}$";

    @Override
    @Transactional
    public User createUser(User user) throws UserManagerException {
        User newUser;
        try {
            newUser = validateUser(user, false, null);
        } catch (Exception e) {
            logger.error(FIELDS_VALIDATION_ERROR, e);
            throw new UserManagerException(FIELDS_VALIDATION_ERROR, e);
        }
        try {
            userDao.save(user);
        } catch (Exception e) {
            logger.error(USER_SAVE_ERROR, e);
            throw new UserManagerException(USER_SAVE_ERROR, e);
        }
        return newUser;
    }

    @Override
    @Transactional
    public User updateUser(final Long userId, final String name,
                           final String surname, final String username, final String password,
                           final String email, final Long roleId) throws UserManagerException {
        User user = userDao.findById(User.class, userId);

        if (user == null) {
            logger.error("User does not exist.");
            throw new UserManagerException("User does not exist.");
        }

        try {
            userDao.update(validateUser(user, true, null));
        } catch (Exception e) {
            logger.error(USER_UPDATE_ERROR + e);
            throw new UserManagerException(USER_UPDATE_ERROR + e);
        }

        return user;
    }

    @Override
    @Transactional
    public User updateUser(final String userUuid, User user)
            throws UserManagerException {
        User user1 = userDao.findByUuid(User.class, userUuid);
        return updateUser(user1.getId(), user.getName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole().getId());
    }

    @Override
    @Transactional
    public void deleteById(final Long id) throws UserManagerException {
        User user = userDao.findById(User.class, id);
        try {
            userDao.delete(user);
        } catch (Exception e) {
            logger.error("Delete user by id", e);
            throw new UserManagerException("Delete user by id", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findById(final Long id) throws UserManagerException {

        try {
            return userDao.findById(User.class, id);

        } catch (RuntimeException e) {
            logger.error("Could not find user by id", e);
            throw new UserManagerException("Could not find user by id", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByUuid(final String uuid) {
        User user = userDao.findByUuid(User.class, uuid);

        if (user == null) {
            logger.error("User with such uuid doesn't exist.");
        } else {
            logger.info("User was found");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByUsername(final String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByEmail(final String email) {
        return userDao.findByEmail(email);

    }

    @Transactional
    User validateUser(User user, boolean existing, String newPassword) throws UserManagerException {

        boolean validated;
        validated = genericValidation(user.getName(), "Name", existing);
        if (!validated) {
            throw new ValidationException();
        }

        validated = genericValidation(user.getSurname(), "Surname", existing);
        if (!validated) {
            throw new ValidationException();
        }

        // Besides matching naming rules, such name should be unique. OtherUser
        // here is the user that can already have the same username.
        User otherUser = findByUsername(user.getUsername());
        validated = validateByPattern(user, otherUser, user.getUsername(),
                PATTERN_USERNAME, "Username", existing);
        if (!validated) {
            throw new ValidationException();
        }

        validated = validatePassword(user.getPassword(), existing);
        if (validated) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        /*
         * Same logic as for username checks.
         */
        otherUser = findByEmail(user.getEmail());
        validated = validateByPattern(user, otherUser, user.getEmail(), PATTERN_EMAIL,
                "Email", existing);
        if (!validated) {
            throw new ValidationException();
        }

        Role role = validateRole(user.getRole().getId());
        if (role == null) {
            throw new ValidationException();
        }

        return user;
    }

    /**
     * Generic field validation.
     * <p>
     * This means, that field should meet general rules: not empty, less than 50
     * chars.
     *
     * @param field          Field, yep.
     * @param nevermindEmpty Flag, that says that we can ignore checks for emptiness.
     * @return String
     * @throws UserManagerException
     */
    @Transactional
    boolean genericValidation(final String field,
                              final String fieldName, final boolean nevermindEmpty)
            throws UserManagerException {
        if (field != null && !field.isEmpty() && field.length() <= FIELD_MAX_LENGTH) {
            return true;
        } else {
            if ((field == null || field.isEmpty()) && !nevermindEmpty) {
                logger.error(fieldName + "field was not validated.");
                throw new UserManagerException(fieldName
                        + "field was not validated.");
            }

            if (field != null && field.length() > FIELD_MAX_LENGTH) {
                logger.error(fieldName + " field was not validated.");
                throw new UserManagerException(fieldName
                        + "field was not validated.");
            }
        }
        return false;
    }

    /**
     * Validate field by pattern.
     *
     * @param user
     * @param otherUser
     * @param field
     * @param pattern
     * @param nevermindEmpty
     * @return String
     * @throws UserManagerException
     */
    @Transactional
    boolean validateByPattern(final User user, final User otherUser,
                              final String field, final String pattern, final String fieldName,
                              final boolean nevermindEmpty) throws UserManagerException {

        if (field != null && field.matches(pattern)
                && (otherUser == null || otherUser.getId() == user.getId())) {
            return true;
        } else {
            if ((field == null || field.isEmpty()) && !nevermindEmpty) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new UserManagerException(fieldName
                        + FIELDS_VALIDATION_ERROR);
            }

            if (field != null && !field.isEmpty() && !field.matches(pattern)) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new UserManagerException(fieldName
                        + FIELDS_VALIDATION_ERROR);
            }

            if (otherUser != null && otherUser.getId() != user.getId()) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new UserManagerException(fieldName + "user exist!");
            }
        }

        return false;
    }

    /**
     * Validate password.
     * <p>
     * Actually, it can be almost anything. The only requirement for now is that
     * it has to be non-empty.
     *
     * @param password       Password.
     * @param nevermindEmpty Flag to ignore checks for isEmpty.
     * @throws UserManagerException
     */
    @Transactional
    boolean validatePassword(final String password,
                             final boolean nevermindEmpty) throws UserManagerException {

        if (password != null && !password.isEmpty()) {
            return true;
        } else {
            if (!nevermindEmpty) {
                logger.error("Password cannot be empty.");
                throw new UserManagerException("Password cannot be empty.");
            }
        }

        return false;
    }

    /**
     * Validate role.
     *
     * @param roleId
     * @return Role
     * @throws UserManagerException
     */
    private Role validateRole(final Long roleId) throws UserManagerException {

        Role role = null;
        if (roleId != null) {
            role = roleDao.findById(Role.class, roleId);

            if (role == null) {
                logger.error(ROLE_DOES_NOT_EXIST);
                throw new UserManagerException(ROLE_DOES_NOT_EXIST);
            }
        }

        return role;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Group> findGroups(Long userId, boolean onlyOpened) {
        return userDao.findGroups(userId, onlyOpened);
    }

    @Override
    @Transactional
    public void appendCompetence(Long userId, Long competenceId)
            throws UserManagerException {
        User user = userDao.findById(User.class, userId);
        Competence competence = competenceDao.findById(Competence.class,
                competenceId);
        try {
            userDao.attendUserToCompetence(user, competence);
        } catch (Exception e) {
            logger.error("Could not attend competence to user", e);
            throw new UserManagerException(
                    "Could not attend competence to user", e);
        }
    }

    @Override
    @Transactional
    public void appendCompetence(String userUuid, String competenceUuid)
            throws UserManagerException {

        User user = userDao.findByUuid(User.class, userUuid);
        Competence competence = competenceDao.findByUuid(Competence.class,
                competenceUuid);

        try {
            userDao.attendUserToCompetence(user, competence);
        } catch (Exception e) {
            logger.error("Could not add competence to user", e);
            throw new UserManagerException("Could not add competence to user",
                    e);
        }

    }

    @Override
    @Transactional
    public void removeUserToCompetence(Long userId, Long competenceId)
            throws UserManagerException {
        User user = userDao.findById(User.class, userId);
        Competence competence = competenceDao.findById(Competence.class,
                competenceId);
        try {
            userDao.removeUserToCompetence(user, competence);
        } catch (Exception e) {
            logger.error("Could not remove user to competence", e);
            throw new UserManagerException(
                    "Could not remove user to competence", e);
        }

    }

    @Override
    @Transactional
    public void removeUserToCompetence(String userUuid, String competenceUuid)
            throws UserManagerException {
        User user = userDao.findByUuid(User.class, userUuid);
        Competence competence = competenceDao.findByUuid(Competence.class,
                competenceUuid);

        try {
            userDao.removeUserToCompetence(user, competence);
        } catch (Exception e) {
            logger.error("Could not remove user to competence", e);
            throw new UserManagerException(
                    "Could not remove user to competence", e);
        }

        new User() {
            @Override
            public String toString() {
                return super.toString() + "Hello@world.com";
            }
        };
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<String> findActiveNameGroups(String username) {
        Set<String> nameGroups = new HashSet<>();
        User user = userDao.findByUsername(username);
        List<Group> listGroup = userDao.findGroups(user.getId(), true);
        for (Group g : listGroup) {
            nameGroups.add(g.getName());
        }
        return nameGroups;

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<User> findAllUsers() {
        return userDao.findAll(User.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean existUserName(String username) {
        return userDao.findByUsername(username) != null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean existEmail(String email) {
        return userDao.findByEmail(email) != null;
    }

    @Override
    @Transactional
    public void deleteByUuid(String userUuid) throws UserManagerException {
        try {
            User user = findByUuid(userUuid);
            removeAssociations(user);
            userDao.delete(user);
            logger.info("User with uuid [" + userUuid + "] was removed");
        } catch (Exception e) {
            logger.error("Could not deleteAchievementType user by uuid", e);
            throw new UserManagerException("Could not deleteAchievementType user by uuid", e);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) throws UserManagerException {
        try {
            userDao.update(user);
        } catch (Exception e) {
            logger.error(USER_UPDATE_ERROR, e);
            throw new UserManagerException(USER_UPDATE_ERROR, e);
        }
    }

    @Override
    @Transactional
    public void removeAssociations(User user) {
        user.getGroups().forEach(group -> group.getUsers().remove(user));
        user.getGroups().clear();
        user.setGroups(Collections.emptySet());

        user.getCompetences().forEach(competence -> competence.getUsers().remove(user));
        user.getCompetences().clear();
        user.setCompetences(Collections.emptySet());

        user.getAchievements().forEach(achievement -> achievementDao.delete(achievement));
        logger.info("User associations had been removed");
    }

    @Override
    public Long sumOfPoints(User user) {
        return userDao.sumOfPoints(user);
    }

    @Override
    @Transactional
    public List<User> findAllManagers() {
        return userDao.findAllManagers();
    }
}