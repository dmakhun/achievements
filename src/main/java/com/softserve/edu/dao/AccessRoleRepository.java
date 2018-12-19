package com.softserve.edu.dao;

import com.softserve.edu.entity.AccessRole;
import org.springframework.data.repository.CrudRepository;

public interface AccessRoleRepository extends CrudRepository<AccessRole, Long> {

    AccessRole findByName(String name);

}
