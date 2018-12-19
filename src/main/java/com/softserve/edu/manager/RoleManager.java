package com.softserve.edu.manager;

import com.softserve.edu.entity.AccessRole;

public interface RoleManager {

    AccessRole findById(Long id);

    AccessRole findRoleByName(String roleName);

}