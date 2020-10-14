package com.nihar.projects.coronavirustracker.controller;

import com.nihar.projects.coronavirustracker.model.LocationBean;
import com.nihar.projects.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*
This is not a rest controller
Rest controller     -> Json Resp from spring
Normal controller   -> Will give a whitelabel error page if we fail to map the response to an entity
=> name which points to a template
 */
@Controller
public class HomeController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    /*
    Will map to a <returnVar>.html file
     */
    @GetMapping(value = "/")
    public String home(Model model) {
        List<LocationBean> allStats = coronaVirusDataService.getAllStats();
        int totalCases = allStats.stream().mapToInt(e -> e.getTotalCases()).sum();
        model.addAttribute("modelAttrib", allStats);
        model.addAttribute("totalCases", totalCases);
        return "home";
    }


}
