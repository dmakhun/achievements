package com.softserve.edu.manager.impl;

import com.softserve.edu.manager.ScheduleRowsManager;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Generates the current week.
 */
@Service("scheduleRowsManager")
public class ScheduleRowsManagerImpl implements ScheduleRowsManager {

    private int year;
    private int month;
    private int day;

    private Calendar calendar = Calendar.getInstance();

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
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
        Calendar c = findMonday();
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
    public Map<Long, String> getWorkWeek(int weekNumber) {
        LocalDate today = LocalDate.now().plusWeeks(weekNumber);
        DayOfWeek[] week = DayOfWeek.values();
        Map<Long, String> workWeek = new LinkedHashMap<>();

        for (long i = 0; i <= 5; i++) {
            workWeek.put(i, today.with(TemporalAdjusters.previousOrSame(week[(int) i])).format(
                    DateTimeFormatter.ISO_LOCAL_DATE));
        }
        return workWeek;
    }
}
