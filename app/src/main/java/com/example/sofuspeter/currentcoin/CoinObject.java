package com.example.sofuspeter.currentcoin;

import android.support.annotation.NonNull;

import java.net.URL;

/**
 * Created by SofusPeter on 18-02-2018.
 */

public class CoinObject implements Comparable{

    private int id;                                         //Internal on cryptocompare (might be needed for further calls)
    private URL url;                                        //URL to the coin on cryptocompare, eg. https://www.cryptocompare.com/coins/ltc/overview
    private URL imageUrl;                                   //URL to a picture of the coin, eg. https://www.cryptocompare.com/media/19782/litecoin-logo.png
    private String symbol;                                  //Name = the symbol eg LTC
    private String coinName;                                //coinName = name eg Litecoin
    private String fullName;                                //FullName = name + symbol eg Litecoin (LTC)

    public CoinObject(int id, URL url, URL imageUrl, String symbol, String coinName, String fullName) {
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
        this.symbol = symbol;
        this.coinName = coinName.trim();
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public URL getUrl() {
        return url;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCoinName() {
        return coinName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return this.getCoinName().compareTo(((CoinObject) o).getCoinName());
    }
}
