package com.edu.academy.manager;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
public class ScheduleRowsManagerTest {

    @Autowired
    private ScheduleRowsManager scheduleRowsManager;

    @Test
    public void findMonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.MARCH, 20);
        scheduleRowsManager.setCalendar(calendar);
        Calendar calendarTrue = Calendar.getInstance();
        calendarTrue.set(2014, Calendar.MARCH, 17, 8, 0);
        calendar = scheduleRowsManager.findMonday();
        assertTrue(calendarTrue.get(Calendar.MONTH) == calendar
                .get(Calendar.MONTH)
                && calendarTrue.get(Calendar.DAY_OF_MONTH) == calendar
                .get(Calendar.DAY_OF_MONTH)
                && calendarTrue.get(Calendar.YEAR) == calendar
                .get(Calendar.YEAR));
    }
}
