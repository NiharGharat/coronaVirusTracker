package com.nihar.projects.coronavirustracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
This is not a rest controller
Rest controller     -> Json Resp from spring
Normal controller   -> Will give a whitelabel error page if we fail to map the response to an entity
=> name which points to a template
 */
@Controller
public class HomeController {

    /*
    Will map to a <returnVar>.html file
     */
    @GetMapping(value = "/")
    public String home() {
        return "home";
    }


}
