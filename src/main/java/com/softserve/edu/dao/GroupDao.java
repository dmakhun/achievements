package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.List;

public interface GroupDao extends GenericDao<Group> {

    /**
     * Finds all groups what will be opened,
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
     * Get groups connected to given competence.
     */
    List<Group> findByCompetenceUuid(final String competenceUuid,
            final boolean onlyOpened);

    /**
     * Add user to group.
     */
    void addUser(final Long userId, final Long groupId);

    /**
     *
     */
    List<User> findUsersByGroupUuid(String groupUuid);

    /**
     *
     */
    List<Group> findByCompetence(Long competenceId, boolean onlyOpened);

    /**
     *
     */
    Group findGroupByName(String name);

    /**
     *
     */
    void addUser(String userUuid, String groupUuid);


}
