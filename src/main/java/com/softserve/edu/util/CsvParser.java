package com.softserve.edu.util;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.softserve.edu.entity.Schedule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * Util class for parsing csv.
 */
public class CsvParser {

    /**
     * List of all Schedule from csv file.
     *
     * @return List<Schedule>.
     */
    public List<Schedule> mapToCSV(File serverFile) {
        ColumnPositionMappingStrategy<Schedule> strat = new ColumnPositionMappingStrategy<Schedule>();
        strat.setType(Schedule.class);
        String[] columns = new String[]{"subject", "startDateStr",
                "startTime", "endDateStr", "endTime", "description", "location"};
        strat.setColumnMapping(columns);
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(serverFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new CsvToBean<Schedule>().parse(strat, csvReader);
    }
}
