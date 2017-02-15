package com.codecool.volunti.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
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

    @PostMapping("/opp")
    public String saveOpportunity(Opportunity opportunity){
        System.out.println("opportunity = " + opportunity.title);
        return "redirect:/";
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
        public List<String> skills = Arrays.asList("english", "italian", "herbish", "russian");

        public Opportunity(){
            this.id++;
        }

        public Opportunity(String title, String accommodationType, String foodType, int hoursExpected, int minimumStayInDays, Date availabilityFrom, Date dateAvailabilityTo, String costs, String requirements, int numberOfVolunteers, List<String> skills) {
            this.title = title;
            this.accommodationType = accommodationType;
            this.foodType = foodType;
            this.hoursExpected = hoursExpected;
            this.minimumStayInDays = minimumStayInDays;
            this.availabilityFrom = availabilityFrom;
            this.dateAvailabilityTo = dateAvailabilityTo;
            this.costs = costs;
            this.requirements = requirements;
            this.numberOfVolunteers = numberOfVolunteers;
            this.skills = skills;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAccommodationType() {
            return accommodationType;
        }

        public void setAccommodationType(String accommodationType) {
            this.accommodationType = accommodationType;
        }

        public String getFoodType() {
            return foodType;
        }

        public void setFoodType(String foodType) {
            this.foodType = foodType;
        }

        public int getHoursExpected() {
            return hoursExpected;
        }

        public void setHoursExpected(int hoursExpected) {
            this.hoursExpected = hoursExpected;
        }

        public String getHoursExpectedType() {
            return hoursExpectedType;
        }

        public void setHoursExpectedType(String hoursExpectedType) {
            this.hoursExpectedType = hoursExpectedType;
        }

        public int getMinimumStayInDays() {
            return minimumStayInDays;
        }

        public void setMinimumStayInDays(int minimumStayInDays) {
            this.minimumStayInDays = minimumStayInDays;
        }

        public Date getAvailabilityFrom() {
            return availabilityFrom;
        }

        public void setAvailabilityFrom(Date availabilityFrom) {
            this.availabilityFrom = availabilityFrom;
        }

        public Date getDateAvailabilityTo() {
            return dateAvailabilityTo;
        }

        public void setDateAvailabilityTo(Date dateAvailabilityTo) {
            this.dateAvailabilityTo = dateAvailabilityTo;
        }

        public String getCosts() {
            return costs;
        }

        public void setCosts(String costs) {
            this.costs = costs;
        }

        public String getRequirements() {
            return requirements;
        }

        public void setRequirements(String requirements) {
            this.requirements = requirements;
        }

        public int getNumberOfVolunteers() {
            return numberOfVolunteers;
        }

        public void setNumberOfVolunteers(int numberOfVolunteers) {
            this.numberOfVolunteers = numberOfVolunteers;
        }

        public List<String> getSkills() {
            return skills;
        }

        public void setSkills(List<String> skills) {
            this.skills = skills;
        }

        //        Id not null, int, serial, primary key
//        title String
//        number_of_volunteers int
//        accomodation_type String
//        food_type String
//        hours_expected  int

    }

}
