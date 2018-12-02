package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.AchievementType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("achievementTypeDao")
public class AchievementTypeDaoImpl extends
        GenericDaoImpl<AchievementType> implements AchievementTypeDao {

    @Autowired
    private UserDao userDao;

    @Override
    public List<AchievementType> findByCompetenceId(Long competenceId) {
        return findEntityList(AchievementType.GET_LIST_ACHIEVEMENT_TYPE,
                competenceId);
    }

    @Override
    public List<AchievementType> findByCompetenceUuid(String competenceUuid) {
        return findEntityList(
                AchievementType.GET_ACHIEVEMENT_TYPES_BY_COMPETENCE_UUID,
                competenceUuid);
    }

}
