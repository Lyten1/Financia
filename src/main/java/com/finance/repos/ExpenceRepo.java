package com.finance.repos;

import com.finance.models.Expence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenceRepo extends JpaRepository<Expence, Long> {
    @Override
    List<Expence> findAll();
}
