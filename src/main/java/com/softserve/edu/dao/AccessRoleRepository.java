package com.softserve.edu.dao;

import com.softserve.edu.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface AccessRoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

}
