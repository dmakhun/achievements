package com.softserve.edu.util;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.softserve.edu.entity.Schedule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvParser {

    private static final Logger logger = LoggerFactory.getLogger(CsvParser.class);

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
            logger.debug("CSV File not found", e);
        }

        return new CsvToBeanBuilder(reader).withMappingStrategy(mappingStrategy)
                .withType(Schedule.class)
                .build().parse();

    }
}
