package com.softserve.edu.dao;

import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.User;
import java.util.List;

public interface GroupDao extends GenericDao<Class> {

    /**
     * Finds all groups what will be opened
     *
     * @return list of groups
     */
    List<Class> findGroupsToBeOpened();

    /**
     * Find all group with certain competence what will be opened.
     *
     * @return list of group
     */
    List<Class> findGroupsToBeOpened(Long competenceId);

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
