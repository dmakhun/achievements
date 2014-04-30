package com.softserve.edu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.softserve.edu.entity.Schedule;

/**
 * Util class for parsing csv.
 * 
 * @author edgar.
 * 
 */
public class CsvParser {

	/**
	 * List of all Schedule from csv file.
	 * 
	 * @return List<Schedule>.
	 */
	public  List<Schedule> mapToCSV(File serverFile) {
		ColumnPositionMappingStrategy<Schedule> strat = new ColumnPositionMappingStrategy<Schedule>();
		strat.setType(Schedule.class);
		String[] columns = new String[] { "subject", "startDateStr",
				"startTime", "endDateStr", "endTime", "description", "location" };
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
