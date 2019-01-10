package com.softserve.edu.controller;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.AchievementTypeManager;
import com.softserve.edu.manager.CompetenceManager;
import java.util.List;
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

    private static final String GENERALERROR = "redirect:/myerror/10";
    private static final Logger logger = LoggerFactory.getLogger(AchievementTypeController.class);

    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    private AchievementTypeRepository achievementTypeRepository;
    @Autowired
    private AchievementTypeManager achievementTypeManager;

    @GetMapping(value = "/admin/achievementtype/allAchievements")
    public String addAchievementTypeAll(
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            Model model) {
        try {
            model.addAttribute("achievementTypeList", achievementTypeRepository.findAll());
            model.addAttribute("status", status);
            return "allAchievements";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/admin/achievementtype/add", method = RequestMethod.GET)
    public String addAchievementTypeAllCompetences(Model model) {
        try {
            model.addAttribute("competenceList", competenceManager.findAllCompetences());
            return "allCompetences";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "/admin/achievementtype/add/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String addAchievementTypePost(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "points", required = false) String points,
            @PathVariable(value = "id") int competenceId) {
        try {
            achievementTypeManager
                    .createAchievementType(name, Integer.parseInt(points), competenceId);
            return "success";
        } catch (AchievementTypeManagerException e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }

    }

    @RequestMapping(value = "/admin/achievementtype/list/{id}", method = RequestMethod.GET)
    public String list(@PathVariable(value = "id") Long competenceId,
            Model model) {
        try {
            List<AchievementType> achievements = achievementTypeRepository
                    .findByCompetenceId(competenceId);

            model.addAttribute("achievements", achievements);
            model.addAttribute("competenceId", competenceId);

            return "achievementTypeList";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @GetMapping(value = "admin/removeAchievementType/{id}")
    public String removeAchievementTypeById(@PathVariable(value = "id") Long achievementTypeId) {
        achievementTypeRepository.deleteById(achievementTypeId);
        return "redirect:/admin/achievementtype/allAchievements?status=success";
    }

}
