package com.codecool.volunti.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Controller
public class MainController {

    @GetMapping("/")
    public String getRoute(){
        return "index";
    }

    // for tests
    @GetMapping("/org/{id}/opp")
    public String display(HttpServletRequest req){
        Opportunity x = new Opportunity();
        Opportunity y = new Opportunity();
        y.title = "other desc";
        req.setAttribute("opps", x);
        req.setAttribute("opps", y);
        return "org_opportunities";
    }

    // for tests
    private class Opportunity {
        public String title = "Test Title";
        public String desc = "test description";
        public int reqs = 4;
        public Date from = new Date();
        public Date to = new Date();

        public Opportunity(){

        }
    }

}
