package com.softserve.edu.dao;

import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.User;
import java.util.List;

public interface ClassDao extends GenericDao<Class> {

    /**
     * Finds all groups what will be opened
     *
     * @return list of groups
     */
    List<Class> findGroupsToBeOpenedByCompetenceId();

    /**
     * Find all group with certain competence what will be opened.
     *
     * @return list of group
     */
    List<Class> findGroupsToBeOpenedByCompetenceId(Long competenceId);

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

    List<Class> findByCompetence(Long competenceId, boolean onlyOpened);

    Class findGroupByName(String name);


}
