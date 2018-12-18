package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import java.util.List;

public interface GroupDao extends GenericDao<Group> {

    List<Group> findByCompetenceId(Long competenceId, boolean onlyOpened);

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


}
