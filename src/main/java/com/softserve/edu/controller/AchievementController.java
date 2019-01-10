package com.softserve.edu.controller;

import static com.softserve.edu.util.Constants.GENERAL_ERROR;

import com.softserve.edu.dao.AchievementTypeRepository;
import com.softserve.edu.dao.CompetenceRepository;
import com.softserve.edu.manager.AchievementManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AchievementController {

    private static final Logger logger = LoggerFactory.getLogger(AchievementController.class);

    @Autowired
    private AchievementManager achievementManager;
    @Autowired
    private AchievementTypeRepository achievementTypeRepository;
    @Autowired
    private CompetenceRepository competenceRepository;

    @GetMapping(value = "/manager/user/award/{id}")
    public String awardConcreteUser(
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            Model model) {
        try {
            model.addAttribute("achList", achievementTypeRepository.findAll());
            model.addAttribute("status", status);
            return "awardUser";
        } catch (Exception e) {
            logger.error("Can't award a user");
            return GENERAL_ERROR;
        }
    }

    @PostMapping(value = "/manager/user/award/{id}")
    public String awardConcreteUserPost(
            @RequestParam(value = "achievement_type_id") Long achievementTypeId,
            @RequestParam(value = "comment") String comment,
            @PathVariable(value = "id") Long userId, Model model) {
        try {
            achievementManager.awardUser(userId, achievementTypeId, comment);
            model.addAttribute("achList", achievementTypeRepository.findAll());
        } catch (Exception e) {
            logger.error("Can't award a user");
            return GENERAL_ERROR;
        }

        return "redirect:/manager/user/award/" + userId + "?status=success";
    }

    @RequestMapping(value = "achievements")
    public String showAchievements(Model model) {
        try {
            model.addAttribute("competences", competenceRepository.findAll());
            model.addAttribute("achievementTypes", achievementTypeRepository.findAll());
            return "showAchievements";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERAL_ERROR;
        }
    }

}
