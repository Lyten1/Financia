package com.finance.services;

import com.finance.models.Income;
import com.finance.repos.IncomeRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepo incomeRepo;

    public List<Income> getAll() {
        return incomeRepo.findAll();
    }


    public List<Income> getDataPerMonth(Long id) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Income> lst = getAll();
        List<Income> resultSet = new ArrayList<>();
        lst.stream()
                .filter(income -> income.getDateByDate().isAfter(firstDayOfMonth))
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
                .filter(income -> income.getDateByDate().isAfter(monday))
                .filter(income -> income.getUserId() == id)
                .forEach(income -> resultSet.add(income));
        return resultSet;
    }

}
