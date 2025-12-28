package com.example.demo.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.net.URL;

@Service
public class CurrencyService {

    public static String getCurrencyRate(String message) throws IOException, ParseException {
        URL url = new URL("https://www.nbrb.by/api/exrates/rates/" + message + "?parammode=2");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()){
            result.append(scanner.nextLine());
        }
        JSONObject object = new JSONObject(result.toString());

        return "Официальный курс щяс BYN " + object.getString("Cur_Abbreviation") + "\n" +
                "на дату: " + getFormatDate(new Date()) + "\n" +
                "во: " + object.getDouble("Cur_OfficialRate") + " BYN за " + object.getInt("Cur_Scale") + " " + object.getString("Cur_Abbreviation");
    }

    private static String getFormatDate(Date data) {
        return new SimpleDateFormat("dd MMM yyyy").format(data);
    }
}
