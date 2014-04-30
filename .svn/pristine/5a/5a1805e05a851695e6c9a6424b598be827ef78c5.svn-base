package com.softserve.edu.manager;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author edgar
 * 
 */
public interface ScheduleManager {

	/**
	 * Fill the table for a week lessons existing.
	 * 
	 * @param calendar
	 *            .
	 * @param group
	 *            .
	 * @return Map.
	 */
	public Map<Long, String> table(Calendar calendar, String group);

	/**
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public File saveFileOnServer(MultipartFile file) throws Exception;

	/**
	 * @param file
	 * @throws Exception
	 */
	public void fillDBfromCSV(File file) throws Exception;
}
