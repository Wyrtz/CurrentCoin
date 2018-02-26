package com.example.sofuspeter.currentcoin;

import android.icu.math.BigDecimal;

import java.net.URL;
import java.util.Currency;

/**
 * Created by SofusPeter on 18-01-2018.
 */
//https://www.geeksforgeeks.org/java-util-currency-methods-example/
class CoinValue {

    private final CoinObject coinObject;
    private TICKER ticker;
    private Double value;
    private Currency currency;

    public CoinValue(TICKER ticker, Double value, Currency currency,CoinObject coinObject) {
        this.ticker = ticker;
        this.value = value;
        this.currency = currency;
        this.coinObject = coinObject;
    }

    public TICKER getTicker() {
        return ticker;
    }

    public Double getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public String getChange(){
        return "+5%";
    }

    public Double getMyValue(){
        return (double) Math.round(value * 0.1 * 100)/100;
    }

    public URL getCoinURL(){
        return coinObject.getUrl();
    }

    public URL getTickerImage(){
       return coinObject.getImageUrl();
    }

    public String getFullName(){
        return coinObject.getFullName();
    }

    @Override
    public String toString() {
        return "" + ticker + " " + value + " " + currency;
    }
}
