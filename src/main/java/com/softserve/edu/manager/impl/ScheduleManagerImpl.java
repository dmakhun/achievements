package com.softserve.edu.manager.impl;

import com.softserve.edu.dao.ClassDao;
import com.softserve.edu.dao.ScheduleDao;
import com.softserve.edu.dao.impl.ScheduleGroupDaoImpl;
import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Schedule;
import com.softserve.edu.entity.ScheduleTable;
import com.softserve.edu.manager.ScheduleManager;
import com.softserve.edu.manager.ScheduleRowsManager;
import com.softserve.edu.util.CsvUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("scheduleManager")
public class ScheduleManagerImpl implements ScheduleManager {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ClassDao classDao;

    @Override
    public Map<Long, String> table(Calendar calendar, String group) {
        Long placeOfText = 0L;
        Map<Long, String> resultMap = new LinkedHashMap<>();
        ScheduleRowsManager scheduleRowsManager = new ScheduleRowsManagerImpl(calendar);
        List<Calendar> calen = scheduleRowsManager.getWeek();
        ScheduleGroupDaoImpl scheduleGroupDao = new ScheduleGroupDaoImpl(group);
        List<Schedule> listScheduleForGroup = scheduleGroupDao.listScheduleForGroup;
        for (Calendar c : calen) {
            ++placeOfText;
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR_OF_DAY);

            for (Schedule schedule : listScheduleForGroup) {
                if (schedule.getStartDateAndTime().get(Calendar.MONTH) == month
                        && schedule.getStartDateAndTime().get(Calendar.DAY_OF_MONTH) == day
                        && schedule.getStartDateAndTime().get(Calendar.HOUR_OF_DAY) == hour) {

                    resultMap.put(placeOfText,
                            schedule.getMeetType() + " " + schedule.getLocation());
                    Calendar calendarTemp = schedule.getStartDateAndTime();
                    calendarTemp.add(Calendar.HOUR_OF_DAY, 1);

                    Long temp = placeOfText;
                    while (calendarTemp.before(schedule.getEndDateAndTime())) {
                        resultMap
                                .put(++temp, schedule.getMeetType() + " " + schedule.getLocation());
                        calendarTemp.add(Calendar.HOUR_OF_DAY, 1);
                    }

                }

            }
        }
        return resultMap;
    }

    @Override
    public File saveFileOnServer(MultipartFile file) throws Exception {
        File serverFile = null;
        if (file.isEmpty()) {
            throw new Exception("File is empty");
        }
        byte[] bytes = file.getBytes();
        // Creating the directory to store file
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "tmpFiles");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Random random = new Random();
        int i = random.nextInt();
        if (i < 0) {
            i = i * (-1);
        }
        File finalFile = new File(rootPath + File.separator + "CSV_FILE_NAME"
                + File.separator + i + ".csv");
        while (finalFile.exists()) {
            i = random.nextInt();
            if (i < 0) {
                i = i * (-1);
            }
        }

        serverFile = new File(dir.getAbsolutePath() + File.separator + i
                + ".csv");
        BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        if (!isUnique(serverFile)) {
            throw new Exception("This file was added before");
        }
        try {
            FileUtils.copyFile(serverFile, finalFile);
        } finally {
            serverFile.delete();
        }
        return finalFile;
    }

    private boolean isUnique(File file) throws IOException {
        String rootPath = System.getProperty("catalina.home");
        File[] files = new File(rootPath + File.separator + "CSV_FILE_NAME").listFiles();

        InputStream input1 = new FileInputStream(file);
        InputStream input2 = null;

        for (File f : files) {
            input2 = new FileInputStream(f);

            if (IOUtils.contentEquals(input1, input2)) {
                return false;
            }
        }

        return true;

    }

    @Override
    @Transactional
    public void fillDBfromCSV(File file) throws Exception {
        int count = 1;
        try {
            List<Schedule> listSchedules = new CsvUtils().mapToCSV(file);
            Class generalClass = null;
            for (Object object : listSchedules) {
                Schedule schedule = (Schedule) object;

                if (generalClass != null
                        && schedule.getGroup().equals(generalClass.getName())) {
                    ScheduleTable scheduleTable = new ScheduleTable(
                            generalClass, schedule.getMeetType(),
                            schedule.getStartDateAndTime(),
                            schedule.getEndDateAndTime(),
                            schedule.getDescription(), schedule.getLocation());
                    scheduleDao.save(scheduleTable);
                    count++;
                    continue;

                }

                Class aClass = classDao.findGroupByName(schedule.getGroup());
                if (aClass == null) {
                    aClass = new Class();
                    aClass.setName(schedule.getGroup());
                    classDao.save(aClass);

                }
                generalClass = aClass;
                ScheduleTable scheduleTable = new ScheduleTable(aClass,
                        schedule.getMeetType(), schedule.getStartDateAndTime(),
                        schedule.getEndDateAndTime(),
                        schedule.getDescription(), schedule.getLocation());
                scheduleDao.save(scheduleTable);
                count++;
            }
        } catch (Exception e) {
            file.delete();
            throw new Exception("invalid value in column " + count);
        }
    }
}
