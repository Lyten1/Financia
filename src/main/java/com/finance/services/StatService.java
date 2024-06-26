package com.finance.services;

import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.repos.ExpenceRepo;
import com.finance.repos.IncomeRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatService {



    // Services
    @Autowired
    private UserService userService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenceService expenceService;


    public Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  userService.findByUsername(authentication.getName()).getId();
    }

    public String getDefaultCurrency(Long id){
        return userService.getById(id).getDefaultCurrency();
    }

    public double getExchangeCurrency(String defaultCurrency){
        if(!defaultCurrency.equals("eur"))
            return currencyService.getExchangeCurrency(defaultCurrency);
        else
            return 1.0;
    }

    public List<Income> getListIncomesPerPeriod(String period, Long currentUser_id, double exchangeCurrency){
        List<Income> incomeList = new ArrayList<>();
        switch (period) {
            case "day" -> incomeList = incomeService.getDataPerDay(currentUser_id);
            case "week" -> incomeList = incomeService.getDataPerWeek(currentUser_id);
            case "month" -> incomeList = incomeService.getDataPerMonth(currentUser_id);
            case "year" -> incomeList = incomeService.getDataPerYear(currentUser_id);
            default -> {
                System.out.println("Invalid parameters! Ending...");
                return null;
            }
        }
        return incomeService.transformListToDefaultCurrency(incomeList, exchangeCurrency);
    }

    public List<Expence> getListExpencesPerPeriod(String period, Long currentUser_id, double exchangeCurrency){
        List<Expence> expenceList = new ArrayList<>();
        switch (period) {
            case "day" -> expenceList = expenceService.getDataPerDay(currentUser_id);
            case "week" -> expenceList = expenceService.getDataPerWeek(currentUser_id);
            case "month" -> expenceList = expenceService.getDataPerMonth(currentUser_id);
            case "year" -> expenceList = expenceService.getDataPerYear(currentUser_id);
            default -> {
                System.out.println("Invalid parameters! Ending...");
                return null;
            }
        }
        return expenceService.transformListToDefaultCurrency(expenceList, exchangeCurrency);
    }

    public double getTotalIncome(List<Income> incomeList){
        return incomeList.stream().mapToDouble(income -> {
            return currencyService.rouderToDec2ForExchanges(income.getAmount());
        }).sum() ;
    }

    public double getTotalExpence(List<Expence> expenceList){
        return expenceList.stream().mapToDouble(expence -> {
            return currencyService.rouderToDec2ForExchanges(expence.getAmount());
        }).sum() ;
    }


    public List<Income> getSortedListOfTenLatestIncomes(List<Income> incomeList){
       return incomeService.getTenLastIncomesSortedReversed(incomeList);
    }


    public List<Expence> getSortedListOfTenLatestExpences(List<Expence> expenceList){
        return expenceService.getTenLastExpencesSortedReversed(expenceList);
    }

    public List<List<Object>> getChartDataIncome(List<Income> incomeList) {

        Map<String, Double> map = new HashMap<>();

        for (Income income: incomeList){
            String currentCategory = income.getCategory();
            if(map.containsKey(currentCategory)){
                double sum = map.get(currentCategory) + income.getAmount();
                sum = currencyService.rouderToDec2ForExchanges(sum);
                map.put(currentCategory, sum);
            }
            else
                map.put(currentCategory, income.getAmount());
        }

        List<List<Object>> listOfLists = new ArrayList<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            List<Object> innerList = new ArrayList<>();
            innerList.add(entry.getKey());
            innerList.add(entry.getValue());
            listOfLists.add(innerList);
        }
        return listOfLists;
    }


    public List<List<Object>> getChartDataExpence(List<Expence> expenceList) {

        Map<String, Double> map = new HashMap<>();

        for (Expence expence: expenceList){
            String currentCategory = expence.getCategory();
            if(map.containsKey(currentCategory)){
                double sum = map.get(currentCategory) + expence.getAmount();
                sum = currencyService.rouderToDec2ForExchanges(sum);
                map.put(currentCategory, sum);
            }
            else
                map.put(currentCategory, expence.getAmount());
        }

        List<List<Object>> listOfLists = new ArrayList<>();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            List<Object> innerList = new ArrayList<>();
            innerList.add(entry.getKey());
            innerList.add(entry.getValue());
            listOfLists.add(innerList);
        }

        return listOfLists;
    }

}
