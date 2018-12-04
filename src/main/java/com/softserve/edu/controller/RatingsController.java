package com.softserve.edu.controller;

import static java.util.stream.Collectors.toMap;

import com.softserve.edu.entity.User;
import com.softserve.edu.manager.UserManager;
import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RatingsController {

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/manager/ratings", method = RequestMethod.GET)
    public String ratings(Model model) {
        List<User> users = userManager.findAllUsers();
        // get points for users and sort by points DESC
        Map<User, Long> userPointsMap = users.stream()
                .map(user -> new AbstractMap.SimpleEntry<>(user, userManager.getTotalPoints(user)))
                .sorted(Map.Entry.<User, Long>comparingByValue().reversed())
                .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        model.addAttribute("mapS", userPointsMap);
        return "ratings";
    }
}