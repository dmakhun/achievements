package com.edu.academy.manager.impl;

import static com.edu.academy.util.Constants.FIELD_MAX_LENGTH;
import static com.edu.academy.util.Constants.USER_UPDATE_ERROR;

import com.edu.academy.exception.UserManagerException;
import com.edu.academy.dao.AchievementRepository;
import com.edu.academy.dao.CompetenceRepository;
import com.edu.academy.dao.GroupRepository;
import com.edu.academy.dao.RoleRepository;
import com.edu.academy.dao.UserRepository;
import com.edu.academy.entity.Competence;
import com.edu.academy.entity.Group;
import com.edu.academy.entity.Role;
import com.edu.academy.entity.User;
import com.edu.academy.manager.UserManager;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userManager")
public class UserManagerImpl implements UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    private static final String USER_DOES_NOT_EXIST = "User does not exist.";
    private static final String PASSWORD_CANNOT_BE_EMPTY = "Password cannot be empty.";
    private static final String FIELDS_VALIDATION_ERROR = " field didn't validate.";
    private static final String ROLE_DOES_NOT_EXIST = "Role does not exist.";

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
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

        try {
            validateUser(user, true);
            if (StringUtils.isNotEmpty(name)) {
                user.setName(name);
            }
            if (StringUtils.isNotEmpty(surname)) {
                user.setSurname(surname);
            }
            if (StringUtils.isNotEmpty(username)) {
                user.setUsername(username);
            }
            if (StringUtils.isNotEmpty(email)) {
                user.setEmail(email);
            }
            if (roleId != null) {
                user.setRole(roleRepository.findById(roleId).get());
            }
            if (StringUtils.isNotEmpty(newPassword)) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
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
     */
    private boolean genericValidation(String field, String fieldName, boolean isExisting)
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

    private boolean validateByPattern(User user, User otherUser,
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
     * Check password for non-emptiness.
     *
     * @param password Password.
     * @param isExisting Flag to ignore checks for isEmpty.
     */
    private boolean validatePassword(String password, boolean isExisting)
            throws ValidationException {

        if (StringUtils.isNotEmpty(password)) {
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
            Optional<Role> role = roleRepository.findById(roleId);
            return role.isPresent();
        }
        return false;
    }

    @Override
    @Transactional
    public void appendCompetence(Long userId, Long competenceId) throws UserManagerException {
        try {
            User user = userRepository.findById(userId).get();
            Competence competence = competenceRepository.findById(competenceId).get();
            user.addCompetence(competence);
        } catch (Exception e) {
            logger.error("Could not append user to competence", e);
            throw new UserManagerException(
                    "Could not append user to competence", e);
        }
    }

    @Override
    @Transactional
    public void removeUserToCompetence(Long userId, Long competenceId) throws UserManagerException {
        try {
            User user = userRepository.findById(userId).get();
            Competence competence = competenceRepository.findById(competenceId).get();
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
        return openedGroups.stream().filter(Objects::nonNull).map(Group::getName)
                .collect(Collectors.toSet());
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
    public Iterable<User> dynamicSearch(String parameter, String pattern, String role,
            int offset, int limit, boolean isFirstChar) {
        pattern = isFirstChar ? "" : "%" + pattern + "%";
        return userRepository
                .findAll(userRepository.createManagerPredicate(parameter, pattern, role),
                        PageRequest.of(offset - 1, limit)).getContent();
    }

}