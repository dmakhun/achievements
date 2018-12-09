package com.softserve.edu.controller;

import com.softserve.edu.dao.AchievementTypeDao;
import com.softserve.edu.dao.CompetenceDao;
import com.softserve.edu.entity.AchievementType;
import com.softserve.edu.entity.Competence;
import com.softserve.edu.exception.AchievementTypeManagerException;
import com.softserve.edu.manager.AchievementTypeManager;
import com.softserve.edu.manager.CompetenceManager;
import java.util.List;
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

    private static final String GENERALERROR = "redirect:/myerror/10";
    private static final Logger logger = LoggerFactory
            .getLogger(AchievementTypeController.class);

    @Autowired
    private CompetenceManager competenceManager;
    @Autowired
    CompetenceDao competenceDao;
    @Autowired
    private AchievementTypeDao achievementTypeDao;
    @Autowired
    private AchievementTypeManager achievementTypeManager;

    @RequestMapping(value = "/admin/achievementtype/allAchievements", method = RequestMethod.GET)
    public String addAchievementTypeAll(
            @RequestParam(value = "status", required = false, defaultValue = "") String status,
            @RequestParam(value = "name", required = false) String name,
            Model model) {
        try {
            Iterable<AchievementType> achievementTypeList = achievementTypeManager
                    .achievementTypesList();

            model.addAttribute("achievementTypeList", achievementTypeList);
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
            List<Competence> competenceList = competenceManager
                    .findAllCompetences();

            model.addAttribute("competenceList", competenceList);

            return "AllCompetencies";
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
            @PathVariable(value = "id") int competenceId, Model model) {
        try {
            int achPoints = Integer.parseInt(points);
            achievementTypeManager.createAchievementType(name, achPoints, competenceId);
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
            List<AchievementType> achievements = achievementTypeDao
                    .findByCompetenceId(competenceId);

            model.addAttribute("achievements", achievements);
            model.addAttribute("competenceId", competenceId);

            return "achievementTypeList";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

    @RequestMapping(value = "admin/removeAchievementType/{id}", method = RequestMethod.GET)
    public String removeAchievementTypeById(
            @PathVariable(value = "id") Long achievementTypeId) {

        try {
            achievementTypeManager.deleteAchievementType(achievementTypeId);

            return "redirect:/admin/achievementtype/allAchievements?status=success";
        } catch (AchievementTypeManagerException e) {
            logger.error(e.getMessage());
            return GENERALERROR;
        }
    }

}
