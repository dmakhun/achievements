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

@Service("userManager")
public class UserManagerImp implements UserManager {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AchievementDao achievementDao;
    @Autowired
    private CompetenceDao competenceDao;

    private static StandardPasswordEncoder encoder = new StandardPasswordEncoder();

    private static final String COULD_NOT_UPDATE_USER = "Could not update User";
    private static final String USER_COULD_NOT_BE_SAVED = "user cannot be created!";
    private static final String FIELDS_VALIDATION_ERROR = "filds doesn't validated!";
    private static final String ROLE_DOES_NOT_EXIST = "Role does not exist!";
    private static final Logger logger = Logger.getLogger(UserManagerImp.class);
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
    public User create(final String name, final String surname,
                       final String username, final String password, final String email,
                       final Long roleId) throws UserManagerException {

        User user;
        try {
            user = validateFields(false, null, name, surname, username,
                    password, email, roleId);
        } catch (Exception e) {
            logger.error("Fields didn't validate", e);
            throw new UserManagerException("Fields didn't validate", e);
        }
        try {
            userDao.save(user);
        } catch (Exception e) {
            logger.error(USER_COULD_NOT_BE_SAVED, e);
            throw new UserManagerException(USER_COULD_NOT_BE_SAVED, e);
        }

        return user;
    }

    @Override
    @Transactional
    public void create(User user) throws UserManagerException {
        try {
            userDao.save(validateUser(user));
        } catch (Exception e) {
            logger.error("Could not create user", e);
            throw new UserManagerException("Could not create user", e);
        }
    }

    @Override
    @Transactional
    public User update(final Long userId, final String name,
                       final String surname, final String username, final String password,
                       final String email, final Long roleId) throws UserManagerException {

        User user = userDao.findById(User.class, userId);

        if (user == null) {
            logger.error("User does not exist!");
            throw new UserManagerException("User does not exist!");
        }

        try {
            user = validateFields(true, user, name, surname, username,
                    password, email, roleId);
            userDao.update(user);
        } catch (Exception e) {
            logger.error(COULD_NOT_UPDATE_USER + e);
            throw new UserManagerException(COULD_NOT_UPDATE_USER + e);
        }

        return user;
    }

    @Override
    @Transactional
    public User update(final String userUuid, final String name,
                       final String surname, final String username, final String password,
                       final String email, final String roleUuid)
            throws UserManagerException {

        User user = userDao.findByUuid(User.class, userUuid);

        if (user == null) {
            logger.error("User with such uuid doesn't exist.");
            throw new UserManagerException("User with such uuid doesn't exist.");
        }

        try {
            user = validateExistingUser(user);
        } catch (Exception e) {
            logger.error(FIELDS_VALIDATION_ERROR, e);
            throw new UserManagerException(FIELDS_VALIDATION_ERROR, e);
        }
        try {
            userDao.update(user);
        } catch (Exception e) {
            logger.error("The user was not updated");
            throw new UserManagerException();
        }

        return user;
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

    /**
     * Superkaramba checks.
     * <p>
     * Throw exception with description if some value violate some rule. Some
     * check types can be disabled: we can disable, for example, empty string
     * check. This can be useful when we updating someone's profile.
     * <p>
     * „Nevermind“ parameter („true“) should be only used for editing
     * existing users.
     *
     * @param nevermindEmpty Ignore empty values; can be useful for editing user.
     * @param currentUser    Long Id of current user; null if there is no user in that
     *                       context.
     * @param name           First name.
     * @param surname        Second name.
     * @param username       Unique username.
     * @param password       Password.
     * @param email          Unique email.
     * @param roleId         Role id.
     * @return Parsed user.
     * @throws UserManagerException
     */
    @Transactional
    User validateFields(final boolean nevermindEmpty,
                        final User currentUser, final String name, final String surname,
                        final String username, final String password, final String email,
                        final Long roleId) throws UserManagerException {

        User user = currentUser == null ? new User() : currentUser;
        boolean validator;

        validator = genericValidation(user, name, "Name", nevermindEmpty);
        if (validator) {
            user.setName(name);
        }

        validator = genericValidation(user, surname, "Surname", nevermindEmpty);
        if (validator) {
            user.setSurname(surname);
        }

        /*
          Besides matching naming rules, such name should be unique. OtherUser
          here is the user, that already can own same username.
         */
        User otherUser = findByUsername(username);
        validator = validateByPattern(user, otherUser, username,
                PATTERN_USERNAME, "Username", nevermindEmpty);
        if (validator) {
            user.setUsername(username);
        }

        validator = validatePassword(password, nevermindEmpty);
        if (validator) {
            user.setPassword(encoder.encode(password));
        }

        /*
          Same logic as for username checks.
         */
        otherUser = findByEmail(email);
        validator = validateByPattern(user, otherUser, email, PATTERN_EMAIL,
                "Email", nevermindEmpty);
        if (validator) {
            user.setEmail(email);
        }

        Role role = validateRole(roleId);
        if (role != null) {
            user.setRole(role);
        }

        return user;
    }

    @Transactional
    User validateUser(User user) throws UserManagerException {

        boolean validated;
        validated = genericValidation(user, user.getName(), "Name", false);
        if (!validated) {
            throw new ValidationException();
        }

        validated = genericValidation(user, user.getSurname(), "Surname", false);
        if (!validated) {
            throw new ValidationException();
        }

        /*
          Besides matching naming rules, such name should be unique. OtherUser
          here is the user, that can own already same username.
         */
        User otherUser = findByUsername(user.getUsername());
        validated = validateByPattern(user, otherUser, user.getUsername(),
                PATTERN_USERNAME, "Username", false);
        if (!validated) {
            throw new ValidationException();
        }

        validated = validatePassword(user.getPassword(), false);
        if (validated) {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        /*
         * Same logic as for username checks.
         */
        otherUser = findByEmail(user.getEmail());
        validated = validateByPattern(user, otherUser, user.getEmail(), PATTERN_EMAIL,
                "Email", false);
        if (!validated) {
            throw new ValidationException();
        }

        Role role = validateRoleByUuid(user.getRole().getUuid());
        if (role == null) {
            throw new ValidationException();
        }

        return user;
    }

    @Transactional
    User validateExistingUser(User user) throws UserManagerException {

        boolean validated;
        validated = genericValidation(user, user.getName(), "Name", true);
        if (!validated) {
            throw new ValidationException();
        }

        validated = genericValidation(user, user.getSurname(), "Surname", true);
        if (!validated) {
            throw new ValidationException();
        }

        /*
          Besides matching naming rules, such name should be unique. OtherUser
          here is the user, that can own already same username.
         */
        User otherUser = findByUsername(user.getUsername());
        validated = validateByPattern(user, otherUser, user.getUsername(),
                PATTERN_USERNAME, "Username", true);
        if (!validated) {
            throw new ValidationException();
        }

        validated = validatePassword(user.getPassword(), true);
        if (validated) {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        /*
         * Same logic as for username checks.
         */
        otherUser = findByEmail(user.getEmail());
        validated = validateByPattern(user, otherUser, user.getEmail(), PATTERN_EMAIL,
                "Email", true);
        if (!validated) {
            throw new ValidationException();
        }

        Role role = validateRoleByUuid(user.getRole().getUuid());
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
     * @param user           User, that will get those field.
     * @param field          Field, yep.
     * @param nevermindEmpty Flag, that says that we can ignore checks for emptiness.
     * @return String
     * @throws UserManagerException
     */
    @Transactional
    boolean genericValidation(final User user, final String field,
                              final String fieldName, final boolean nevermindEmpty)
            throws UserManagerException {

        if (field != null && !field.isEmpty() && field.length() <= 50) {
            return true;
        } else {
            if ((field == null || field.isEmpty()) && !nevermindEmpty) {
                logger.error(fieldName + "fild doesn't validated!");
                throw new UserManagerException(fieldName
                        + "fild doesn't validated!");
            }

            if (field != null && field.length() > 50) {
                logger.error(fieldName + "fild doesn't validated!");
                throw new UserManagerException(fieldName
                        + "field doesn't validated!");
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

    /**
     * @param roleUuid
     * @return
     * @throws UserManagerException
     */
    private Role validateRoleByUuid(final String roleUuid)
            throws UserManagerException {

        Role role = null;
        if (roleUuid != null) {
            role = roleDao.findByUuid(Role.class, roleUuid);
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
    public void update(User user) throws UserManagerException {
        try {
            userDao.update(user);
        } catch (Exception e) {
            logger.error(COULD_NOT_UPDATE_USER, e);
            throw new UserManagerException(COULD_NOT_UPDATE_USER, e);
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