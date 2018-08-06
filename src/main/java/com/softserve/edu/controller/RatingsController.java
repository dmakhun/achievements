package com.softserve.edu.controller;

import com.softserve.edu.entity.User;
import com.softserve.edu.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class RatingsController {

    @Autowired
    private UserManager userManager;

    private static Map<User, Long> sortByComparator(Map<User, Long> unsortedMap) {

        List<Object> list = new LinkedList<>(unsortedMap.entrySet());

        // sort list based on comparator
        list.sort(Collections.reverseOrder((Comparator) (o1, o2) -> ((Comparable) ((Map.Entry) (o1)).getValue())
                .compareTo(((Map.Entry) (o2)).getValue())));

        // put sorted list into map again
        //LinkedHashMap make sure order in which keys were inserted
        Map sortedMap = new LinkedHashMap();
        for (Object aList : list) {
            Map.Entry entry = (Map.Entry) aList;
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    @RequestMapping(value = "/manager/ratings", method = RequestMethod.GET)
    public String ratings(Model model) {
        List<User> users = userManager.findAllUsers();
        Map<User, Long> mapS = new HashMap<>();

        for (User user : users) {
            mapS.put(user, userManager.sumOfPoints(user));
        }

        mapS = sortByComparator(mapS);

        model.addAttribute("mapS", mapS);
        return "ratings";
    }
}