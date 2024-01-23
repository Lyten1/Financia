package com.finance.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "income")
@Getter @Setter
public class Income{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private double amount;
    @Column(name = "category")
    private String category;
    @Column(name = "comment")
    private String comment;
    @Column(name = "userId")
    private int userId;
    @Column(name = "currency")
    private int currency;
    @Column(name = "date")
    private String date;

    public LocalDate getDateByDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }



}
