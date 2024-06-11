package com.finance.services;

import com.finance.models.Currency;
import com.finance.repos.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import org.json.JSONObject;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepo currencyRepo;

    private final List<String> currencyNamesArray = Arrays.asList("usd", "aud", "nok",
            "cad", "czk", "jpy", "pln", "sek", "chf", "uah", "gbp", "cny");



    public void updateAllCurrenciesInDatabase(){

        try {
            // Задаємо URL-адресу для HTTP-запиту
            String url = "https://www.floatrates.com/daily/eur.json";

            // Створюємо об'єкт URL і відкриваємо з'єднання
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            // Задаємо тип HTTP-запиту
            connection.setRequestMethod("GET");

            // Отримуємо відповідь від сервера
            int responseCode = connection.getResponseCode();

            // Якщо запит успішний (код 200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Створюємо читач для зчитування відповіді
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                // Зчитуємо відповідь рядок за рядком
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());

                for (String currencyName: currencyNamesArray) {
                    JSONObject Currency = jsonObject.getJSONObject(currencyName);

                    double rate = Currency.getDouble("rate");

                    Currency currency = new Currency(rate, currencyName);


                    if(currencyRepo.getCurrencyByCurrencyName(currencyName).equals(Optional.empty()))
                        currencyRepo.save(currency);
                    else
                        currencyRepo.updateCurrency(currency.getCurrencyRelateToEur(), currency.getCurrencyName());
                }
            } else {
                // Якщо запит не успішний, виводимо повідомлення про помилку
                System.out.println("HTTP GET request failed. Response Code: " + responseCode);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public double getExchangeCurrency(String defaultCurrency){
        Currency currency = currencyRepo.getCurrencyByCurrencyName(defaultCurrency).orElse(null);
        if(currency != null)
            return currency.getCurrencyRelateToEur();
        else
            return 0.0;
    }

    public double rouderToDec2ForExchanges(double amount){
        return Math.round(amount*100.0)/100.0;
    }

    public List<String> getListOfCurrencies(String defCurrency){
        List<String> currencies = new ArrayList<>();
        currencies.add(defCurrency);
        if(!defCurrency.equals("eur")){
            currencies.add("eur");
        }
        currencyNamesArray.stream().filter(cur -> !currencies.contains(cur)).forEach(currencies::add);
        return currencies;
    }

}
