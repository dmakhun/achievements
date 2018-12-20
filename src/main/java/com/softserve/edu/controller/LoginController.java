package com.softserve.edu.controller;

import static com.softserve.edu.util.Constants.ROLE_USER;

import com.softserve.edu.dao.GroupRepository;
import com.softserve.edu.dao.RoleRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.entity.User;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.CompetenceManager;
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
    private GroupRepository groupRepository;
    @Autowired
    private RoleRepository roleRepository;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(ModelMap model, Principal pr) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getAuthorities().toString().contains(ROLE_USER)) {
                return "redirect:/userHome";
            }
            List<Competence> competences = competenceManager.findAllCompetences();

            List<List<Group>> groupLists = new ArrayList<>();
            for (Competence competence : competences) {
                groupLists.add(groupRepository.findPendingByCompetenceId(competence.getId()));
            }

            model.addAttribute("competences", competences);
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

    @RequestMapping(value = "/logout")
    public String logout() {
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
            if (userManager.isEmailExists(user.getEmail())) {
                result.rejectValue("email", "DuplicateKey.user.email");
            }
            if (userManager.isUsernameExists(user.getUsername())) {
                result.rejectValue("username", "DuplicateKey.user.username");
            }
            if (result.hasErrors()) {
                return "registration";
            }

            user.setRole(roleRepository.findByName(ROLE_USER));

            userManager.createUser(user);

            String usernameSurname = user.getName() + " " + user.getSurname();
            model.addAttribute("usernameSurname", usernameSurname);
            model.addAttribute("username", user.getUsername());

            return "login";
        } catch (UserManagerException e) {
            logger.error("Can't create user", e);
            return "registration";
        } catch (Exception e) {
            logger.error("Can't create user", e);
            return GENERALERROR;
        }
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
