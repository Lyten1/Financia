package com.finance;

import com.finance.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ComponentScan("com.finance.*")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CurrencyService currencyService;

    //@Scheduled(fixedRate = 20 * 1000) // Оновлювати валюти кожні 20 секунд
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // Оновлювати валюти кожні 24 години
    public void updateCurrencies() {
        System.out.println("Updating currencies...");
        currencyService.updateAllCurrenciesInDatabase();
        System.out.println("Currencies updated");
    }
}
