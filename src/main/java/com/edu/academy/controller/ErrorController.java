package com.edu.academy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ErrorController {

    @RequestMapping(value = "/myerror/{errorCode}", method = RequestMethod.GET)
    public String myError(@PathVariable(value = "errorCode") Integer errorCode,
                          ModelMap model) {

        switch (errorCode) {
            case 400:
                model.addAttribute("title", "400");
                model.addAttribute("description", "Bad Request");
                break;
            case 401:
                model.addAttribute("title", "401");
                model.addAttribute("description", "Unauthorized");
                break;
            case 403:
                model.addAttribute("title", "403");
                model.addAttribute("Forbidden");
                break;
            case 404:
                model.addAttribute("title", "404");
                model.addAttribute("description", "Not Found");
                break;
            case 405:
                model.addAttribute("title", "405");
                model.addAttribute("description", "");
                break;
            case 500:
                model.addAttribute("title", "500");
                model.addAttribute("description", "Internal Server Error");
                return "login";
            case 503:
                model.addAttribute("title", "503");
                model.addAttribute("description", " Out of Resources");
                break;
            case 10:
                model.addAttribute("title", "500");
                model.addAttribute("description", "Controller <br>Full stack trace IN LOG");
                break;

            default:
                model.addAttribute("title", "1");
                model.addAttribute("description", "Throwable");
        }

        return "errorPage";
    }

}
