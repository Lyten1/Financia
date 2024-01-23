package com.finance.repos;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.finance.models.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepo  extends JpaRepository<Income, Long> {


    @Override
    List<Income> findAll();
}
