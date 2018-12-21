package com.softserve.edu.controller;

import static com.softserve.edu.util.Constants.GENERAL_ERROR;

import com.softserve.edu.manager.ScheduleManager;
import com.softserve.edu.manager.ScheduleRowsManager;
import com.softserve.edu.manager.UserManager;
import java.io.File;
import java.util.Calendar;
import java.util.Map;
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

/**
 * Controller serves a request to schedule.
 */
@Controller
public class ScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private ScheduleRowsManager scheduleRowsManager;

    @RequestMapping(value = "/schedule/{group:[a-zA-Z0-9\\.\\-_]+}/{dateAdd}")
    public String schedule(@PathVariable("group") String group,
            @PathVariable("dateAdd") Integer dateAdd, Model model) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 7 * dateAdd);
            scheduleRowsManager.setCalendar(calendar);
            Map<Long, String> mapWeek = scheduleRowsManager.getWeekHead();
            Map<Long, String> map = scheduleManager.table(calendar, group.replace('_', ' '));

            model.addAttribute("group", group);
            model.addAttribute("mapWeek", mapWeek);
            model.addAttribute("map", map);
            model.addAttribute("next", dateAdd + 1);
            model.addAttribute("prev", dateAdd - 1);
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
            File serverFile = scheduleManager.saveFileOnServer(file);
            scheduleManager.fillDBfromCSV(serverFile);
            model.addAttribute("status", "file upload successfully");
            return "addSchedule";
        } catch (Exception e) {
            logger.error(e.getMessage());
            model.addAttribute("status", e.getMessage());
            return "addSchedule";
        }
    }

}
