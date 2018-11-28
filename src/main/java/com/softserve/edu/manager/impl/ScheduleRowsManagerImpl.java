package com.softserve.edu.manager.impl;

import com.softserve.edu.manager.ScheduleRowsManager;

import java.util.*;

/**
 * Generates the current week.
 */
public class ScheduleRowsManagerImpl implements ScheduleRowsManager {

    private final int year;
    private final int month;
    private int day;
    /**
     * current date from constructor.
     */
    private Calendar calendar = Calendar.getInstance();

    /**
     * The default constructor.
     *
     * @param calendar
     */
    public ScheduleRowsManagerImpl(Calendar calendar) {
        this.calendar = calendar;
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Calendar findMonday() {
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            day = day - 1;
            calendar.set(year, month, day);
        }
        calendar.set(year, month, day, 8, 0);
        return calendar;
    }

    @Override
    public List<Calendar> getWeek() {
        List<Calendar> week = new ArrayList<>();
        Calendar c = this.findMonday();
        int hour;
        int m = c.get(Calendar.MONTH);
        int TempDay = c.get(Calendar.DAY_OF_MONTH);

        while (c.get(Calendar.DAY_OF_WEEK) < 7) {
            while (c.get(Calendar.HOUR_OF_DAY) <= 20) {
                hour = c.get(Calendar.HOUR_OF_DAY) + 1;
                Calendar tempCalendar = new GregorianCalendar();
                tempCalendar.set(year, m, TempDay, hour - 1, 0);
                week.add(tempCalendar);
                c.set(year, m, TempDay, hour, 0);
            }
            TempDay = TempDay + 1;
            c.set(year, m, TempDay, 8, 0);
        }
        return week;
    }

    @Override
    public Map<Long, String> getWeekHead() {
        Map<Long, String> workWeekMap = new LinkedHashMap<>();
        calendar = this.findMonday();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        for (long i = 1; i < 6; i++) {
            calendar.set(year, month, day);
            int tempMonth = calendar.get(Calendar.MONTH) + 1;
            workWeekMap.put(i, calendar.get(Calendar.DAY_OF_MONTH) + "."
                            + tempMonth + "."
                            + calendar.get(Calendar.YEAR));
            day = day + 1;
        }

        return workWeekMap;

    }

}
