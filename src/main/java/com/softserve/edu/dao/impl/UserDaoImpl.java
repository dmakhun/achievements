package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.dao.RoleDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User>
        implements UserDao {

    public static final String SUM_OF_POINTS = "select sum(atype.points) from AchievementType atype where atype in(select ac.achievementType  "
            + "from Achievement ac WHERE user_id like :user_id)";
    public static final String COUNT_ALL_MANAGERS = "select count(*) FROM User u inner join fetch u.role r WHERE r.id = ?1";

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private CompetenceDao competenceDao;
    @Autowired
    private GroupDao groupDao;

    @Override
    public void attendUserToCompetence(User user, Competence competence) {
        user.getCompetences().add(competence);
    }

    @Override
    public void removeUserToCompetence(User user, Competence competence) {

        user.getCompetences().remove(competence);
        competence.getUsers().remove(user);

    }

    @Override
    public User findByUsername(String username) {

        try {
            if (username == null || username.isEmpty()) {
                return null;
            }

            return this.findEntity(User.FIND_USER_BY_NAME, username);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        return this.findEntity(User.FIND_USER_BY_EMAIL, email);
    }

    @Override
    public List<Group> findGroups(Long userId, boolean onlyOpened) {

        if (onlyOpened) {
            return groupDao.findEntityList(User.FIND_ONLY_OPENED_GROUPS,
                    userId, new Date());
        }
        return groupDao.findEntityList(User.FIND_GROUPS, userId);

    }

    @Override
    public Long countManagers() {
        return (Long) entityManager
                .createQuery(COUNT_ALL_MANAGERS)
                .setParameter("user_id", roleDao.findRole("ROLE_MANAGER")).getSingleResult();
    }

    @Override
    public List<User> findAllManagers() {
        return findEntityList(User.FIND_ALL_USERS_BY_ROLE, roleDao.findRole("ROLE_MANAGER"));
    }

    public Long sumOfPoints(User user) {
        return (Long) entityManager
                .createQuery(
                        SUM_OF_POINTS)
                .setParameter("user_id", user.getId()).getSingleResult();
    }
}
