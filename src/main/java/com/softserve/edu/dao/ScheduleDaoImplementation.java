package com.softserve.edu.dao;

import com.softserve.edu.entity.ScheduleTable;
import org.springframework.stereotype.Repository;


@Repository("scheduleDao")
public class ScheduleDaoImplementation extends
        GenericDaoImplementation<ScheduleTable> implements ScheduleDao {
}