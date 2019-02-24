package com.softserve.edu.entity;

import com.softserve.edu.util.CsvUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse csv file in List of Schedule for specific group.
 */
public class ScheduleGroup {

    private String groupName;
    public List<Schedule> listScheduleForGroup;
    private List<Schedule> schedules = CsvUtils.mapToCSV();

    /**
     * The default constructor.
     *
     * @param groupName .
     */
    public ScheduleGroup(String groupName) {
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
        for (Object object : this.schedules) {
            Schedule schedule = (Schedule) object;

            if (schedule.getGroup().equals(groupName)) {
                schedules.add(schedule);
            }
        }
        return schedules;
    }
}
