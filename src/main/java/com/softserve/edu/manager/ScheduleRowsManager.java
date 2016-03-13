package com.softserve.edu.manager;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Generates the current week.
 *
 * @author edgar.
 */
public interface ScheduleRowsManager {

    /**
     * Find near last Monday.
     *
     * @return Calendar.
     */
    public Calendar findMonday();

    /**
     * Generates the current week.
     *
     * @return List<Calendar>.
     */
    public List<Calendar> getWeek();

    /**
     * Determines the value of the head of the week.
     *
     * @return Map<Long, String>.
     */
    public Map<Long, String> getWeekHead();
}
