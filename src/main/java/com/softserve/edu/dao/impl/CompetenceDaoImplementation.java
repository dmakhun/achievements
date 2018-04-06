package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("competenceDao")
public class CompetenceDaoImplementation extends
        GenericDaoImplementation<Competence> implements CompetenceDao {

    @Autowired
    private GroupDao groupDao;

    @Override
    public List<Group> showGroups(int groupId) {
        return groupDao.findEntityList(Competence.SHOW_GROUPS, groupId);
    }

    @Override
    public List<Group> findGroupsByCompetenceUuid(String competenceUuid) {
        return groupDao.findEntityList(
                Competence.FIND_GROUPS_BY_COMPETENCE_UUID, competenceUuid);

    }

    @Override
    public Competence findByName(String name) {
        return this.findEntity(Competence.FIND_COMPETENCE_BY_NAME, name);
    }

    @Override
    public List<Competence> findCompetencesByUserId(Long userId) {

        User user = entityManager.find(User.class, userId);

        return new ArrayList<>(user.getCompetences());
    }

    @Override
    public List<Competence> findByUserUuid(String userUuid) {
        return this.findEntityList(Competence.FIND_BY_USER_UUID, userUuid);
    }

    @Override
    public List<Competence> findAllCompetences() {
        return findAll(Competence.class);
    }

}
