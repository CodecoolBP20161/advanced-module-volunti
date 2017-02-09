package com.codecool.volunti.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/")
    public String getRoute(){
        return "index";
    }

    // for tests, but route and return value legit for the final product
    @GetMapping("/org/{id}/opp")
    public String display(HttpServletRequest req){
        // equals find all
        List<Opportunity> list = new ArrayList<>();

        Opportunity x = new Opportunity();
        Opportunity y = new Opportunity();
        list.add(x);
        list.add(y);

        y.title = "other desc";
        req.setAttribute("opps", list);
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
