package com.finance.controllers;


import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.models.User;
import com.finance.services.CurrencyService;
import com.finance.services.IncomeService;
import com.finance.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Controller
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;


    @GetMapping("/income")
    public String getIncome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> categories = Arrays.asList("Sallary", "Gift", "Sell", "Other");

        User currUser = userService.findByUsername(authentication.getName());
        String defCur = currUser.getDefaultCurrency();
        List<String> currencies = currencyService.getListOfCurrencies(defCur);


        List<Income> incomeList = incomeService.getDataPerMonth(currUser.getId());
        incomeList = incomeService.getTenLastIncomesSortedReversed(incomeList);
        incomeList = incomeService.transformListFromEURtoCurrency(incomeList);

        Income income = new Income();

        model.addAttribute("incomeList", incomeList);
        model.addAttribute("categories", categories);
        model.addAttribute("currencies", currencies);
        model.addAttribute("income", income);
        return "income";
    }

    @PostMapping("/income/add")
    public String addNewIncome(@ModelAttribute Income income){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());

        if(!income.getCurrency().equals("eur")) {
            double exchangeCurrency = incomeService.getExchangeCurrency(income.getCurrency());
            income.setAmount(income.getAmount() / exchangeCurrency);
        }

        income.setUserId(user.getId());
        income.setDate(income.convertDate(income.getDate()));

        incomeService.save(income);
        return "redirect:/income";
    }



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
