package com.semicolok.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security Hello World");
        model.addObject("message", "This is welcome page!");
        model.setViewName("hello");
        return model;
    }
    
    @RequestMapping(value = "/login/form", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
 
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(ModelMap map) {
        map.put("title", "Spring Security Hello World");
        map.put("message", "This is protected page - Admin Page!");
        return "admin";
    }
 
    @RequestMapping(value = "/dba", method = RequestMethod.GET)
    public String dbaPage(ModelMap map) {
        map.put("title", "Spring Security Hello World");
        map.put("message", "This is protected page - Database Page!");
        return "admin";
    }
}
