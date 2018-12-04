package com.softserve.edu.controller;

import com.softserve.edu.dao.UserDao;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.CompetenceManager;
import com.softserve.edu.manager.GroupManager;
import com.softserve.edu.manager.RoleManager;
import com.softserve.edu.manager.UserManager;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

    private static final String GENERALERROR = "redirect:/myerror/10";
    private static final Logger logger = LoggerFactory
            .getLogger(LoginController.class);

    @Autowired
    private UserManager userManager;
    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    private GroupManager groupManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private UserDao userDao;

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(ModelMap model, Principal pr) {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (auth.getAuthorities().toString().contains("ROLE_USER")) {
                return "redirect:/userHome";
            }
            List<Competence> competences = competenceManager
                    .findAllCompetences();
            model.addAttribute("competences", competences);

            List<List<Group>> groupLists = new ArrayList<>();
            for (Competence competence : competences) {
                groupLists.add(groupManager.inFuture(competence.getId()));
            }
            model.addAttribute("groups_lists", groupLists);

            return "index";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/login")
    public String signIn(
            @RequestParam(value = "error", required = false, defaultValue = "") String error,
            Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/createaccount", method = RequestMethod.POST)
    public String createAccount(@Valid User user, BindingResult result, Model model) {
        try {
            if (userManager.existEmail(user.getEmail())) {
                result.rejectValue("email", "DuplicateKey.user.email");
            }
            if (userManager.existUserName(user.getUsername())) {
                result.rejectValue("username", "DuplicateKey.user.username");
            }
            if (result.hasErrors()) {
                return "registration";
            }

            Long id = roleManager.findRoleByName("ROLE_USER").getId();
            user.setRole(roleManager.findById(id));

            userManager.createUser(user);

            String usernameSurname = user.getName() + " " + user.getSurname();
            model.addAttribute("usernameSurname", usernameSurname);
            model.addAttribute("username", user.getUsername());

            return "login";
        } catch (UserManagerException e) {
            logger.error(e.getMessage());
            return "registration";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }

    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "login";
    }

    @RequestMapping(value = "/sheduleTable", method = RequestMethod.GET)
    public String sheduleTable() {
        return "sheduleTable";
    }

    @RequestMapping(value = "recovery")
    public String passwordRecovery() {
        return "recovery";
    }

}
