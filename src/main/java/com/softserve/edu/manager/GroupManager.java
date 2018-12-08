package com.softserve.edu.manager;

import com.softserve.edu.entity.Class;
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
    List<Class> inFuture();

    /**
     * Method finds in database all groups what will be started from current time. Select only those
     * groups, that belong to given competence.
     *
     * @return list of future groups
     */
    List<Class> inFuture(Long competenceId);

    /**
     * Get groups, connected to given competenceId.
     *
     * @param competenceId Competence id.
     * @param onlyOpened Flag that says to get only opened groups.
     */
    List<Class> findAllByCompetenceId(Long competenceId, boolean onlyOpened);

    /**
     * Create new group.
     */
    Long create(String name, Date startDate,
            Date endDate, Long competenceId) throws GroupManagerException;

    /**
     * Modify existing group.
     *
     * @param groupId ID of existing group.
     * @param name Class name.
     * @param start Opening date.
     * @param end Ending date.
     * @param competenceId Competence id.
     */
    void modify(Long groupId, String name, Date start,
            Date end, Long competenceId) throws GroupManagerException;

    /**
     * Add existing user to existing group.
     */
    void addUser(Long userId, Long groupId) throws GroupManagerException;

    /**
     * Get users from given group.
     */
    List<User> users(Long groupId);

    void deleteById(Long groupId) throws GroupManagerException;

    void create(Class aClass) throws GroupManagerException;

    boolean validateGroup(Class aClass);

    Class findGroupByGroupName(String groupName);

    void removeAssociation(Class aClass);


}
