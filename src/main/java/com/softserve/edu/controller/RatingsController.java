package com.softserve.edu.controller;

import com.softserve.edu.entity.User;
import com.softserve.edu.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RatingsController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/manager/ratings", method = RequestMethod.GET)
    public String ratings(Model model) {
        List<User> users = userManager.findAllUsers();
        Map<User, Long> userPoints = new HashMap<>();

        for (User user : users) {
            userPoints.put(user, userManager.sumOfPoints(user));
        }

        userPoints = userPoints.entrySet().stream()
                .sorted(Map.Entry.<User, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        model.addAttribute("mapS", userPoints);
        return "ratings";
    }
}