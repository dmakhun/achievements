package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.dao.GroupDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("competenceDao")
public class CompetenceDaoImpl extends
        GenericDaoImpl<Competence> implements CompetenceDao {

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Class> findGroupsByCompetenceId(int competenceId) {
        return groupDao.findEntityList(Competence.FIND_GROUPS_BY_COMPETENCE_ID, competenceId);
    }

    @Override
    public Competence findByName(String name) {
        return findEntity(Competence.FIND_COMPETENCE_BY_NAME, name);
    }

    @Override
    public List<Competence> findCompetencesByUserId(Long userId) {
        User user = userDao.findById(User.class, userId);
        return new ArrayList<>(user.getCompetences());
    }

    @Override
    public List<Competence> findAllCompetences() {
        return findAll(Competence.class);
    }

}
