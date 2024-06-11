package com.finance.services;

import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.repos.ExpenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenceService {

    @Autowired
    private ExpenceRepo expencesRepo;

    @Autowired
    private CurrencyService currencyService;

    public void save(Expence expence){
        expencesRepo.save(expence);
    }

    public List<Expence> getAll() {
        return expencesRepo.findAll();
    }


    public List<Expence> getDataPerDay(Long id) {
        LocalDate today = LocalDate.now();
        List<Expence> lst = getAll();
        List<Expence> resultSet = new ArrayList<>();
        lst.stream()
                .filter(expence -> expence.getDateByDate().isEqual(today))
                .filter(expence -> expence.getUserId() == id)
                .forEach(expence -> resultSet.add(expence));
        return resultSet;
    }
    public List<Expence> getDataPerWeek(Long id) {
        LocalDate today = LocalDate.now();
        LocalDate monday;
        if (today.getDayOfWeek() != DayOfWeek.MONDAY){
            monday = today.minusDays(today.getDayOfWeek().getValue());
        } else {
            monday = today;
        }
        List<Expence> lst = getAll();
        List<Expence> resultSet = new ArrayList<>();
        lst.stream()
                .filter(expence -> expence.getDateByDate().isAfter(monday.minusDays(1)))
                .filter(expence -> expence.getUserId() == id)
                .forEach(expence -> resultSet.add(expence));
        return resultSet;
    }

    public List<Expence> getDataPerMonth(Long id) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        List<Expence> lst = getAll();
        List<Expence> resultSet = new ArrayList<>();
        lst.stream()
                .filter(expence -> expence.getDateByDate().isAfter(firstDayOfMonth.minusDays(1)))
                .filter(expence -> expence.getUserId() == id)
                .forEach(expence -> resultSet.add(expence));
        return resultSet;
    }
    public List<Expence> getDataPerYear(Long id) {
        LocalDate firstDayOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        List<Expence> lst = getAll();
        List<Expence> resultSet = new ArrayList<>();
        lst.stream()
                .filter(expence -> expence.getDateByDate().isAfter(firstDayOfYear.minusDays(1)))
                .filter(expence -> expence.getUserId() == id)
                .forEach(expence -> resultSet.add(expence));
        return resultSet;
    }

    public List<Expence> getTenLastExpencesSortedReversed(List<Expence> expenceList){
        List<Expence> sortedExpenceList = expenceList.stream()
                .sorted(Comparator.comparing(Expence::getDateByDate)
                        .thenComparing(Expence::getId))
                .collect(Collectors.toList());
        Collections.reverse(sortedExpenceList);
        sortedExpenceList = sortedExpenceList.stream().limit(10).collect(Collectors.toList());
        return  sortedExpenceList;
    }

    public double getExchangeCurrency(String defaultCurrency){
        if(!defaultCurrency.equals("eur"))
            return currencyService.getExchangeCurrency(defaultCurrency);
        else
            return 1.0;
    }


    public List<Expence> transformListFromEURtoCurrency(List<Expence> expenceList){
        expenceList.stream().filter(expence -> !expence.getCurrency().equals("eur"))
                .forEach(expence ->{
                    double exch = currencyService.getExchangeCurrency(expence.getCurrency());
                    expence.setAmount(currencyService.rouderToDec2ForExchanges(expence.getAmount() * exch));
                });
        return expenceList;
    }

    public List<Expence> transformListToDefaultCurrency(List<Expence> expenceList, double exchangeCurrency){
       expenceList.forEach(expence -> expence.setAmount( currencyService.rouderToDec2ForExchanges(
               expence.getAmount() * exchangeCurrency)));
       return expenceList;
    }





}
