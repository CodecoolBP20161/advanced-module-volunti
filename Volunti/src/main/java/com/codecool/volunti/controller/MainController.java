package com.codecool.volunti.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/org/{id}/opp/form")
    public String form(Model model){
        Opportunity opportunity = new Opportunity();
        model.addAttribute("opportunity", opportunity);
        return "multi-form";
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

        y.title = "other title";
        req.setAttribute("opps", list);
        return "org_opportunities";
    }

    // for tests
    private class Opportunity {
        public int id = 0;
        public String title = null;
        public String accommodationType = "tent";
        public String foodType = "halal";
        public int hoursExpected = 100;
        public String hoursExpectedType = "hrs";
        public int minimumStayInDays = 2;
        public Date availabilityFrom = new Date();
        public Date dateAvailabilityTo = new Date();
        public String costs = "százezer";
        public String requirements = "szükségletek";
        public int numberOfVolunteers = 23423;

        public Opportunity(){
            this.id++;
        }


//        Id not null, int, serial, primary key
//        title String
//        number_of_volunteers int
//        accomodation_type String
//        food_type String
//        hours_expected  int

    }

}
