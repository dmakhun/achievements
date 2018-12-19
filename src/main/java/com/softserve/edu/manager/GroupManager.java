package com.softserve.edu.manager;

import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.GroupManagerException;
import java.util.Date;

public interface GroupManager {

    /**
     * Create new group.
     */
    Long create(String name, Date startDate,
            Date endDate, Long competenceId) throws GroupManagerException;

    /**
     * Modify existing group.
     *
     * @param groupId ID of existing group.
     * @param name Group name.
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

    void create(Group aGroup) throws GroupManagerException;

    boolean validateGroup(Group aGroup);

    void removeAssociation(Group aGroup);


}
