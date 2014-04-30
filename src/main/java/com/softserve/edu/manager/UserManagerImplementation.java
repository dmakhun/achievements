package com.softserve.edu.manager;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.edu.dao.AchievementDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Achievement;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.Role;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.InvalidValueException;
import com.softserve.edu.exception.UserManagerException;

@Service("userManager")
public class UserManagerImplementation implements UserManager {

	private static final String COULD_NOT_UPDATE_USER = "Could not update User";
	private static final String USER_COULD_NOT_BE_SAVED = "user cannot be created!";
	private static final String FILDS_DOES_NOT_VALIDATED = "filds doesn't validated!";
	private static final String ROLE_DOES_NOT_EXIST = "Role does not exist!";
	private static final Logger LOGGER = Logger
			.getLogger(UserManagerImplementation.class);

	@Autowired
	UserDao userDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	AchievementDao AchievementDao;
	@Autowired
	CompetenceDao competenceDao;

	static StandardPasswordEncoder encoder = new StandardPasswordEncoder();
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

		User user = null;
		try {
			user = validateFields(false, null, name, surname, username,
					password, email, roleId);
		} catch (Exception e) {
			LOGGER.error("filds doesn't validated!", e);
			throw new UserManagerException("filds doesn't validated!", e);
		}
		try {
			userDao.save(user);
		} catch (Exception e) {
			LOGGER.error(USER_COULD_NOT_BE_SAVED, e);
			throw new UserManagerException(USER_COULD_NOT_BE_SAVED, e);
		}

		return user;
	}

	@Override
	@Transactional
	public void create(User user) throws UserManagerException {

		try {
			User userToSave = validateFields(false, null, user.getName(),
					user.getSurname(), user.getUsername(), user.getPassword(),
					user.getEmail(), user.getRole().getUuid());

			userDao.save(userToSave);

		} catch (Exception e) {
			LOGGER.error("Could not create user", e);
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
			LOGGER.error("User does not exist!");
			throw new UserManagerException("User does not exist!");
		}

		try {
			user = validateFields(true, user, name, surname, username,
					password, email, roleId);

			userDao.update(user);

		} catch (Exception e) {
			LOGGER.error(COULD_NOT_UPDATE_USER + e);
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
			LOGGER.error("User with such uuid doesn't exist.");
			throw new UserManagerException("User with such uuid doesn't exist.");
		}

		try {
			user = validateFields(true, user, name, surname, username,
					password, email, roleUuid);
		} catch (Exception e) {
			LOGGER.error(FILDS_DOES_NOT_VALIDATED, e);
			throw new UserManagerException(FILDS_DOES_NOT_VALIDATED, e);
		}
		try {
			userDao.update(user);
		} catch (Exception e) {
			LOGGER.error("The user was not updated");
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
			LOGGER.error("Delete user by id", e);
			throw new UserManagerException("Delete user by id", e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User findById(final Long id) throws UserManagerException {

		try {
			return userDao.findById(User.class, id);

		} catch (RuntimeException e) {
			LOGGER.error("Could not find user by id", e);
			throw new UserManagerException("Could not find user by id", e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User findByUuid(final String uuid) {
		User user = userDao.findByUuid(User.class, uuid);

		if (user == null) {
			LOGGER.error("User with such uuid doesn't exist.");
		} else {
			LOGGER.info("User was found");
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
	 * 
	 * Throw exception with description if some value violate some rule. Some
	 * check types can be disabled: we can disable, for example, empty string
	 * check. This can be useful when we updating someone's profile.
	 * 
	 * „Nevermind“ parameter („true“) should be only used for editing
	 * existing users.
	 * 
	 * @param nevermindEmpty
	 *            Ignore empty values; can be useful for editing user.
	 * @param currentUser
	 *            Long Id of current user; null if there is no user in that
	 *            context.
	 * @param name
	 *            First name.
	 * @param surname
	 *            Second name.
	 * @param username
	 *            Unique username.
	 * @param password
	 *            Password.
	 * @param email
	 *            Unique email.
	 * @param roleId
	 *            Role id.
	 * @return User Parsed user.
	 * @throws InvalidValueException
	 * @throws NoSuchAlgorithmException
	 * 
	 * @author vkudrtc
	 * @throws UserManagerException
	 */
	@Transactional
	private User validateFields(final boolean nevermindEmpty,
			final User currentUser, final String name, final String surname,
			final String username, final String password, final String email,
			final Long roleId) throws UserManagerException {

		User user = currentUser == null ? new User() : currentUser;
		boolean validator;

		validator = validateGeneric(user, name, "Name", nevermindEmpty);
		if (validator) {
			user.setName(name);
		}

		validator = validateGeneric(user, surname, "Surname", nevermindEmpty);
		if (validator) {
			user.setSurname(surname);
		}

		/**
		 * Besides matching naming rules, such name should be unique. OtherUser
		 * here is the user, that already can own same username.
		 */
		User otherUser = findByUsername(username);
		validator = validateByPattern(user, otherUser, username,
				PATTERN_USERNAME, "Username", nevermindEmpty);
		if (validator) {
			user.setUsername(username);
		}

		validator = validatePassword(password, nevermindEmpty);
		if (validator) {
			// user.setSalt(PasswordUtil.generatePassOrSalt(8));
			user.setPassword(encoder.encode(password));
		}

		/**
		 * Same logic as for username checks.
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

	/**
	 * @param nevermindEmpty
	 * @param currentUser
	 * @param name
	 * @param surname
	 * @param username
	 * @param password
	 * @param email
	 * @param roleUuid
	 * @return
	 * @throws UserManagerException
	 */
	@Transactional
	private User validateFields(final boolean nevermindEmpty,
			final User currentUser, final String name, final String surname,
			final String username, final String password, final String email,
			final String roleUuid) throws UserManagerException {

		User user = currentUser == null ? new User() : currentUser;

		boolean validator = validateGeneric(user, name, "Name", nevermindEmpty);
		if (validator) {
			user.setName(name);
		}

		validator = validateGeneric(user, surname, "Surname", nevermindEmpty);
		if (validator) {
			user.setSurname(surname);
		}

		/**
		 * Besides matching naming rules, such name should be unique. OtherUser
		 * here is the user, that can own already same username.
		 */
		User otherUser = null;
		try {
			otherUser = findByUsername(username);
		} catch (IllegalArgumentException e) {

		}
		validator = validateByPattern(user, otherUser, username,
				PATTERN_USERNAME, "Username", nevermindEmpty);
		if (validator) {
			user.setUsername(username);
		}

		validator = validatePassword(password, nevermindEmpty);
		if (validator) {
			// user.setSalt(PasswordUtil.generatePassOrSalt(8));
			user.setPassword(encoder.encode(password));
		}

		/**
		 * Same logic as for username checks.
		 */

		try {
			otherUser = findByEmail(email);
		} catch (IllegalArgumentException e) {
		}

		validator = validateByPattern(user, otherUser, email, PATTERN_EMAIL,
				"Email", nevermindEmpty);
		if (validator) {
			user.setEmail(email);
		}
		Role role = validateRoleByUuid(roleUuid);

		if (role != null) {
			user.setRole(role);
		}

		return user;
	}

	/**
	 * Generic field validation.
	 * 
	 * This means, that field should meet general rules: not empty, less than 50
	 * chars.
	 * 
	 * @param user
	 *            User, that will get those field.
	 * @param field
	 *            Field, yep.
	 * @param nevermindEmpty
	 *            Flag, that says that we can ignore checks for emptiness.
	 * @return String
	 * @throws InvalidValueException
	 * 
	 * @author vkudrtc
	 * @throws UserManagerException
	 */
	@Transactional
	private boolean validateGeneric(final User user, final String field,
			final String fieldName, final boolean nevermindEmpty)
			throws UserManagerException {

		if (field != null && !field.isEmpty() && field.length() <= 50) {
			return true;
		} else {
			if ((field == null || field.isEmpty()) && !nevermindEmpty) {
				LOGGER.error(fieldName + "fild doesn't validated!");
				throw new UserManagerException(fieldName
						+ "fild doesn't validated!");
			}

			if (field != null && field.length() > 50) {
				LOGGER.error(fieldName + "fild doesn't validated!");
				throw new UserManagerException(fieldName
						+ "fild doesn't validated!");
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
	 * @throws InvalidValueException
	 * 
	 * @author vkudrtc
	 * @throws UserManagerException
	 */
	@Transactional
	private boolean validateByPattern(final User user, final User otherUser,
			final String field, final String pattern, final String fieldName,
			final boolean nevermindEmpty) throws UserManagerException {

		if (field != null && field.matches(pattern)
				&& (otherUser == null || otherUser.getId() == user.getId())) {
			return true;
		} else {
			if ((field == null || field.isEmpty()) && !nevermindEmpty) {
				LOGGER.error(fieldName + FILDS_DOES_NOT_VALIDATED);
				throw new UserManagerException(fieldName
						+ FILDS_DOES_NOT_VALIDATED);
			}

			if (field != null && !field.isEmpty() && !field.matches(pattern)) {
				LOGGER.error(fieldName + FILDS_DOES_NOT_VALIDATED);
				throw new UserManagerException(fieldName
						+ FILDS_DOES_NOT_VALIDATED);
			}

			if (otherUser != null && otherUser.getId() != user.getId()) {
				LOGGER.error(fieldName + FILDS_DOES_NOT_VALIDATED);
				throw new UserManagerException(fieldName + "user exist!");
			}
		}

		return false;
	}

	/**
	 * Validate password.
	 * 
	 * Actually, it can be almost anything. The only requirement for now is that
	 * it has to be non-empty.
	 * 
	 * 
	 * @param user
	 *            User.
	 * @param password
	 *            Password.
	 * @param nevermindEmpty
	 *            Flag to ignore checks for isEmpty.
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidValueException
	 * 
	 * @author vkudrtc
	 * @throws UserManagerException
	 */
	@Transactional
	private boolean validatePassword(final String password,
			final boolean nevermindEmpty) throws UserManagerException {

		if (password != null && !password.isEmpty()) {
			return true;
		} else {
			if ((password == null || password.isEmpty()) && !nevermindEmpty) {
				LOGGER.error("Password cannot be empty.");
				throw new UserManagerException("Password cannot be empty.");
			}
		}

		return false;
	}

	/**
	 * Validate role.
	 * 
	 * @param user
	 * @param roleId
	 * @return Role
	 * @throws UserManagerException
	 * @throws InvalidValueException
	 */
	private Role validateRole(final Long roleId) throws UserManagerException {

		Role role = null;
		if (roleId != null) {
			role = roleDao.findById(Role.class, roleId);

			if (role == null) {
				LOGGER.error(ROLE_DOES_NOT_EXIST);
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
				LOGGER.error(ROLE_DOES_NOT_EXIST);
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
	public void attendCompetence(Long userId, Long competenceId)
			throws UserManagerException {
		User user = userDao.findById(User.class, userId);
		Competence competence = competenceDao.findById(Competence.class,
				competenceId);
		try {
			userDao.attendUserToCompetence(user, competence);
		} catch (Exception e) {
			LOGGER.error("Could not attend competence to user", e);
			throw new UserManagerException(
					"Could not attend competence to user", e);
		}
	}

	@Override
	@Transactional
	public void attendCompetence(String userUuid, String competenceUuid)
			throws UserManagerException {

		User user = userDao.findByUuid(User.class, userUuid);
		Competence competence = competenceDao.findByUuid(Competence.class,
				competenceUuid);

		try {
			userDao.attendUserToCompetence(user, competence);
		} catch (Exception e) {
			LOGGER.error("Could not add competence to user", e);
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
			LOGGER.error("Could not remove user to competence", e);
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
			LOGGER.error("Could not remove user to competence", e);
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
		Set<String> nameGroups = new HashSet<String>();
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
	public boolean existUserName(String userName) {
		return userDao.findByUsername(userName) != null ? true : false;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public boolean existEmail(String email) {
		return userDao.findByEmail(email) != null ? true : false;
	}

	@Override
	@Transactional
	public void deleteByUuid(String userUuid) throws UserManagerException {
		try {
			User user = findByUuid(userUuid);
			removeAssociation(user);
			userDao.delete(user);
			LOGGER.info("User with uuid [" + userUuid + "] was removed");
		} catch (Exception e) {
			LOGGER.error("Could not delete user by uuid", e);
			throw new UserManagerException("Could not delete user by uuid", e);
		}
	}

	@Override
	@Transactional
	public void update(User user) throws UserManagerException {
		try {
			userDao.update(user);
		} catch (Exception e) {
			LOGGER.error(COULD_NOT_UPDATE_USER, e);
			throw new UserManagerException(COULD_NOT_UPDATE_USER, e);
		}
	}

	@Override
	@Transactional
	public void removeAssociation(User user) {

		Set<Group> groups = user.getGroups();
		for (Group g : groups) {
			g.getUsers().remove(user);
		}
		groups.clear();
		user.setGroups(groups);

		Set<Competence> competences = user.getCompetences();
		for (Competence c : competences) {
			c.getUsers().remove(user);
		}
		competences.clear();
		user.setCompetences(competences);

		Set<Achievement> achievements = user.getAchievements();
		for (Achievement a : achievements) {
			AchievementDao.delete(a);
		}
		LOGGER.info("User association was removed");
	}

	@Override
	public Long sumOfPoints(User user) {
		return userDao.sumOfPoints(user);
	}
	
	@Override
	@Transactional
	public List<User>findAllManagers(){
		return userDao.findAllManagers();
	}
	
}