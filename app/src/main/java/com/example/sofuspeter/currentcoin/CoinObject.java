package com.example.sofuspeter.currentcoin;

import java.net.URL;

/**
 * Created by SofusPeter on 18-02-2018.
 */

public class CoinObject {

    private int id;                                         //Internal on cryptocompare (might be needed for further calls)
    private URL url;                                     //URL to the coin on cryptocompare, eg. https://www.cryptocompare.com/coins/ltc/overview
    private URL imageUrl;                                //URL to a picture of the coin, eg. https://www.cryptocompare.com/media/19782/litecoin-logo.png
    private String name;                                    //Name = the symbol eg LTC
    private String coinName;                                //FullName = name + symbol eg Litecoin (LTC)
    private String fullName;                                //coinName = name + symbol

    public CoinObject(int id, URL url, URL imageUrl, String name, String coinName, String fullName) {
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
        this.name = name;
        this.coinName = coinName;
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

    public String getName() {
        return name;
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
}
