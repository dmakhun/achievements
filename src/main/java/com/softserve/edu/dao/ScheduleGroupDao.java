package com.softserve.edu.dao;

import java.util.ArrayList;
import java.util.List;

import com.softserve.edu.entity.Schedule;
import com.softserve.edu.util.ScheduleMapping;

/**
 * class ScheduleGroupDao parse csv file in List of Schedule for specific group.
 * 
 * @author edgar.
 */
public class ScheduleGroupDao {

	/**
	 * name of specific group.
	 */
	public String groupName;

	/**
	 * list of Schedule for specific group.
	 */
	public List<Schedule> listScheduleForGroup;

	/**
	 * The default constructor.
	 * 
	 * @param groupName
	 *            .
	 */
	public ScheduleGroupDao(String groupName) {
		this.groupName = groupName;
		this.listScheduleForGroup = this.listScheduleForThisGroup();

	}

	/**
	 * List of all Schedule from csv file.
	 */
	private List<Schedule> list = ScheduleMapping.mapToCSV();

	/**
	 * List of Schedule for groupName.
	 * 
	 * @return listScheduleForGroup.
	 */
	private List<Schedule> listScheduleForThisGroup() {
		List<Schedule> l = new ArrayList<Schedule>();
		for (Object object : list) {
			Schedule schedule = (Schedule) object;

			if (schedule.getGroup().equals(groupName)) {
				l.add(schedule);
			}
		}
		return l;

	}
}
