package com.softserve.edu.util;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.softserve.edu.entity.Schedule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
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
        ColumnPositionMappingStrategy<Schedule> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(Schedule.class);
        String[] columns = new String[]{"subject", "startDateStr",
                "startTime", "endDateStr", "endTime", "description", "location"};
        mappingStrategy.setColumnMapping(columns);
        Reader reader = null;
        try {
            reader = new FileReader(serverFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new CsvToBeanBuilder(reader).withMappingStrategy(mappingStrategy)
                .withType(Schedule.class)
                .build().parse();

    }
}
