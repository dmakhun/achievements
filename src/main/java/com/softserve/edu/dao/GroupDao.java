package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.List;

public interface GroupDao extends GenericDao<Group> {

    /**
     * Find all group with certain competence what will be opened.
     *
     * @return list of group
     */
    List<Group> findGroupsToBeOpenedByCompetenceId(Long competenceId);

    /**
     * All users in some group.
     *
     * @return List of users
     */
    List<User> userList(Long groupId);

    /**
     * Add user to group.
     */
    void addUser(Long userId, Long groupId);

    List<Group> findByCompetence(Long competenceId, boolean onlyOpened);

    Group findGroupByName(String name);


}
