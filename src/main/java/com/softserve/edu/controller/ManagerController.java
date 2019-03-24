package com.softserve.edu.controller;

import static com.softserve.edu.util.Constants.GENERAL_ERROR;
import static com.softserve.edu.util.Constants.ROLE_MANAGER;

import com.softserve.edu.dao.AchievementRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.dao.GroupRepository;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.GroupManager;
import com.softserve.edu.manager.UserManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupManager groupManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private AchievementRepository achievementRepository;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/manager/groups", method = RequestMethod.GET)
    public String groups(Model model) {
        try {
            model.addAttribute("competences", competenceRepository.findWithOpenedGroups());
            return "groups";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/manager/groups/manage", method = RequestMethod.POST)
    public ResponseEntity<String> groupManagement(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "group_name") String groupName,
            @RequestParam(value = "competence") Long competenceId,
            @RequestParam(value = "dateStart") String dateStart,
            @RequestParam(value = "dateEnd") String dateEnd, Locale locale) {
        try {
            switch (type.toLowerCase()) {
                case "create":
                    return createGroup(groupName, competenceId, dateStart, dateEnd, locale);
                case "modify":
                    return modifyGroup(id, groupName, competenceId, dateStart, dateEnd, locale);
                case "delete":
                    return deleteGroup(id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<String> createGroup(String groupName,
            Long competenceId, String startDate, String endDate, Locale locale) {

        Long groupId = null;
        try {
            ResponseEntity<String> pass = groupChecks(groupName, startDate, endDate, locale);
            if (pass.getStatusCode() != HttpStatus.OK) {
                return pass;
            }

            LocalDate dateStart = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
            groupId = groupManager.create(groupName, dateStart, dateEnd, competenceId);
        } catch (GroupManagerException e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(groupId.toString(), HttpStatus.OK);
    }

    private ResponseEntity<String> modifyGroup(Long groupId, String name,
            Long competence, String start, String end, Locale locale) {
        try {
            ResponseEntity<String> pass = groupChecks(name, start, end, locale);
            if (pass.getStatusCode() != HttpStatus.OK) {
                return pass;
            }
            LocalDate dateStart = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
            LocalDate dateEnd = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);
            groupManager.modify(groupId, name, dateStart, dateEnd, competence);
        } catch (GroupManagerException e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<String> deleteGroup(Long groupId) {
        groupRepository.deleteById(groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<String> groupChecks(String name, String startDate,
            String endDate, Locale locale) {
        try {
            LocalDate dateStart = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

            if (name.isEmpty()) {
                return new ResponseEntity<>(messageSource.getMessage(
                        "groups.error.emptyname", null, locale),
                        HttpStatus.NOT_ACCEPTABLE);
            }

            if (dateStart.isAfter(dateEnd)) {
                return new ResponseEntity<>(messageSource.getMessage(
                        "groups.error.inversed", null, locale),
                        HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(messageSource.getMessage(
                "groups.error.datepattern", null, locale),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/manager/group/{id}", method = RequestMethod.GET)
    public String concreteGroup(@PathVariable(value = "id") Long groupId,
            Model model) {
        try {
            List<User> groupAttendees = userRepository.findByGroups_Id(groupId);
            model.addAttribute("users", groupAttendees);
            return "userlist";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/manager/attendees", method = RequestMethod.GET)
    public String attendees(Model model) {
        try {
            model.addAttribute("competences", competenceRepository.findWithOpenedGroups());
            return "attendees";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/manager/attendees", method = RequestMethod.POST)
    public String attendUser(@RequestParam(value = "user_id") Long userId,
            @RequestParam(value = "group_id") Long groupId,
            @RequestParam(value = "competence_id") Long competenceId) {
        try {
            groupManager.addUser(userId, groupId);
            userManager.removeUserToCompetence(userId, competenceId);
        } catch (GroupManagerException | UserManagerException e) {
            logger.error(e.getMessage());
        }

        return "redirect:/manager/attendees";
    }

    @RequestMapping(value = "/admin/removeManager", method = RequestMethod.GET)
    public String removeManager(
            @RequestParam(value = "status", defaultValue = "", required = false) String status,
            Model model) {
        try {

            List<User> managers = userRepository.findByRoleName(ROLE_MANAGER);
            model.addAttribute("userlist", managers);
            model.addAttribute("status", status);
            return "removeManager";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/manager/ratings", method = RequestMethod.GET)
    public String ratings(Model model) {
        List<User> users = userRepository.findAllUsers();
        users.forEach(user -> user
                .setPoints(achievementRepository.findTotalAchievementPointsByUserId(user.getId())
                        .orElse(0L)));
        users = users.stream().sorted(Comparator.comparing(User::getPoints).reversed())
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "ratings";
    }

}
