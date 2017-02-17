//package com.codecool.volunti.controller;
//
//
//import com.codecool.volunti.model.Opportunity;
//import com.codecool.volunti.repository.OpportunityRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import javax.validation.Valid;
//
//@Controller
//public class OpportunityController {
//
////    @Autowired
//    private OpportunityRepository repository;
//
////    @Autowired
////    public void setRepository(OpportunityRepository repository) {
////        this.repository = repository;
////    }
//
//    @GetMapping("/org/{id}/opp/form")
//    public String form(Model model){
////        Opportunity opportunity = new Opportunity("title", 10, "aa", "BB", 10, 10, new Date(), new Date(),"aa", "aa", null);
////        model.addAttribute("opportunity", opportunity);
//        return "multi-form";
//    }
//
//    @PostMapping("/opp")
//    public String saveOpportunity(@Valid Opportunity opportunity){
//        System.out.println("opportunity = " + opportunity);
//        return "index";
//    }
//}
