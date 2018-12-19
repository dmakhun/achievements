package com.softserve.edu.manager.impl;

import static com.softserve.edu.util.Constants.FIELD_MAX_LENGTH;
import static com.softserve.edu.util.Constants.ROLE_MANAGER;
import static com.softserve.edu.util.Constants.USER_UPDATE_ERROR;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.AccessRole;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.UserManager;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userManager")
public class UserManagerImpl implements UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    private static final String USER_DOES_NOT_EXIST = "User does not exist.";
    private static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty.";
    private static final String USER_SAVE_ERROR = "User cannot be created.";
    private static final String FIELDS_VALIDATION_ERROR = " field didn't validate.";
    private static final String ROLE_DOES_NOT_EXIST = "AccessRole does not exist.";

    private static final String PATTERN_EMAIL = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
    private static final String PATTERN_USERNAME = "^[a-zA-Z0-9\\.\\-_]{3,50}$";

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AchievementDao achievementDao;
    @Autowired
    private CompetenceDao competenceDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User createUser(User user) throws UserManagerException {
        try {
            validateUser(user, false);
        } catch (ValidationException e) {
            logger.error(FIELDS_VALIDATION_ERROR, e);
            throw new UserManagerException(FIELDS_VALIDATION_ERROR, e);
        }
        try {
            userDao.save(user);
        } catch (Exception e) {
            logger.error(USER_SAVE_ERROR, e);
            throw new UserManagerException(USER_SAVE_ERROR, e);
        }
        return user;
    }

    @Override
    @Transactional
    public User updateUser(Long userId, String name,
            String surname, String username, String newPassword,
            String email, Long roleId) throws UserManagerException {
        User user = userDao.findById(User.class, userId);

        if (user == null) {
            logger.error(USER_DOES_NOT_EXIST);
            throw new UserManagerException(USER_DOES_NOT_EXIST);
        }

        try {
            validateUser(user, true);
            user.setPassword(passwordEncoder.encode(newPassword));
            userDao.update(user);
        } catch (Exception e) {
            logger.error(USER_UPDATE_ERROR + e);
            throw new UserManagerException(e);
        }

        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) throws UserManagerException {
        try {
            userDao.update(user);
        } catch (Exception e) {
            logger.error(USER_UPDATE_ERROR, e);
            throw new UserManagerException(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws UserManagerException {
        try {
            User user = userDao.findById(User.class, id);
            removeAssociations(user);
            userDao.delete(user);
        } catch (Exception e) {
            logger.error("Delete user by id", e);
            throw new UserManagerException("Delete user by id", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findById(Long id) throws UserManagerException {
        try {
            return userDao.findById(User.class, id);
        } catch (RuntimeException e) {
            logger.error("Could not find user by id", e);
            throw new UserManagerException("Could not find user by id", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByEmail(String email) {
        return userDao.findByEmail(email);

    }

    private void validateUser(User user, boolean isExisting) throws ValidationException {

        boolean validated;
        validated = genericValidation(user.getName(), "Name", isExisting);
        if (!validated) {
            throw new ValidationException();
        }

        validated = genericValidation(user.getSurname(), "Surname", isExisting);
        if (!validated) {
            throw new ValidationException();
        }

        // Besides matching naming rules, such name should be unique. OtherUser
        // here is the user that can have the same username already.
        User otherUser = findByUsername(user.getUsername());
        validated = validateByPattern(user, otherUser, user.getUsername(), PATTERN_USERNAME,
                "Username", isExisting);
        if (!validated) {
            throw new ValidationException();
        }

        validated = validatePassword(user.getPassword(), isExisting);
        if (!validated) {
            throw new ValidationException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Same logic as for username checks.
        otherUser = findByEmail(user.getEmail());
        validated = validateByPattern(user, otherUser, user.getEmail(), PATTERN_EMAIL, "Email",
                isExisting);
        if (!validated) {
            throw new ValidationException();
        }

        validated = validateRole(user.getAccessRole().getId());
        if (!validated) {
            logger.error(ROLE_DOES_NOT_EXIST);
            throw new ValidationException(ROLE_DOES_NOT_EXIST);
        }
    }

    /**
     * Generic field validation.
     * <p>
     * This means, that field should meet general rules: not empty, less than 50 chars.
     *
     * @param isExisting Flag that says we can ignore checks for emptiness because the method was
     * called for updates.
     * @return String
     */
    @Transactional
    boolean genericValidation(String field, String fieldName, boolean isExisting)
            throws ValidationException {
        if (field != null && !field.isEmpty() && field.length() <= FIELD_MAX_LENGTH) {
            return true;
        } else {
            if ((field == null || field.isEmpty()) && !isExisting) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new ValidationException(fieldName + FIELDS_VALIDATION_ERROR);
            }
            if (field != null && field.length() > FIELD_MAX_LENGTH) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new ValidationException(fieldName + FIELDS_VALIDATION_ERROR);
            }
            return false;
        }
    }

    @Transactional
    boolean validateByPattern(User user, User otherUser,
            String field, String pattern, String fieldName,
            boolean isExisting) throws ValidationException {

        if (field != null && field.matches(pattern)
                && (otherUser == null || otherUser.getId().equals(user.getId()))) {
            return true;
        } else {
            if ((field == null || field.isEmpty()) && !isExisting) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new ValidationException(fieldName + FIELDS_VALIDATION_ERROR);
            }

            if (field != null && !field.isEmpty() && !field.matches(pattern)) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new ValidationException(fieldName + FIELDS_VALIDATION_ERROR);
            }

            if (otherUser != null && !otherUser.getId().equals(user.getId())) {
                logger.error(fieldName + FIELDS_VALIDATION_ERROR);
                throw new ValidationException(fieldName + "user exists!");
            }
        }

        return false;
    }

    /**
     * Check  password for non-emptiness.
     *
     * @param password Password.
     * @param isExisting Flag to ignore checks for isEmpty.
     */
    private boolean validatePassword(String password, boolean isExisting)
            throws ValidationException {

        if (password != null && !password.isEmpty()) {
            return true;
        } else {
            if (!isExisting) {
                logger.error(PASSWORD_CANNOT_BE_EMPTY);
                throw new ValidationException(PASSWORD_CANNOT_BE_EMPTY);
            }
        }

        return false;
    }

    private boolean validateRole(Long roleId) {
        if (roleId != null) {
            AccessRole accessRole = roleDao.findById(AccessRole.class, roleId);
            return accessRole != null;
        }
        return true;
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
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<String> findActiveNameGroups(String username) {
        Set<String> nameGroups = new HashSet<>();
        User user = userDao.findByUsername(username);
        List<Group> listGroups = userDao.findGroups(user.getId(), true);
        for (Group aGroup : listGroups) {
            nameGroups.add(aGroup.getName());
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
    public boolean isUsernameExists(String username) {
        return userDao.findByUsername(username) != null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean isEmailExists(String email) {
        return userDao.findByEmail(email) != null;
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
    public Long getTotalPoints(User user) {
        return userDao.findByUsername(user.getUsername()).getAchievements()
                .stream().mapToLong(achievement -> achievement.getAchievementType().getPoints())
                .sum();
    }

    @Override
    @Transactional
    public List<User> findAllManagers() {
        return userDao.findByRole(ROLE_MANAGER);
    }
}