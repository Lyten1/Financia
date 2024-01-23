package com.finance.services;

import com.finance.models.Expence;
import com.finance.models.Income;
import com.finance.repos.ExpenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenceService {

    @Autowired
    private ExpenceRepo expencesRepo;

    public List<Expence> getAll() {
        return expencesRepo.findAll();
    }


    public List<Expence> getDataPerMonth(Long id) {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Expence> lst = getAll();
        List<Expence> resultSet = new ArrayList<>();
        lst.stream()
                .filter(expence -> expence.getDateByDate().isAfter(firstDayOfMonth))
                .filter(expence -> expence.getUserId() == id)
                .forEach(expence -> resultSet.add(expence));
        return resultSet;
    }
}
