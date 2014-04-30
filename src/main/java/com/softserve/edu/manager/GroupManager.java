package com.softserve.edu.manager;

import java.util.Date;
import java.util.List;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;

public interface GroupManager {

	/**
	 * Method finds in database all groups what will be started from current
	 * time.
	 * 
	 * @return list of future groups
	 */
	public List<Group> inFuture();

	/**
	 * Method finds in database all groups what will be started from current
	 * time. Select only those groups, that belong to given competence.
	 * 
	 * @return list of future groups
	 */
	public List<Group> inFuture(Long competenceId);

	/**
	 * Get groups, connected to given competenceId.
	 * 
	 * @param competenceId
	 *            Competence id.
	 * @param onlyOpened
	 *            Flag that says to get only opened groups.
	 * @return
	 */
	public List<Group> findByCompetence(final Long competenceId,
			final boolean onlyOpened);

	/**
	 * @param competenceUuid
	 * @param onlyOpened
	 * @return
	 */
	public List<Group> findByCompetenceUuid(final String competenceUuid,
			final boolean onlyOpened);

	/**
	 * Create new group.
	 * 
	 * @param name
	 * @param date
	 * @param competenceId
	 * @throws GroupManagerException 
	 */
	public Long create(final String name, final Date startDate,
			final Date endDate, final Long competenceId) throws GroupManagerException;

	/**
	 * Modify existing group.
	 * 
	 * @param groupId ID of existing group.
	 * @param name Group name.
	 * @param start Opening date.
	 * @param end Ending date.
	 * @param competenceId Competence id.
	 * @throws GroupManagerException 
	 */
	public void modify(final Long groupId, final String name, final Date start,
			final Date end, final Long competenceId) throws GroupManagerException;

	/**
	 * Add existing user to existing group.
	 * 
	 * @param userId
	 * @param groupId
	 * @throws GroupManagerException 
	 */
	public void addUser(final Long userId, final Long groupId) throws GroupManagerException;

	/**
	 * Get users from given group.
	 * 
	 * @param groupId
	 * @return
	 */
	public List<User> users(final Long groupId);

	/**
	 * @param groupUuid
	 * @return
	 */
	public List<User> findUsersByGroupUuid(String groupUuid);

	/**
	 * @param groupId
	 * @throws GroupManagerException
	 */
	public void deleteById(Long groupId) throws GroupManagerException;

	/**
	 * @param userUuid
	 * @param groupUuid
	 * @throws GroupManagerException
	 */
	public void addUser(String userUuid, String groupUuid) throws GroupManagerException;

	/**
	 * @param uuid
	 * @return
	 */
	public Group findGroupByGroupUuid(String uuid);

	/**
	 * @param group
	 * @throws GroupManagerException
	 */
	void create(Group group) throws GroupManagerException;

	/**
	 * @param group
	 * @return
	 */
	boolean validateGroup(Group group);

	/**
	 * @param groupName
	 * @return
	 */
	Group findGroupByGroupName(String groupName);

	/**
	 * @param groupUuid
	 * @throws GroupManagerException
	 */
	void deleteByUuid(String groupUuid) throws GroupManagerException;

	/**
	 * @param group
	 */
	void removeAssociation(Group group);


}
