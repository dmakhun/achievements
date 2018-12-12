package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.List;

public interface ClassDao extends GenericDao<Group> {

    /**
     * Finds all groups what will be opened
     *
     * @return list of groups
     */
    List<Group> findGroupsToBeOpened();

    /**
     * Find all group with certain competence what will be opened.
     *
     * @return list of group
     */
    List<Group> findGroupsToBeOpened(Long competenceId);

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
