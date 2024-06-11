package com.finance.controllers;


import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.models.User;
import com.finance.services.CurrencyService;
import com.finance.services.ExpenceService;
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


@Controller
public class ExpenceController {

    @Autowired
    private ExpenceService expenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/expence")
    public String getExpence(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> categories = Arrays.asList("Food", "Travel", "Home", "Party", "Needs", "Other");

        User currUser = userService.findByUsername(authentication.getName());
        String defCur = currUser.getDefaultCurrency();
        List<String> currencies = currencyService.getListOfCurrencies(defCur);


        List<Expence> expenceList = expenceService.getDataPerMonth(currUser.getId());
        expenceList = expenceService.getTenLastExpencesSortedReversed(expenceList);
        expenceList = expenceService.transformListFromEURtoCurrency(expenceList);


        Expence expence = new Expence();

        model.addAttribute("expenceList", expenceList);
        model.addAttribute("categories", categories);
        model.addAttribute("currencies", currencies);
        model.addAttribute("expence", expence);
        return "expence";
    }

    @PostMapping("/expence/add")
    public String addNewExpence(@ModelAttribute Expence expence){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        String defCur = user.getDefaultCurrency();

        if(!expence.getCurrency().equals("eur")) {
            double exchangeCurrency = expenceService.getExchangeCurrency(expence.getCurrency());
            expence.setAmount(expence.getAmount() / exchangeCurrency);
        }

        expence.setUserId(user.getId());
        expence.setDate(expence.convertDate(expence.getDate()));
        expenceService.save(expence);
        return "redirect:/expence";
    }






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
        model.addAttribute("List", currentUserExpences);
        return "index.html";
    }


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
