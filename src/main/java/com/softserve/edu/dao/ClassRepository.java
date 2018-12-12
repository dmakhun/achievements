package com.softserve.edu.dao;

import com.softserve.edu.entity.Group;
import org.springframework.data.repository.CrudRepository;

public interface ClassRepository extends CrudRepository<Group, Long> {

}
