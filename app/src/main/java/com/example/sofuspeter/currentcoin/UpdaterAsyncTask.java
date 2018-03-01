package com.example.sofuspeter.currentcoin;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by SofusPeter on 21-12-2017.
 */

public class UpdaterAsyncTask extends AsyncTask<String, Void,  ArrayList<CoinValue>> {
    private static final String TAG = "------>";
    private final MainActivity mainActivityRef;

    public UpdaterAsyncTask(MainActivity mainActivity) {
        mainActivityRef = mainActivity;
    }

    @Override
    protected ArrayList<CoinValue> doInBackground(String... strings) {

        ArrayList<CoinValue> coins = mainActivityRef.getCoins();

        //Format the URL, ready to make a call to the API
        String baseURL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=";
        String fullURL = baseURL + formRequest(coins);
        Log.i(TAG,fullURL);

        try {
            //Connect to the API
            URL url = new URL(fullURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setRequestMethod("GET");

            //Get response as JSON
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) connection.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //For all coins in the list, create a Jsonobject from the name of the ticker. Set the value of said coin to the value (denoted with the desirred currency)
            for(CoinValue coin:coins){
                JsonObject coinJsonObject = jsonObject.get(coin.getTicker()).getAsJsonObject();
                String currency = coin.getCurrency().toString();
                coin.setValue(coinJsonObject.get(currency).getAsDouble());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coins;
    }

    private String formRequest( ArrayList<CoinValue> coins) {
        //Example of correct format: https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,ADA,&tsyms=USD
        String response = "";
        for(CoinValue coin: coins){
            response = response.concat(coin.getTicker() + ",");
        }
        response = response.concat("&tsyms=");

        //As of now, all are in the same currency
        response = response.concat(coins.get(0).getCurrency().toString());

        return response;
    }

    @Override
    protected void onPostExecute(ArrayList<CoinValue> coins) {
        super.onPostExecute(coins);
        mainActivityRef.postUpdate();

    }

}