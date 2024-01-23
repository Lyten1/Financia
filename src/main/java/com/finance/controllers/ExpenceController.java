package com.finance.controllers;


import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.services.ExpenceService;
import com.finance.services.IncomeService;
import com.finance.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/expence")
public class ExpenceController {

    @Autowired
    private ExpenceService expenceService;

    @Autowired
    private UserService userService;
    @GetMapping("/get")
    public String getPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long current_id =  userService.findByUsername(authentication.getName()).getId();
        List<Expence> lst = expenceService.getAll();

        List<Expence> currentUserExpences = new ArrayList<>();
        for ( Expence expence : lst){
            if(expence.getUserId() == current_id){
                currentUserExpences.add(expence);
            }
        }

        double sum = currentUserExpences.stream()
                .mapToDouble(Expence::getAmount).sum();
        String formSum = String.format("%.2f", sum);


        model.addAttribute("expence", formSum);
        return "index.html";
    }

    @GetMapping("/getToday")
    public String getDataPerDay(Model model){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        List<Expence> lst = expenceService.getAll();
        double sum = lst.stream()
                .filter(expence -> formattedDate.equals(expence.getDate()))
                .mapToDouble(Expence::getAmount).sum();
        String formSum = String.format("%.2f", sum);
        model.addAttribute("expence", formSum);
        return "index.html";
    }

    @GetMapping("/getFromDate/{request_data}")
    public String getDataFromDay(@PathVariable("request_data") String date, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate formattedDate = LocalDate.parse(date, formatter);
        List<Expence> lst = expenceService.getAll();
        double sum = lst.stream()
                .filter(expence -> expence.getDateByDate().isAfter(formattedDate))
                .mapToDouble(Expence::getAmount).sum();
        String formSum = String.format("%.2f", sum);
        model.addAttribute("expence", formSum);
        return "index.html";
    }




}
