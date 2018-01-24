package com.example.sofuspeter.currentcoin;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Currency;

/**
 * Created by SofusPeter on 21-12-2017.
 */

public class Updater extends AsyncTask<String, Void,  ArrayList<CoinObject>> {
    private static final String TAG = "-->";
    private final MainActivity mainActivityRef;

    public Updater(MainActivity mainActivity) {
        mainActivityRef = mainActivity;

    }

    @Override
    protected ArrayList<CoinObject> doInBackground(String... strings) {

        ArrayList<CoinObject> coins = null;

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
            CoinObject btcCoin = new CoinObject(TICKER.BTC, btc.get("USD").getAsDouble(), Currency.getInstance("USD"));
            CoinObject ethCoin = new CoinObject(TICKER.ETH, eth.get("USD").getAsDouble(), Currency.getInstance("USD"));
            CoinObject adaCoin = new CoinObject(TICKER.ADA, ada.get("USD").getAsDouble(), Currency.getInstance("USD"));
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
    protected void onPostExecute(ArrayList<CoinObject> coinObjects) {
        super.onPostExecute(coinObjects);
        mainActivityRef.setCoinArrayList(coinObjects);
    }

}