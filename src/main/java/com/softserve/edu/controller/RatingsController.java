package com.softserve.edu.controller;

import static java.util.stream.Collectors.toMap;

import com.softserve.edu.dao.AchievementRepository;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.User;
import com.softserve.edu.manager.UserManager;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RatingsController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AchievementRepository achievementRepository;

    @GetMapping(value = "/manager/ratings")
    public String ratings(Model model) {
        // get points for users and sort by points DESC
        Map<User, Long> userPointsMap = userRepository.findAll().stream()
                .map(user -> new AbstractMap.SimpleEntry<>(user, userManager.getTotalPoints(user)))
                .sorted(Map.Entry.<User, Long>comparingByValue().reversed())
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        model.addAttribute("mapS", userPointsMap);
        return "ratings";
    }
}