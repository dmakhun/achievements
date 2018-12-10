package com.softserve.edu.util;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import com.softserve.edu.entity.Schedule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvUtils {

    private static final Logger logger = LoggerFactory.getLogger(CsvUtils.class);
    private static final String CSV_FILE_NAME = "/lvivitaschedule_last.csv";

    public static List<Schedule> mapToCSV() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(CSV_FILE_NAME);
        return getBuilderForFile(new File(url.getFile()), getMappingStrategy());
    }

    public List<Schedule> mapToCSV(File serverFile) {
        return getBuilderForFile(serverFile, getMappingStrategy());
    }

    private static List<Schedule> getBuilderForFile(File serverFile,
            ColumnPositionMappingStrategy<Schedule> mappingStrategy) {
        Reader reader = null;
        try {
            reader = new FileReader(serverFile);
        } catch (FileNotFoundException e) {
            logger.debug("CSV File not found ", e);
        }
        return new CsvToBeanBuilder(reader).withMappingStrategy(mappingStrategy)
                .withType(Schedule.class)
                .build().parse();
    }

    private static ColumnPositionMappingStrategy<Schedule> getMappingStrategy() {
        ColumnPositionMappingStrategy<Schedule> mappingStrategy = new ColumnPositionMappingStrategy<>();
        mappingStrategy.setType(Schedule.class);
        String[] columns = new String[]{"subject", "startDateStr",
                "startTime", "endDateStr", "endTime", "description", "location"};
        mappingStrategy.setColumnMapping(columns);
        return mappingStrategy;
    }
}
