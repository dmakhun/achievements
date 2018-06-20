package com.softserve.edu.controller;

import com.softserve.edu.entity.Competence;
import com.softserve.edu.entity.Group;
import com.softserve.edu.exception.CompetenceManagerException;
import com.softserve.edu.exception.UserManagerException;
import com.softserve.edu.manager.CompetenceManager;
import com.softserve.edu.manager.GroupManager;
import com.softserve.edu.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CompetenceController {

    private static final String GENERALERROR = "redirect:/myerror/10";
    private static final Logger logger = LoggerFactory
            .getLogger(CompetenceController.class);

    @Autowired
    CompetenceManager competenceManager;
    @Autowired
    UserManager userManager;
    @Autowired
    GroupManager groupManager;


    @RequestMapping(value = "/competence", method = RequestMethod.GET)
    public String groups(Model model) {
        try {
            List<Competence> competences = competenceManager
                    .findAllCompetences();
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
            List<Competence> competencelist = competenceManager
                    .findAllCompetences();

            model.addAttribute("competencelist", competencelist);
            model.addAttribute("status", status);

            return "addCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/addCompetence", method = RequestMethod.POST, params = {"addCompetence"})
    public String addCompetence(
            Model model,
            @RequestParam(value = "competenceName", required = false, defaultValue = "") String name) {
        try {
            competenceManager.create(name);
            return "redirect:/admin/competenceAll?statusAdd=success";
        } catch (CompetenceManagerException e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    public String addCompetencePost(
            Model model,
            @RequestParam(value = "competenceName", required = false, defaultValue = "") String name) {
        try {
            competenceManager.create(name);

            return "redirect:/addCompetence?status=success";
        } catch (CompetenceManagerException e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/removeCompetence", method = RequestMethod.GET)
    public String removeCompetence(
            @RequestParam(value = "status", defaultValue = "", required = false) String status,
            Model model) {
        try {
            List<Competence> competencelist = competenceManager
                    .findAllCompetences();
            model.addAttribute("competencelist", competencelist);
            model.addAttribute("status", status);

            return GENERALERROR;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/removeCompetence", method = RequestMethod.POST)
    public String removeCompetencePost(
            Model model,
            @RequestParam(value = "competence", required = false, defaultValue = "") Long cid) {
        try {
            competenceManager.delete(cid);
            return "redirect:/competencies/removeCompetence?status=success";
        } catch (CompetenceManagerException e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/addManagerList", method = RequestMethod.POST)
    public String addManager(
            @RequestParam(value = "userList", required = false, defaultValue = "") Long userId,
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
            List<Competence> list = competenceManager.findAllCompetences();
            model.addAttribute("list", list);
            return "groupsAndCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/manager/competence", method = RequestMethod.POST)
    public String getGroupsPost(
            @RequestParam(value = "competence") Long competerceId, Model model) {
        try {
            List<Competence> list = competenceManager.findAllCompetences();
            model.addAttribute("list", list);
            List<Group> listGroups = groupManager.findByCompetence(
                    competerceId, true);
            model.addAttribute("list_groups", listGroups);

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
            List<Competence> competencelist = competenceManager
                    .findAllCompetences();

            model.addAttribute("statusRemove", statusRemove);
            model.addAttribute("statusAdd", statusAdd);
            model.addAttribute("competencelist", competencelist);

            return "forDeleteOrAddCompetence";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }

    }

    @RequestMapping(value = "/admin/removeCompetence/{id}", method = RequestMethod.GET)
    public String removeCompetenceById(
            @PathVariable(value = "id") Long competenceId) {
        try {
            competenceManager.delete(competenceId);
            return "redirect:/admin/competenceAll?statusRemove=success";
        } catch (CompetenceManagerException e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }
}
