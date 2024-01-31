package com.finance.controllers;


import com.finance.models.Expence;
import com.finance.models.Income;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;


    @GetMapping("/get")
    public String getPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long current_id =  userService.findByUsername(authentication.getName()).getId();
        List<Income> lst = incomeService.getAll();

        List<Income> currentUserIncomes = new ArrayList<>();
        for ( Income income : lst){
            if(income.getUserId() == current_id){
                currentUserIncomes.add(income);
            }
        }

        double sum = currentUserIncomes.stream()
                .mapToDouble(Income::getAmount).sum();
        String formSum = String.format("%.2f", sum);

        model.addAttribute("income", formSum);

        model.addAttribute("List", currentUserIncomes);
        return "index.html";
    }

    @GetMapping("/getToday")
    public String getDataPerDay(Model model){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        List<Income> lst = incomeService.getAll();
        double sum = lst.stream()
                .filter(income -> formattedDate.equals(income.getDate()))
                .mapToDouble(Income::getAmount).sum();
        String formSum = String.format("%.2f", sum);
        model.addAttribute("income", formSum);
        return "index.html";
    }

    @GetMapping("/getFromDate/{request_data}")
    public String getDataFromDay(@PathVariable("request_data") String date, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate formattedDate = LocalDate.parse(date, formatter);
        List<Income> lst = incomeService.getAll();
        double sum = lst.stream()
                .filter(income -> income.getDateByDate().isAfter(formattedDate))
                .mapToDouble(Income::getAmount).sum();
        String formSum = String.format("%.2f", sum);
        model.addAttribute("income", formSum);
        return "index.html";
    }







}
