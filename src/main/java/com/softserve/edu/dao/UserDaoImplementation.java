package com.softserve.edu.dao;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository("userDao")
public class UserDaoImplementation extends GenericDaoImplementation<User>
        implements UserDao {

    @Autowired
    RoleDao roleDao;
    @Autowired
    CompetenceDao competenceDao;
    @Autowired
    GroupDao groupDao;

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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder
                .createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root));

        criteriaQuery.where(criteriaBuilder.equal(root.get("role"),
                roleDao.findRole("ROLE_MANAGER")));

        TypedQuery<Long> countQuery = entityManager.createQuery(criteriaQuery);
        Long totalObjectsNumber = countQuery.getSingleResult();

        return totalObjectsNumber;
    }

    public Long sumOfPoints(User user) {
        Long points = (Long) entityManager
                .createQuery(
                        "select sum(at.points) from AchievementType at where at in(select ac.achievementType  "
                                + "from Achievement ac WHERE user_id like :user_id)")
                .setParameter("user_id", user.getId()).getSingleResult();
        if (points == null) {
            return 0l;
        }
        return points;
    }

    @Override
    public List<User> findAllManagers() {
        return findEntityList(User.FIND_ALL_BY_ROLE,
                roleDao.findRole("ROLE_MANAGER"));
    }
}
