package com.softserve.edu.manager;

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

    /**
     * @param file
     * @return
     * @throws Exception
     */
    File saveFileOnServer(MultipartFile file) throws Exception;

    /**
     * @param file
     * @throws Exception
     */
    void fillDBfromCSV(File file) throws Exception;
}
