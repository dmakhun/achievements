package com.softserve.edu.controller;

import static com.softserve.edu.util.Constants.GENERAL_ERROR;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.AchievementTypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AchievementTypeController {

    private static final Logger logger = LoggerFactory.getLogger(AchievementTypeController.class);

    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private AchievementTypeRepository achievementTypeRepository;
    @Autowired
    private AchievementTypeManager achievementTypeManager;

    @RequestMapping(value = "/admin/achievementtype/allAchievements", method = RequestMethod.GET)
    public String allAchievementTypes(Model model) {
        try {
            model.addAttribute("allAchievementTypes", achievementTypeRepository.findAll());
            return "allAchievementTypes";
        } catch (Exception e) {
            logger.error("Can't display all achievement types: ", e);
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/admin/achievementtype/add", method = RequestMethod.GET)
    public String allCompetences(Model model) {
        try {
            model.addAttribute("allCompetences", competenceRepository.findAll());
            return "allCompetences";
        } catch (Exception e) {
            logger.error("Can't display all competences: ", e);
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
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "admin/removeAchievementType/{id}", method = RequestMethod.GET)
    public String removeAchievementTypeById(@PathVariable(value = "id") Long achievementTypeId) {
        achievementTypeRepository.deleteById(achievementTypeId);
        return "redirect:/admin/achievementtype/allAchievements?status=success";
    }

}
