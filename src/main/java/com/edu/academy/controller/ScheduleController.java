package com.edu.academy.controller;

import com.edu.academy.manager.ScheduleManager;
import com.edu.academy.manager.ScheduleRowsManager;
import com.edu.academy.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import static com.edu.academy.util.Constants.GENERAL_ERROR;

@Controller
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private ScheduleRowsManager scheduleRowsManager;

    @RequestMapping(value = "/schedule/{group:[a-zA-Z0-9\\.\\-_]+}/{weekNumber}")
    public String schedule(@PathVariable("group") String group,
                           @PathVariable("weekNumber") Integer weekNumber, Model model) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 7 * weekNumber);
            scheduleRowsManager.setCalendar(calendar);
            Map<Long, String> workWeekDates = scheduleRowsManager.getWorkWeekDates(weekNumber);
            Map<Long, String> map = scheduleManager.table(calendar, group.replace('_', ' '));

            model.addAttribute("group", group);
            model.addAttribute("workWeekDates", workWeekDates);
            model.addAttribute("map", map);
            model.addAttribute("next", weekNumber + 1);
            model.addAttribute("prev", weekNumber - 1);
            return "schedule";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/scheduleTable", method = RequestMethod.GET)
    public String scheduleTable(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute("set", userManager.findOpenedGroupNames(auth.getName()));
            return "scheduleTable";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/addSchedule", method = RequestMethod.GET)
    public String addSchedule(Model model) {
        return "addSchedule";
    }

    @RequestMapping(value = "/addSchedule", method = RequestMethod.POST)
    String uploadFileHandler(@RequestParam("file") MultipartFile file,
                             Model model) throws IllegalStateException {

        try {
            File serverFile = scheduleManager.store(file);
            scheduleManager.fillDBfromCSV(serverFile);
            model.addAttribute("status", "file uploaded successfully");
            return "addSchedule";
        } catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("status", e.getMessage());
            return "addSchedule";
        }
    }

}
