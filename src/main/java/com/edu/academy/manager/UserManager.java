package com.edu.academy.manager;

import com.edu.academy.exception.UserManagerException;
import com.edu.academy.entity.User;

import java.util.Set;

public interface UserManager {

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

    void deleteById(Long id) throws UserManagerException;

    User findByUsername(String username);

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

    Set<String> findOpenedGroupNames(String username);

    boolean isUsernameExists(String username);

    boolean isEmailExists(String email);

    void removeAssociations(User user);

    Iterable<User> dynamicSearch(String parameter, String pattern, String role, int offset,
            int limit, boolean isFirstChar);
}
