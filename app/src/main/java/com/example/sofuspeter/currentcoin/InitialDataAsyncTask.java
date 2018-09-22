package com.example.sofuspeter.currentcoin;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by SofusPeter on 18-02-2017.
 */

public class InitialDataAsyncTask extends AsyncTask<String, Void, HashMap<String, CoinObject>> {

    private static final String TAG = "------------------>";
    private final MainActivity mainActivityRef;

    public InitialDataAsyncTask(MainActivity mainActivity) {
        mainActivityRef = mainActivity;

    }

    @Override
    protected HashMap<String, CoinObject> doInBackground(String... strings) {

        //Array for all coins we find
        //ArrayList<CoinObject> coinObjects = new ArrayList<>();
        HashMap<String, CoinObject>  coinObjects = new HashMap<>();

        try {
            //Connect to the API
            URL sourceUrlForApi = new URL("https://min-api.cryptocompare.com/data/all/coinlist");
            HttpURLConnection connection = (HttpURLConnection) sourceUrlForApi.openConnection();
            connection.connect();
            connection.setRequestMethod("GET");

            //Get response as JSON
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) connection.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //Get the response as string from JSON
            String baseImageUrl = jsonObject.get("BaseImageUrl").getAsString();
            String baseLinkUrl = jsonObject.get("BaseLinkUrl").getAsString();
            JsonObject data = jsonObject.getAsJsonObject("Data");           //<-- is not a list!!
            Set<Map.Entry<String, JsonElement>> coinSet = data.entrySet();  //So it is a mapping of key (string eg "BTC") to values (of JsonElements)

            for (Map.Entry ent: coinSet){
                //all required entries for creating a coinobject
                int id;
                URL url;
                URL imageUrl;
                String symbol;
                String fullName;
                String coinName;

                //Get the JsonElement for this coin, which contains above mentioned information
                JsonElement coinJsonElement = (JsonElement) ent.getValue();
                JsonObject coinJsonObject = coinJsonElement.getAsJsonObject();
                id = coinJsonObject.get("Id").getAsInt();                                                          //Internal on cryptocompare (might be needed for further calls)
                url = new URL(baseLinkUrl + coinJsonObject.get("Url").getAsString());                        //URL to the coin on cryptocompare, eg. https://www.cryptocompare.com/coins/ltc/overview
                try {                                                                                              //Not all coins has a picture!
                    imageUrl = new URL(baseImageUrl + coinJsonObject.get("ImageUrl").getAsString());             //URL to a picture of the coin, eg. https://www.cryptocompare.com/media/19782/litecoin-logo.png
                } catch (java.lang.NullPointerException nullPoint){
                    imageUrl = new URL ("https://vignette.wikia.nocookie.net/theimaginenation/images/e/ed/N-a.jpg/revision/latest?cb=20121209234504");      //Backup picture TODO: permenant link to backup picture
                }
                symbol = coinJsonObject.get("Symbol").getAsString();                                                  //Name = the symbol eg LTC
                fullName = coinJsonObject.get("FullName").getAsString();                                          //FullName = name + symbol eg Litecoin (LTC)
                coinName = coinJsonObject.get("CoinName").getAsString();                                          //coinName = name + symbol

                CoinObject co = new CoinObject(id,url,imageUrl,symbol,coinName,fullName);
                coinObjects.put(symbol,co);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coinObjects;
    }

    @Override
    protected void onPostExecute(HashMap<String, CoinObject> coinObjects) {
        super.onPostExecute(coinObjects);
        mainActivityRef.setCoinObjects(coinObjects);
    }


}