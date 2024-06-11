package com.finance.controllers;

import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.services.*;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.router.Route;
import elemental.html.DivElement;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.http.HttpServletResponse;
import org.jsoup.helper.HttpConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.round;


@Controller
@RequestMapping("/stat")
public class StatController {

    @Autowired
    private StatService statService;


    @GetMapping("/day")
    public String getDayStatistic(Model model){


//        // this day income & expence
//        // 10 last transactions

        String period = "day";
        Long currentUser_id = statService.getUserId();
        String defaultCurrency = statService.getDefaultCurrency(currentUser_id);
        double exchangeCurrency = statService.getExchangeCurrency(defaultCurrency);
        List<Income> incomeList = statService.getListIncomesPerPeriod(period, currentUser_id, exchangeCurrency);
        List<Expence> expenceList = statService.getListExpencesPerPeriod(period, currentUser_id, exchangeCurrency);
        double totalIncome = statService.getTotalIncome(incomeList);
        double totalExpence = statService.getTotalExpence(expenceList);
        List<Income> sortIncomeList = statService.getSortedListOfTenLatestIncomes(incomeList);
        List<Expence> sortExpenceList = statService.getSortedListOfTenLatestExpences(expenceList);

        model.addAttribute("chartDataInc", statService.getChartDataIncome(incomeList));
        model.addAttribute("chartDataExp", statService.getChartDataExpence(expenceList));


        model.addAttribute("set", period);
        model.addAttribute("defCurrency", defaultCurrency.toUpperCase());
        model.addAttribute("total_income", String.format("%.2f", totalIncome));
        model.addAttribute("total_expence", String.format("%.2f", totalExpence));
        model.addAttribute("incomeList", sortIncomeList);
        model.addAttribute("expenceList", sortExpenceList);

        return "statis";
    }


    @GetMapping("/week")
    public String getWeekStatistic(Model model){

//        // this week income & expence
//        // 10 last transactions

        String period = "week";
        Long currentUser_id = statService.getUserId();
        String defaultCurrency = statService.getDefaultCurrency(currentUser_id);
        double exchangeCurrency = statService.getExchangeCurrency(defaultCurrency);
        List<Income> incomeList = statService.getListIncomesPerPeriod(period, currentUser_id,exchangeCurrency);
        List<Expence> expenceList = statService.getListExpencesPerPeriod(period, currentUser_id, exchangeCurrency);
        double totalIncome = statService.getTotalIncome(incomeList);
        double totalExpence = statService.getTotalExpence(expenceList);
        List<Income> sortIncomeList = statService.getSortedListOfTenLatestIncomes(incomeList);
        List<Expence> sortExpenceList = statService.getSortedListOfTenLatestExpences(expenceList);

        model.addAttribute("chartDataInc", statService.getChartDataIncome(incomeList));
        model.addAttribute("chartDataExp", statService.getChartDataExpence(expenceList));


        model.addAttribute("set", period);
        model.addAttribute("defCurrency", defaultCurrency.toUpperCase());
        model.addAttribute("total_income", String.format("%.2f", totalIncome));
        model.addAttribute("total_expence", String.format("%.2f", totalExpence));
        model.addAttribute("incomeList", sortIncomeList);
        model.addAttribute("expenceList", sortExpenceList);

        return "statis";
    }

    @GetMapping("/month")
    public String getDefaultStatistic(Model model){
        // this month income & expence
        // 10 last transactions

        String period = "month";
        Long currentUser_id = statService.getUserId();
        String defaultCurrency = statService.getDefaultCurrency(currentUser_id);
        double exchangeCurrency = statService.getExchangeCurrency(defaultCurrency);
        List<Income> incomeList = statService.getListIncomesPerPeriod(period, currentUser_id, exchangeCurrency);
        List<Expence> expenceList = statService.getListExpencesPerPeriod(period, currentUser_id, exchangeCurrency);
        double totalIncome = statService.getTotalIncome(incomeList);
        double totalExpence = statService.getTotalExpence(expenceList);
        List<Income> sortIncomeList = statService.getSortedListOfTenLatestIncomes(incomeList);
        List<Expence> sortExpenceList = statService.getSortedListOfTenLatestExpences(expenceList);




        model.addAttribute("chartDataInc", statService.getChartDataIncome(incomeList));
        model.addAttribute("chartDataExp", statService.getChartDataExpence(expenceList));

        model.addAttribute("set", period);
        model.addAttribute("defCurrency", defaultCurrency.toUpperCase());
        model.addAttribute("total_income", String.format("%.2f", totalIncome));
        model.addAttribute("total_expence", String.format("%.2f", totalExpence));
        model.addAttribute("incomeList", sortIncomeList);
        model.addAttribute("expenceList", sortExpenceList);


        return "statis";
    }





    @GetMapping("/year")
    public String getYearStatistic(Model model){

//        // this month income & expence
//        // 10 last transactions

        String period = "year";
        Long currentUser_id = statService.getUserId();
        String defaultCurrency = statService.getDefaultCurrency(currentUser_id);
        double exchangeCurrency = statService.getExchangeCurrency(defaultCurrency);
        List<Income> incomeList = statService.getListIncomesPerPeriod(period, currentUser_id, exchangeCurrency);
        List<Expence> expenceList = statService.getListExpencesPerPeriod(period, currentUser_id, exchangeCurrency);
        double totalIncome = statService.getTotalIncome(incomeList);
        double totalExpence = statService.getTotalExpence(expenceList);
        List<Income> sortIncomeList = statService.getSortedListOfTenLatestIncomes(incomeList);
        List<Expence> sortExpenceList = statService.getSortedListOfTenLatestExpences(expenceList);

        model.addAttribute("chartDataInc", statService.getChartDataIncome(incomeList));
        model.addAttribute("chartDataExp", statService.getChartDataExpence(expenceList));


        model.addAttribute("set", period);
        model.addAttribute("defCurrency", defaultCurrency.toUpperCase());
        model.addAttribute("total_income", String.format("%.2f", totalIncome));
        model.addAttribute("total_expence", String.format("%.2f", totalExpence));
        model.addAttribute("incomeList", sortIncomeList);
        model.addAttribute("expenceList", sortExpenceList);
        return "statis";
    }





}
