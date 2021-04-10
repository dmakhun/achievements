package com.edu.academy.manager;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

public interface ScheduleManager {

    /**
     * Fill the table for a week lessons existing.
     *
     * @param calendar .
     * @param group    .
     * @return Map.
     */
    Map<Long, String> table(Calendar calendar, String group);

    File store(MultipartFile file);

    void fillDBfromCSV(File file) throws Exception;
}
