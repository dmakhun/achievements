package com.edu.academy.controller;

import com.edu.academy.dao.CompetenceRepository;
import com.edu.academy.dao.GroupRepository;
import com.edu.academy.entity.Competence;
import com.edu.academy.exception.UserManagerException;
import com.edu.academy.manager.CompetenceManager;
import com.edu.academy.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.edu.academy.util.Constants.GENERAL_ERROR;

@Controller
public class CompetenceController {

    private static final Logger logger = LoggerFactory.getLogger(CompetenceController.class);

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/competence", method = RequestMethod.GET)
    public String allCompetences(Model model) {
        model.addAttribute("competences", competenceManager.findAllCompetences());
        return "showCompetence";
    }

    @RequestMapping(value = "/competence", method = RequestMethod.POST)
    public String allCompetencesPost(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("competences", competenceManager.findAllCompetences());
        return "showCompetence";
    }

    @RequestMapping(value = "/addCompetence", method = RequestMethod.GET)
    public String addCompetence(
            @RequestParam(value = "status", defaultValue = "", required = false) String status,
            Model model) {
        model.addAttribute("allCompetences", competenceManager.findAllCompetences());
        model.addAttribute("status", status);
        return "addCompetence";
    }

    @RequestMapping(value = "/addCompetence", params = {
            "addCompetence"}, method = RequestMethod.POST)
    public String addCompetence(
            @RequestParam(value = "competenceName", required = false, defaultValue = "") String name) {
        competenceRepository.save(new Competence(name, LocalDate.now()));
        return "redirect:/admin/competenceAll?statusAdd=success";
    }

    @RequestMapping(value = "/addManagerList", method = RequestMethod.POST)
    public String addManager(
            @RequestParam(value = "userId", required = false, defaultValue = "") Long userId,
            @RequestParam(value = "roleId", required = false, defaultValue = "") Long roleId) {
        try {
            userManager.updateUser(userId, null, null, null, null, null, roleId);
            return "redirect:/competencies/addManager?status=success";
        } catch (UserManagerException e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/manager/competence", method = RequestMethod.GET)
    public String allCompetencesGroups(Model model) {
        model.addAttribute("allCompetences", competenceManager.findAllCompetences());
        return "groupsAndCompetence";
    }

    @RequestMapping(value = "/manager/competence", method = RequestMethod.POST)
    public String allCompetencesGroups(
            @RequestParam(value = "competence") Long competenceId, Model model) {
        model.addAttribute("allCompetences", competenceManager.findAllCompetences());
        model.addAttribute("openedGroups",
                groupRepository.findOpenedByCompetenceId(competenceId));
        return "groupsAndCompetence";
    }

    @RequestMapping(value = "/admin/competenceAll", method = RequestMethod.GET)
    public String getCompetencies(
            Model model,
            @RequestParam(value = "statusRemove", required = false, defaultValue = "") String statusRemove,
            @RequestParam(value = "statusAdd", required = false, defaultValue = "") String statusAdd) {
        model.addAttribute("allCompetences", competenceManager.findAllCompetences());
        model.addAttribute("statusAdd", statusAdd);
        model.addAttribute("statusRemove", statusRemove);
        return "forDeleteOrAddCompetence";
    }

    @GetMapping(value = "/admin/removeCompetence/{id}")
    public String removeCompetenceById(@PathVariable(value = "id") Long competenceId) {
        competenceRepository.deleteById(competenceId);
        return "redirect:/admin/competenceAll?statusRemove=success";
    }
}
