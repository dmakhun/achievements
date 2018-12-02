package com.softserve.edu.dao.impl;

import com.softserve.edu.entity.Schedule;
import com.softserve.edu.util.ScheduleMapping;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse csv file in List of Schedule for specific group.
 */
public class ScheduleGroupDaoImpl {

    /**
     * name of specific group.
     */
    private String groupName;

    /**
     * list of Schedule for specific group.
     */
    public List<Schedule> listScheduleForGroup;
    /**
     * List of all Schedule from csv file.
     */
    private List<Schedule> list = ScheduleMapping.mapToCSV();

    /**
     * The default constructor.
     *
     * @param groupName .
     */
    public ScheduleGroupDaoImpl(String groupName) {
        this.groupName = groupName;
        listScheduleForGroup = listScheduleForThisGroup();

    }

    /**
     * List of Schedule for groupName.
     *
     * @return listScheduleForGroup.
     */
    private List<Schedule> listScheduleForThisGroup() {
        List<Schedule> schedules = new ArrayList<>();
        for (Object object : list) {
            Schedule schedule = (Schedule) object;

            if (schedule.getGroup().equals(groupName)) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }
}
