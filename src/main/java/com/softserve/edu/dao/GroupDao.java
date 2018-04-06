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
    List<Group> inFuture();

    /**
     * Find all group with certain competence what will be opened.
     *
     * @param competenceId
     * @return list of group
     */
    List<Group> inFuture(Long competenceId);

    /**
     * All users in some group.
     *
     * @return List of users
     */
    List<User> userList(Long groupId);

    /**
     * Get groups connected to given competence.
     *
     * @param competenceUuid
     * @param onlyOpened
     * @return
     */
    List<Group> findByCompetenceUuid(final String competenceUuid,
                                     final boolean onlyOpened);

    /**
     * Add user to group.
     *
     * @param userId
     * @param groupId
     */
    void addUser(final Long userId, final Long groupId);

    /**
     * @param groupUuid
     * @return
     */
    List<User> findUsersByGroupUuid(String groupUuid);

    /**
     * @param competenceId
     * @param onlyOpened
     * @return
     */
    List<Group> findByCompetence(Long competenceId, boolean onlyOpened);

    /**
     * @param name
     * @return
     */
    Group findGroupByName(String name);

    /**
     * @param userUuid
     * @param groupUuid
     */
    void addUser(String userUuid, String groupUuid);


}
