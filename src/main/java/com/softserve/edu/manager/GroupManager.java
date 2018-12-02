package com.softserve.edu.manager;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import java.util.Date;
import java.util.List;

public interface GroupManager {

    /**
     * Method finds in database all groups what will be started from current time.
     *
     * @return list of future groups
     */
    List<Group> inFuture();

    /**
     * Method finds in database all groups what will be started from current time. Select only those
     * groups, that belong to given competence.
     *
     * @return list of future groups
     */
    List<Group> inFuture(Long competenceId);

    /**
     * Get groups, connected to given competenceId.
     *
     * @param competenceId Competence id.
     * @param onlyOpened Flag that says to get only opened groups.
     */
    List<Group> findByCompetence(final Long competenceId,
            final boolean onlyOpened);

    /**
     *
     */
    List<Group> findByCompetenceUuid(final String competenceUuid,
            final boolean onlyOpened);

    /**
     * Create new group.
     */
    Long create(final String name, final Date startDate,
            final Date endDate, final Long competenceId) throws GroupManagerException;

    /**
     * Modify existing group.
     *
     * @param groupId ID of existing group.
     * @param name Group name.
     * @param start Opening date.
     * @param end Ending date.
     * @param competenceId Competence id.
     */
    void modify(final Long groupId, final String name, final Date start,
            final Date end, final Long competenceId) throws GroupManagerException;

    /**
     * Add existing user to existing group.
     */
    void addUser(final Long userId, final Long groupId) throws GroupManagerException;

    /**
     * Get users from given group.
     */
    List<User> users(final Long groupId);

    /**
     *
     */
    List<User> findUsersByGroupUuid(String groupUuid);

    /**
     *
     */
    void deleteById(Long groupId) throws GroupManagerException;

    /**
     *
     */
    void addUser(String userUuid, String groupUuid) throws GroupManagerException;

    /**
     *
     */
    Group findGroupByGroupUuid(String uuid);

    /**
     *
     */
    void create(Group group) throws GroupManagerException;

    /**
     *
     */
    boolean validateGroup(Group group);

    /**
     *
     */
    Group findGroupByGroupName(String groupName);

    /**
     *
     */
    void deleteByUuid(String groupUuid) throws GroupManagerException;

    /**
     *
     */
    void removeAssociation(Group group);


}
