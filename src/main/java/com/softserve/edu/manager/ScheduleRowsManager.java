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

    /**
     * Determines the value of the head of the week.
     *
     * @return Map<Long ,   String>.
     */
    Map<Byte, String> getWeekHead();

    void setCalendar(Calendar calendar);
}
