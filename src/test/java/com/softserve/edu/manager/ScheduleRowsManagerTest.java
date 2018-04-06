package com.softserve.edu.manager;

import com.softserve.edu.manager.impl.ScheduleRowsManagerImplementation;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertTrue;

public class ScheduleRowsManagerTest {

    @Test
    public void findMonday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.MARCH, 20);
        ScheduleRowsManagerImplementation srm = new ScheduleRowsManagerImplementation(calendar);
        Calendar calendarTrue = Calendar.getInstance();
        calendarTrue.set(2014, Calendar.MARCH, 17, 8, 0);
        calendar = srm.findMonday();
        assertTrue(calendarTrue.get(Calendar.MONTH) == calendar
                .get(Calendar.MONTH)
                && calendarTrue.get(Calendar.DAY_OF_MONTH) == calendar
                .get(Calendar.DAY_OF_MONTH)
                && calendarTrue.get(Calendar.YEAR) == calendar
                .get(Calendar.YEAR));
    }
}
