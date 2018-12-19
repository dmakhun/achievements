package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends GenericDao<User> {

    /**
     * Adds user and competence to the attending table.
     *
     * @param user - user you want to attend
     * @param competence - competence you want to attend user to
     */
    void attendUserToCompetence(User user, Competence competence);

    User findByUsername(String username);

    User findByEmail(String email);

    void removeUserToCompetence(User user, Competence competence);

    List<User> findByRole(String role);

}
