package com.example.sofuspeter.currentcoin;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by SofusPeter on 21-12-2017.
 */

public class Updater extends AsyncTask<String, Void, Void> {
    private static final String TAG = "-->";
    private final View rootView;

    public Updater(View rootView) {
        this.rootView = rootView;

    }

    @Override
    protected Void doInBackground(String... strings) {
        try{
            //Connect to the API
            URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=ETH&tsyms=DKK,USD");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setRequestMethod("GET");
            Log.i(TAG,connection.getResponseMessage());

            //Get response as JSON
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) connection.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //Get the response as string from JSON
            Log.i(TAG,jsonObject.toString());
            jsonObject.get("DKK").getAsFloat();
            Log.i(TAG,""+jsonObject.get("DKK").getAsFloat());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i(TAG,"Done");
//        Snackbar mySnack = Snackbar.make(rootView,"Done",Snackbar.LENGTH_SHORT);
//        mySnack.show();

    }

    //https://www.coindesk.com/api/
//    @Override
//    protected Void doInBackground(String... strings) {
//        try{
//            //Connect to the website API
//            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice/DKK.json");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//            connection.setRequestMethod("GET");
//            Log.i(TAG,connection.getResponseMessage());
//
//            //Get response as JSON
//            JsonParser jsonParser = new JsonParser();
//            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) connection.getContent()));
//            JsonObject jsonObject = jsonElement.getAsJsonObject();
//
//            //Get the response as string from JSON
//            JsonObject bpi = jsonObject.get("bpi").getAsJsonObject();
//            JsonObject dkkRates = bpi.get("DKK").getAsJsonObject();
//            float dkkRate = dkkRates.get("rate_float").getAsFloat();
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
