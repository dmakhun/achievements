package com.softserve.edu.controller;

import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.manager.AchievementManager;
import com.softserve.edu.manager.AchievementTypeManager;
import com.softserve.edu.manager.CompetenceManager;
import com.softserve.edu.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AchievementController {

    private static final Logger logger = Logger
            .getLogger(AchievementController.class);

    @Autowired
    private AchievementManager achievementManager;
    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    private AchievementTypeManager achievementTypeManager;

    @RequestMapping(value = "/manager/user/award/{id}", method = RequestMethod.GET)
    public String awardConcreteUser(
            @PathVariable(value = "id") Long userId,
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            Model model) {
        try {
            List<AchievementType> achivList = achievementTypeManager
                    .achievementTypesList();

            model.addAttribute("achList", achivList);
            model.addAttribute("status", status);

            return "awardUser";
        } catch (Exception e) {
            logger.error(e);
            return Constants.GENERAL_ERROR;
        }
    }

    @RequestMapping(value = "/manager/user/award/{id}", method = RequestMethod.POST)
    public String awardConcreteUserPost(
            @RequestParam(value = "achievement_type_id") Long achievementTypeId,
            @RequestParam(value = "comment") String comment,
            @PathVariable(value = "id") Long userId, Model model) {
        try {
            List<AchievementType> achList = achievementTypeManager
                    .achievementTypesList();

            model.addAttribute("achList", achList);

            achievementManager.awardUser(userId, achievementTypeId, comment);
        } catch (Exception e) {
            logger.error(e);
            return Constants.GENERAL_ERROR;
        }

        return "redirect:/manager/user/award/" + userId + "?status=success";
    }

    @RequestMapping(value = "achievements")
    public String showAchievements(Model model) {
        try {
            List<Competence> competences = competenceManager
                    .findAllCompetences();

            List<AchievementType> achievementTypes = achievementTypeManager
                    .achievementTypesList();

            model.addAttribute("competences", competences);
            model.addAttribute("achievementTypes", achievementTypes);
            return "showAchievements";
        } catch (Exception e) {
            logger.error(e);
            return Constants.GENERAL_ERROR;
        }
    }

}
