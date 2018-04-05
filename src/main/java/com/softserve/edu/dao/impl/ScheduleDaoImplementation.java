package com.softserve.edu.dao.impl;

import com.softserve.edu.dao.ScheduleDao;
import com.softserve.edu.entity.ScheduleTable;
import org.springframework.stereotype.Repository;


@Repository("scheduleDao")
public class ScheduleDaoImplementation extends
        GenericDaoImplementation<ScheduleTable> implements ScheduleDao {
}