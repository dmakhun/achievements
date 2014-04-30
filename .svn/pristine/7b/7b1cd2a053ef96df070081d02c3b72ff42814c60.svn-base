package com.softserve.edu.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;

@Repository("competenceDao")
public class CompetenceDaoImplementation extends
		GenericDaoImplementation<Competence> implements CompetenceDao {

	@Autowired
	GroupDao groupDao;

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
	public List<Competence> findByUser(Long userId) {

		User user = (User) entityManager.find(User.class, userId);
		List<Competence> list = new ArrayList<>(user.getCompetences());

		return list;
	}

	@Override
	public List<Competence> findByUserUuid(String userUuid) {
		return this.findEntityList(Competence.FIND_BY_USER_UUID, userUuid);
	}

	@Override
	public List<Competence> listWithUsers() {

		List<Competence> list = findAll(Competence.class);
		for (Competence competence : list) {
			competence.getUsers().size();
		}
		return list;
	}

}
