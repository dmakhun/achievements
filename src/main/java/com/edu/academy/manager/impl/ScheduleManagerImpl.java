package com.edu.academy.manager.impl;

import com.edu.academy.dao.GroupRepository;
import com.edu.academy.dao.ScheduleRepository;
import com.edu.academy.entity.Group;
import com.edu.academy.entity.Schedule;
import com.edu.academy.entity.ScheduleGroup;
import com.edu.academy.entity.ScheduleTable;
import com.edu.academy.exception.StorageException;
import com.edu.academy.manager.ScheduleManager;
import com.edu.academy.manager.ScheduleRowsManager;
import com.edu.academy.util.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("scheduleManager")
public class ScheduleManagerImpl implements ScheduleManager {

    private final Path rootLocation = Paths.get(System.getProperty("user.dir"), "CSV");
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ScheduleRowsManager scheduleRowsManager;

    @Override
    public Map<Long, String> table(Calendar calendar, String group) {
        Long placeOfText = 0L;
        Map<Long, String> resultMap = new LinkedHashMap<>();
        scheduleRowsManager.setCalendar(calendar);
        List<Calendar> week = scheduleRowsManager.getWeek();
        ScheduleGroup scheduleGroupDao = new ScheduleGroup(group);
        List<Schedule> listScheduleForGroup = scheduleGroupDao.listScheduleForGroup;
        for (Calendar c : week) {
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
    public File store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Files.createDirectories(rootLocation);
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return new File(rootLocation.resolve(filename).toUri());
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    @Transactional
    public void fillDBfromCSV(File file) throws Exception {
        int count = 1;
        try {
            List<Schedule> listSchedules = new CsvUtils().mapToCSV(file);
            Group generalGroup = null;
            for (Object object : listSchedules) {
                Schedule schedule = (Schedule) object;

                if (generalGroup != null
                        && schedule.getGroup().equals(generalGroup.getName())) {
                    ScheduleTable scheduleTable = new ScheduleTable(
                            generalGroup, schedule.getMeetType(),
                            schedule.getStartDateAndTime(),
                            schedule.getEndDateAndTime(),
                            schedule.getDescription(), schedule.getLocation());
                    scheduleRepository.save(scheduleTable);
                    count++;
                    continue;

                }

                Group group = groupRepository.findByName(schedule.getGroup());
                if (group == null) {
                    group = new Group();
                    group.setName(schedule.getGroup());
                    groupRepository.save(group);
                }
                generalGroup = group;
                ScheduleTable scheduleTable = new ScheduleTable(group,
                        schedule.getMeetType(), schedule.getStartDateAndTime(),
                        schedule.getEndDateAndTime(),
                        schedule.getDescription(), schedule.getLocation());
                scheduleRepository.save(scheduleTable);
                count++;
            }
        } catch (Exception e) {
            file.delete();
            throw new Exception("invalid value in column " + count);
        }

    }
}
