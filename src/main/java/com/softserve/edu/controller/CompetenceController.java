package com.softserve.edu.controller;

import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.dao.GroupRepository;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.CompetenceManager;
import com.softserve.edu.manager.GroupManager;
import com.softserve.edu.manager.UserManager;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompetenceController {

    private static final String GENERALERROR = "redirect:/myerror/10";
    private static final Logger logger = LoggerFactory
            .getLogger(CompetenceController.class);

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private GroupManager groupManager;

    @RequestMapping(value = "/competence", method = RequestMethod.GET)
    public String groups(Model model) {
        try {
            List<Competence> competences = competenceManager.findAllCompetences();
            model.addAttribute("competences", competences);
            return "showCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/addCompetence", method = RequestMethod.GET)
    public String addCompetence(
            @RequestParam(value = "status", defaultValue = "", required = false) String status,
            Model model) {
        try {
            model.addAttribute("allCompetences", competenceManager.findAllCompetences());
            model.addAttribute("status", status);
            return "addCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @PostMapping(value = "/addCompetence", params = {"addCompetence"})
    public String addCompetence(
            @RequestParam(value = "competenceName", required = false, defaultValue = "") String name) {
        competenceRepository.save(new Competence(name, LocalDate.now()));
        return "redirect:/admin/competenceAll?statusAdd=success";
    }

    @RequestMapping(value = "/addManagerList", method = RequestMethod.POST)
    public String addManager(
            @RequestParam(value = "userlist", required = false, defaultValue = "") Long userId,
            @RequestParam(value = "rolelist", required = false, defaultValue = "") Long roleId) {
        try {
            userManager.updateUser(userId, null, null, null, null, null, roleId);
            return "redirect:/competencies/addManager?status=success";
        } catch (UserManagerException e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/manager/competence", method = RequestMethod.GET)
    public String getGroups(Model model) {
        try {
            List<Competence> allCompetences = competenceManager.findAllCompetences();
            model.addAttribute("allCompetences", allCompetences);
            return "groupsAndCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/manager/competence", method = RequestMethod.POST)
    public String getGroupsPost(
            @RequestParam(value = "competence") Long competenceId, Model model) {
        try {
            List<Competence> allCompetences = competenceManager.findAllCompetences();
            List<Group> openedGroups = groupRepository.findOpenedByCompetenceId(competenceId);
            model.addAttribute("allCompetences", allCompetences);
            model.addAttribute("openedGroups", openedGroups);
            return "groupsAndCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/admin/competenceAll", method = RequestMethod.GET)
    public String getCompetencies(
            Model model,
            @RequestParam(value = "statusRemove", required = false, defaultValue = "") String statusRemove,
            @RequestParam(value = "statusAdd", required = false, defaultValue = "") String statusAdd) {
        try {
            List<Competence> competences = competenceManager.findAllCompetences();
            model.addAttribute("statusRemove", statusRemove);
            model.addAttribute("statusAdd", statusAdd);
            model.addAttribute("competencelist", competences);
            return "forDeleteOrAddCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }

    }

    @GetMapping(value = "/admin/removeCompetence/{id}")
    public String removeCompetenceById(@PathVariable(value = "id") Long competenceId) {
        competenceRepository.deleteById(competenceId);
        return "redirect:/admin/competenceAll?statusRemove=success";
    }
}
