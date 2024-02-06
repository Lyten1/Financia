package com.finance.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "currencies")
@Getter @Setter
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "currencyVal")
    private double currencyRelateToEur;
    @Column(name = "currencyAbb")
    private String currencyName;

    public Currency() {
    }

    public Currency(double currencyRelateToEur, String currencyName) {
        this.currencyRelateToEur = currencyRelateToEur;
        this.currencyName = currencyName;
    }



}
