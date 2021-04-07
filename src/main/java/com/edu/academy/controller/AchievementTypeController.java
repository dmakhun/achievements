package com.edu.academy.controller;

import static com.edu.academy.util.Constants.GENERAL_ERROR;

import com.edu.academy.dao.AchievementTypeRepository;
import com.edu.academy.exception.AchievementTypeManagerException;
import com.edu.academy.manager.AchievementTypeManager;
import com.edu.academy.manager.CompetenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AchievementTypeController {

    private static final Logger logger = LoggerFactory.getLogger(AchievementTypeController.class);

    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    private AchievementTypeRepository achievementTypeRepository;
    @Autowired
    private AchievementTypeManager achievementTypeManager;

    @GetMapping(value = "/admin/achievementtype/allAchievementTypes")
    public String addAchievementTypeAll(
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            Model model) {
        try {
            model.addAttribute("achievementTypeList", achievementTypeRepository.findAll());
            model.addAttribute("status", status);
            return "allAchievementTypes";
        } catch (Exception e) {
            logger.error("Could not display all achievement types: ", e);
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/admin/achievementtype/add", method = RequestMethod.GET)
    public String addAchievementTypeAllCompetences(Model model) {
        try {
            model.addAttribute("competenceList", competenceManager.findAllCompetences());
            return "allCompetences";
        } catch (Exception e) {
            logger.error("Could not display all competences for achievement types: ", e);
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/admin/achievementtype/add/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String addAchievementType(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "points", required = false) String points,
            @PathVariable(value = "id") int competenceId) {
        try {
            achievementTypeManager.createAchievementType(name, points, competenceId);
            return "success";
        } catch (AchievementTypeManagerException e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }

    }

    @RequestMapping(value = "/admin/achievementtype/list/{id}", method = RequestMethod.GET)
    public String list(@PathVariable(value = "id") Long competenceId,
            Model model) {
        try {
            model.addAttribute("achievements", achievementTypeRepository
                    .findByCompetenceId(competenceId));
            model.addAttribute("competenceId", competenceId);
            return "achievementTypeList";
        } catch (Exception e) {
            logger.error("Could achievement types per competence: ", e);
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "admin/removeAchievementType/{id}", method = RequestMethod.GET)
    public String removeAchievementTypeById(@PathVariable(value = "id") Long achievementTypeId) {
        achievementTypeRepository.deleteById(achievementTypeId);
        return "redirect:/admin/achievementtype/allAchievementTypes?status=success";
    }

}
