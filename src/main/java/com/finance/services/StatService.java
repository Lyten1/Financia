package com.finance.services;

import com.finance.repos.ExpenceRepo;
import com.finance.repos.IncomeRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatService {

    @Autowired
    private ExpenceRepo expenceRepo;

    @Autowired
    private IncomeRepo incomeRepo;




}
