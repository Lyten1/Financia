package com.finance.repos;

import com.finance.models.Currency;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Currency c SET c.currencyRelateToEur = :value WHERE c.currencyName = :name")
    void updateCurrency(@Param("value") double value, @Param("name") String name);


    @Query("SELECT c FROM Currency c WHERE c.currencyName = :name")
    Optional<Currency> getCurrencyByCurrencyName(@Param("name") String name);
}
