package com.softserve.edu.manager.impl;

import static com.softserve.edu.util.Constants.FIELD_MAX_LENGTH;
import static com.softserve.edu.util.Constants.ROLE_MANAGER;
import static com.softserve.edu.util.Constants.USER_UPDATE_ERROR;

import com.softserve.edu.dao.AchievementRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.dao.GroupRepository;
import com.softserve.edu.dao.RoleRepository;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.UserManager;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    private AchievementRepository achievementRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private RoleRepository roleRepository;
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
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(Long userId, String name,
            String surname, String username, String newPassword,
            String email, Long roleId) throws UserManagerException {
        User user = userRepository.findById(userId).get();

        if (user == null) {
            logger.error(USER_DOES_NOT_EXIST);
            throw new UserManagerException(USER_DOES_NOT_EXIST);
        }

        try {
            validateUser(user, true);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(USER_UPDATE_ERROR + e);
            throw new UserManagerException(e);
        }

        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws UserManagerException {
        try {
            User user = userRepository.findById(id).get();
            removeAssociations(user);
            userRepository.delete(user);
        } catch (Exception e) {
            logger.error("Delete user by id", e);
            throw new UserManagerException("Delete user by id", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
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
        otherUser = userRepository.findByEmail(user.getEmail());
        validated = validateByPattern(user, otherUser, user.getEmail(), PATTERN_EMAIL, "Email",
                isExisting);
        if (!validated) {
            throw new ValidationException();
        }

        validated = validateRole(user.getRole().getId());
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
            Role role = roleRepository.findById(roleId).get();
            return role != null;
        }
        return true;
    }

    @Override
    @Transactional
    public void appendCompetence(Long userId, Long competenceId) {
        User user = userRepository.findById(userId).get();
        Competence competence = competenceRepository.findById(competenceId).get();
        user.addCompetence(competence);
    }

    @Override
    @Transactional
    public void removeUserToCompetence(Long userId, Long competenceId)
            throws UserManagerException {
        User user = userRepository.findById(userId).get();
        Competence competence = competenceRepository.findById(competenceId).get();
        try {
            user.removeCompetence(competence);
        } catch (Exception e) {
            logger.error("Could not remove user to competence", e);
            throw new UserManagerException(
                    "Could not remove user to competence", e);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Set<String> findOpenedGroupNames(String username) {
        User user = userRepository.findByUsername(username);
        List<Group> openedGroups = groupRepository.findOpenedByUserId(user.getId());
        return openedGroups.stream().map(Group::getName).collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean isEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
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

        user.getAchievements().forEach(achievement -> achievementRepository.delete(achievement));
        logger.info("User associations had been removed");
    }

    @Override
    public Long getTotalPoints(User user) {
        return userRepository.findByUsername(user.getUsername()).getAchievements()
                .stream().mapToLong(achievement -> achievement.getAchievementType().getPoints())
                .sum();
    }

    @Override
    @Transactional
    public List<User> findAllManagers() {
        return userRepository.findByAccessRoleName(ROLE_MANAGER);
    }
}