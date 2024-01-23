package com.finance.services;

import com.finance.models.Income;
import com.finance.repos.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepo incomeRepo;

    public List<Income> getAll() {
        return incomeRepo.findAll();
    }

}
