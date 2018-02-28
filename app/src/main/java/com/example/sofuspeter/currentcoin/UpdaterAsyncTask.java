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
import java.util.Currency;
import java.util.HashMap;

/**
 * Created by SofusPeter on 21-12-2017.
 */

//ToDo: make Main activity create coins and put them in a list
//ToDo: make a coins value able to update its value(s)
//ToDo: give list to updater
//ToDo: from list, create string for API
//ToDo: for all coins, update their values from API callback

public class UpdaterAsyncTask extends AsyncTask<String, Void,  ArrayList<CoinValue>> {
    private static final String TAG = "-->";
    private final MainActivity mainActivityRef;

    public UpdaterAsyncTask(MainActivity mainActivity) {
        mainActivityRef = mainActivity;

    }

    @Override
    protected ArrayList<CoinValue> doInBackground(String... strings) {

        ArrayList<CoinValue> coins = null;
        HashMap<String, CoinObject> coinObjects = null;

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

            coins  = new ArrayList<>();
            coinObjects = mainActivityRef.getCoinObjects();
            CoinValue btcCoin = new CoinValue(TICKER.BTC, btc.get("USD").getAsDouble(), Currency.getInstance("USD"), coinObjects.get("BTC"));
            CoinValue ethCoin = new CoinValue(TICKER.ETH, eth.get("USD").getAsDouble(), Currency.getInstance("USD"), coinObjects.get("ETH"));
            CoinValue adaCoin = new CoinValue(TICKER.ADA, ada.get("USD").getAsDouble(), Currency.getInstance("USD"), coinObjects.get("ADA"));        //ToDo: get ticker from coinObject
            coins.add(btcCoin);
            coins.add(ethCoin);
            coins.add(adaCoin);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coins;
    }

    @Override
    protected void onPostExecute(ArrayList<CoinValue> coinValues) {
        super.onPostExecute(coinValues);
        mainActivityRef.setCoinArrayList(coinValues);
    }

}