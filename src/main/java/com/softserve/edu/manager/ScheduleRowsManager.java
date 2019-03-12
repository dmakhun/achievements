package com.softserve.edu.manager;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Generates the current week.
 */
public interface ScheduleRowsManager {

    /**
     * Find near last Monday.
     *
     * @return Calendar.
     */
    Calendar findMonday();

    /**
     * Generates the current week.
     *
     * @return List<Calendar>.
     */
    List<Calendar> getWeek();

    void setCalendar(Calendar calendar);

    Map<Long, String> getWorkWeekDates(int weekNumber);
}
