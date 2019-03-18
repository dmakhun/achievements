package com.softserve.edu.controller;

import com.softserve.edu.dao.AchievementRepository;
import com.softserve.edu.dao.UserRepository;
import com.softserve.edu.entity.User;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RatingsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AchievementRepository achievementRepository;

    @GetMapping(value = "/manager/ratings")
    public String ratings(Model model) {
        List<User> users = userRepository.findAllUsers();
        users.forEach(user -> user
                .setPoints(achievementRepository.findTotalAchievementPointsByUserId(user.getId())
                        .orElse(0L)));
        users = users.stream().sorted(Comparator.comparing(User::getPoints).reversed())
                .collect(Collectors.toList());
        model.addAttribute("users", users);
        return "ratings";
    }

}