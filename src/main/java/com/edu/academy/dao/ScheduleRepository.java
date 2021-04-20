package com.edu.academy.dao;

import com.edu.academy.entity.ScheduleTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleTable, Long> {


}
