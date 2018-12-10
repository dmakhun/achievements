package com.softserve.edu.controller;

import static java.util.stream.Collectors.toMap;

import com.softserve.edu.entity.Class;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.GroupManagerException;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.CompetenceManager;
import com.softserve.edu.manager.GroupManager;
import com.softserve.edu.manager.UserManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
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

    private static final String GENERAL_ERROR = "redirect:/myerror/10";
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private GroupManager groupManager;
    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/manager/groups", method = RequestMethod.GET)
    public String groups(
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            Model model) {
        try {
            // TODO pass just competence entity
            List<Competence> competenceList = competenceManager.findAllCompetences();
            Map<String, Set<Class>> groups = competenceList.stream()
                    .map(competence -> new AbstractMap.SimpleEntry<>(competence.getName(),
                            competence.getClasses())) // TODO filter just opened groups
                    .collect(toMap(AbstractMap.SimpleEntry::getKey,
                            AbstractMap.SimpleEntry::getValue));

            model.addAttribute("status", status);
            model.addAttribute("groups", groups);
            model.addAttribute("competences", competenceList);
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
                case "createAchievementType":
                    return createGroup(groupName, competenceId, dateStart, dateEnd,
                            locale);

                case "modify":
                    return modifyGroup(id, groupName, competenceId, dateStart,
                            dateEnd, locale);

                case "deleteAchievementType":
                    return deleteGroup(id);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<String> createGroup(String groupName,
            Long competenceId, String start, String end, Locale locale)
            throws ParseException {

        Long groupId = null;
        try {
            ResponseEntity<String> pass = groupChecks(groupName, start, end, locale);
            if (pass.getStatusCode() != HttpStatus.OK) {
                return pass;
            }

            Date starting = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            Date ending = new SimpleDateFormat("yyyy-MM-dd").parse(end);

            groupId = groupManager.create(groupName, starting, ending, competenceId);
        } catch (GroupManagerException e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(groupId.toString(), HttpStatus.OK);
    }

    private ResponseEntity<String> modifyGroup(Long groupId, String name,
            Long competence, String start, String end, Locale locale)
            throws ParseException {
        try {
            ResponseEntity<String> pass = groupChecks(name, start, end, locale);
            if (pass.getStatusCode() != HttpStatus.OK) {
                return pass;
            }
            Date starting = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            Date ending = new SimpleDateFormat("yyyy-MM-dd").parse(end);
            groupManager.modify(groupId, name, starting, ending, competence);
        } catch (GroupManagerException e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<String> deleteGroup(Long groupId) {
        try {
            groupManager.deleteById(groupId);
        } catch (GroupManagerException e) {
            logger.error(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<String> groupChecks(String name, String start,
            String end, Locale locale) {
        try {
            Date starting = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            Date ending = new SimpleDateFormat("yyyy-MM-dd").parse(end);

            if (name.isEmpty()) {
                return new ResponseEntity<>(messageSource.getMessage(
                        "groups.error.emptyname", null, locale),
                        HttpStatus.NOT_ACCEPTABLE);
            }

            if (starting.after(ending)) {
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
            List<User> userList = groupManager.users(groupId);
            model.addAttribute("users", userList);
            return "userlist";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/manager/attendees", method = RequestMethod.GET)
    public String attendees(Model model) {
        try {
            List<Competence> competenceList = competenceManager.findAllCompetences();
            Map<String, List<Class>> groups = new HashMap<>();
            for (Competence competence : competenceList) {
                groups.put(competence.getName(),
                        groupManager.findAllByCompetenceId(competence.getId(), true));
            }

            model.addAttribute("competences", competenceList);
            model.addAttribute("competence_groups", groups);

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

            List<User> managers = userManager.findAllManagers();

            model.addAttribute("userlist", managers);
            model.addAttribute("status", status);

            return "removeManager";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

}
