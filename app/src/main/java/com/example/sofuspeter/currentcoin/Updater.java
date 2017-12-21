package com.example.sofuspeter.currentcoin;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
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

    public Updater() {

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i(TAG,"Done");
    }

    @Override
    protected Void doInBackground(String... strings) {
        try{
            //Connect to the website API
            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice/DKK.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setRequestMethod("GET");
            Log.i(TAG,connection.getResponseMessage());
            //Get response as JSON
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) connection.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            Log.i(TAG, String.valueOf(jsonObject == null));

            //Get the response as string from JSON
            String yah = jsonObject.get("bpi").getAsJsonObject().get("rate").getAsString();
            Log.i(TAG, yah);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
