package com.softserve.edu.util;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import com.softserve.edu.entity.Schedule;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

/**
 * Util class for parsing csv.
 *
 * @author edgar.
 */
public class ScheduleMapping {

    /**
     * Single instance of ScheduleMapping.
     */
    private static ScheduleMapping instance;

    /**
     * The default constructor.
     */
    private ScheduleMapping() {
    }

    /**
     * Single instance of ScheduleMapping.
     *
     * @return ScheduleMapping.
     */
    public static ScheduleMapping getInstance() {
        if (instance == null) {
            instance = new ScheduleMapping();
        }
        return instance;
    }

    /**
     * List of all Schedule from csv file.
     *
     * @return List<Schedule>.
     */
    public static List<Schedule> mapToCSV() {
        ColumnPositionMappingStrategy<Schedule> strat = new ColumnPositionMappingStrategy<Schedule>();
        strat.setType(Schedule.class);
        String[] columns = new String[]{"subject", "startDateStr",
                "startTime", "endDateStr", "endTime", "description", "location"};
        strat.setColumnMapping(columns);
        URL url = Thread.currentThread().getContextClassLoader()
                .getResource("/lvivitaschedule_last.csv");
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(url.getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new CsvToBean<Schedule>().parse(strat, csvReader);
    }
}
