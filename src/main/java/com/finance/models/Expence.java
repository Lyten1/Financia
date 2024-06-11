package com.finance.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "expences")
@Getter @Setter
public class Expence {
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
    private Long userId;
    @Column(name = "currency")
    private String currency;
    @Column(name = "date")
    private String date;

    public LocalDate getDateByDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    public String convertDate(String inputDate) {

        String[] parts = inputDate.split("-");
        String outputDate = parts[2] + "/" + parts[1] + "/" + parts[0];
        return outputDate;
    }


    @Override
    public String toString() {
        return this.getAmount() + " - " + this.getCurrency();
    }
}
