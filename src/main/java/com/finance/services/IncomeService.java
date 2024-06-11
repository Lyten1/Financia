package com.finance.services;

import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.repos.IncomeRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
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
import java.util.stream.Stream;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepo incomeRepo;

    @Autowired
    private CurrencyService currencyService;

    public void save(Income income){
        incomeRepo.save(income);
    }


    public List<Income> getAll() {
        return incomeRepo.findAll();
    }


    public List<Income> getDataPerDay(Long id) {
        LocalDate today = LocalDate.now();
        List<Income> lst = getAll();
        List<Income> resultSet = new ArrayList<>();
        lst.stream()
                .filter(income -> income.getDateByDate().isEqual(today))
                .filter(income -> income.getUserId() == id)
                .forEach(income -> resultSet.add(income));
        return resultSet;
    }

    public List<Income> getDataPerWeek(Long id) {
        LocalDate today = LocalDate.now();
        LocalDate monday;
        if (today.getDayOfWeek() != DayOfWeek.MONDAY){
            monday = today.minusDays(today.getDayOfWeek().getValue());
        } else {
            monday = today;
        }
        List<Income> lst = getAll();
        List<Income> resultSet = new ArrayList<>();
        lst.stream()
                .filter(income -> income.getDateByDate().isAfter(monday.minusDays(1)))
                .filter(income -> income.getUserId() == id)
                .forEach(income -> resultSet.add(income));
        return resultSet;
    }

    public List<Income> getDataPerMonth(Long id) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        List<Income> lst = getAll();
        List<Income> resultSet = new ArrayList<>();
        lst.stream()
                .filter(income -> income.getDateByDate().isAfter(firstDayOfMonth.minusDays(1)))
                .filter(income -> income.getUserId() == id)
                .forEach(income -> resultSet.add(income));
       return resultSet;
    }

    public List<Income> getDataPerYear(Long id) {
        LocalDate firstDayOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        List<Income> lst = getAll();
        List<Income> resultSet = new ArrayList<>();
        lst.stream()
                .filter(income -> income.getDateByDate().isAfter(firstDayOfYear.minusDays(1)))
                .filter(income -> income.getUserId() == id)
                .forEach(income -> resultSet.add(income));
        return resultSet;
    }


    public List<Income> getTenLastIncomesSortedReversed(List<Income> incomeList){
        List<Income> sortedIncomeList = incomeList.stream()
                .sorted(Comparator.comparing(Income::getDateByDate)
                        .thenComparing(Income::getId))
                .collect(Collectors.toList());
        Collections.reverse(sortedIncomeList);
        sortedIncomeList = sortedIncomeList.stream().limit(10).collect(Collectors.toList());
        return  sortedIncomeList;
    }

    public double getExchangeCurrency(String defaultCurrency){
        if(!defaultCurrency.equals("eur"))
            return currencyService.getExchangeCurrency(defaultCurrency);
        else
            return 1.0;
    }

    public List<Income> transformListFromEURtoCurrency(List<Income> incomeList){
        incomeList.stream().filter(expence -> !expence.getCurrency().equals("eur"))
                .forEach(income ->{
                    double exch = currencyService.getExchangeCurrency(income.getCurrency());
                    income.setAmount(currencyService.rouderToDec2ForExchanges(income.getAmount() * exch));
                });
        return incomeList;
    }

    public List<Income> transformListToDefaultCurrency(List<Income> incomeList, double exchangeCurrency){
        incomeList.forEach(income -> income.setAmount( currencyService.rouderToDec2ForExchanges(
                income.getAmount() * exchangeCurrency)));
        return incomeList;
    }


}
