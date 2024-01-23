package com.finance.controllers;

import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.services.ExpenceService;
import com.finance.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stat")
public class StatController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenceService expenceService;
    @GetMapping("/getAll")
    public String getAllStatistic(Model model){
        double totalIncome = incomeService.getAll().stream()
                .mapToDouble(Income::getAmount).sum();
        double totalExpence = expenceService.getAll().stream()
                .mapToDouble(Expence::getAmount).sum();

        model.addAttribute("total_income", totalIncome);
        model.addAttribute("total_expence", totalExpence);

        return "statis";
    }

}
