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

//ToDo: from list, create string for API
//ToDo: for all coins, update their values from API callback (not a switchcase like now!)

public class UpdaterAsyncTask extends AsyncTask<String, Void,  ArrayList<CoinValue>> {
    private static final String TAG = "------>";
    private final MainActivity mainActivityRef;

    public UpdaterAsyncTask(MainActivity mainActivity) {
        mainActivityRef = mainActivity;
    }

    @Override
    protected ArrayList<CoinValue> doInBackground(String... strings) {

        ArrayList<CoinValue> coins = mainActivityRef.getCoins();

        try {
            //Connect to the API
            //URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=DKK,USD");
            URL url = new URL("https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,ADA&tsyms=USD,DKK");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setRequestMethod("GET");

            //Get response as JSON
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) connection.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //Get the response as string from JSON
            Log.i(TAG, jsonObject.toString());
            JsonObject btc = jsonObject.get("BTC").getAsJsonObject();
            JsonObject eth = jsonObject.get("ETH").getAsJsonObject();
            JsonObject ada = jsonObject.get("ADA").getAsJsonObject();

            for (CoinValue c: coins){
                   switch (c.getTicker()){
                       case ADA:
                           c.setValue(ada.get("USD").getAsDouble());
                           break;
                       case BTC:
                           c.setValue(btc.get("USD").getAsDouble());
                           break;
                       case ETH:
                           c.setValue(eth.get("USD").getAsDouble());
                           break;
                       default:
                   }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coins;
    }

    @Override
    protected void onPostExecute(ArrayList<CoinValue> coins) {
        super.onPostExecute(coins);
        mainActivityRef.postUpdate();

    }

}