package com.finance.services;

import com.finance.models.Expence;
import com.finance.repos.ExpenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenceService {

    @Autowired
    private ExpenceRepo expencesRepo;

    public List<Expence> getAll() {
        return expencesRepo.findAll();
    }
}
