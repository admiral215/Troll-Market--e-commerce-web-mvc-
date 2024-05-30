package com.indocyber.trollmarket.configuration;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class FormatterIndonesiaCurrency {
    public String format (BigDecimal price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return formatter.format(price);
    }

    public String format (Double price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id","ID"));
        return formatter.format(price);
    }
}
