package com.finance.controllers;

import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.services.ExpenceService;
import com.finance.services.IncomeService;
import com.finance.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stat")
public class StatController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenceService expenceService;

    @Autowired
    private UserService userService;



    @GetMapping("/week")
    public String getWeekStatistic(Model model){

        // this week income & expence
        // 10 last transactions


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long current_id =  userService.findByUsername(authentication.getName()).getId();

        List<Income> listIncome = incomeService.getDataPerWeek(current_id);
        List<Expence> listExpence = expenceService.getDataPerWeek(current_id);


        double totalIncome = listIncome.stream().mapToDouble(income -> income.getAmount()).sum();
        double totalExpence = listExpence.stream().mapToDouble(expence -> expence.getAmount()).sum();

        model.addAttribute("total_income", String.format("%.2f", totalIncome));
        model.addAttribute("total_expence",String.format("%.2f", totalExpence));


        List<Income> sortedIncomeList = listIncome.stream()
                .sorted(Comparator.comparing(Income::getDateByDate).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<Expence> sortedExpenceList = listExpence.stream()
                .sorted(Comparator.comparing(Expence::getDateByDate).reversed())
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("incomeList", sortedIncomeList);
        model.addAttribute("expenceList", sortedExpenceList);


        return "statis";
    }

    @GetMapping("/month")
    public String getDefaultStatistic(Model model){

        // this month income & expence
        // 10 last transactions


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long current_id =  userService.findByUsername(authentication.getName()).getId();

        List<Income> listIncome = incomeService.getDataPerMonth(current_id);
        List<Expence> listExpence = expenceService.getDataPerMonth(current_id);


        double totalIncome = listIncome.stream().mapToDouble(income -> income.getAmount()).sum();
        double totalExpence = listExpence.stream().mapToDouble(expence -> expence.getAmount()).sum();

        model.addAttribute("total_income", String.format("%.2f", totalIncome));
        model.addAttribute("total_expence",String.format("%.2f", totalExpence));


        List<Income> sortedIncomeList = listIncome.stream()
                .sorted(Comparator.comparing(Income::getDateByDate).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<Expence> sortedExpenceList = listExpence.stream()
                .sorted(Comparator.comparing(Expence::getDateByDate).reversed())
                .limit(10)
                .collect(Collectors.toList());

        model.addAttribute("incomeList", sortedIncomeList);
        model.addAttribute("expenceList", sortedExpenceList);


        return "statis";
    }

}
