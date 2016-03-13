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
    public List<Group> inFuture();

    /**
     * @param Find all group with certain competence what will be opened.
     * @return list of group
     */
    public List<Group> inFuture(Long competenceId);

    /**
     * All users in some group.
     *
     * @return List<User>
     */
    public List<User> userList(Long groupId);

    /**
     * Get groups connected to given competence.
     *
     * @param competenceId
     * @param onlyOpened
     * @return
     */
    public List<Group> findByCompetenceUuid(final String competenceUuid,
                                            final boolean onlyOpened);

    /**
     * Add user to group.
     *
     * @param userId
     * @param groupId
     */
    public void addUser(final Long userId, final Long groupId);

    /**
     * @param groupUuid
     * @return
     */
    public List<User> findUsersByGroupUuid(String groupUuid);

    /**
     * @param competenceId
     * @param onlyOpened
     * @return
     */
    public List<Group> findByCompetence(Long competenceId, boolean onlyOpened);

    /**
     * @param name
     * @return
     */
    public Group findGroupByName(String name);

    /**
     * @param userUuid
     * @param groupUuid
     */
    public void addUser(String userUuid, String groupUuid);


}
