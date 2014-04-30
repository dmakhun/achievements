package com.softserve.edu.dao;

import org.springframework.stereotype.Repository;
import com.softserve.edu.entity.ScheduleTable;


@Repository("scheduleDao")
public class ScheduleDaoImplementation extends
		GenericDaoImplementation<ScheduleTable> implements ScheduleDao {
}