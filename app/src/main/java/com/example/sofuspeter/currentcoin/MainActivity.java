package com.example.sofuspeter.currentcoin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;

//TODO: make currency spinner (so you can pick currency)
//TODO: Create "more" option
//TODO: add price change
//TODO: Plus to add new currency (how to update then?)
//ToDo: make coins consistent to "on create"
//ToDo: delete coin! Swipe or long press ?
//ToDo: other sites: Info, news, helpful links

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ConstraintLayout layout;
    private MainActivity activity;
    private ArrayList<CoinValue> coinArrayList;
    private HashMap<java.lang.String, CoinObject> coinObjects;
    private MyCustomAdapter adapter;
    private java.lang.String TAG = "------->";
    private String shPrefName = "SavedCoins";
    private String shPrefCoinArrayKey = "shPrefCoinArrayKey";
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private SharedPreferences shPref;
    private Gson gson;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
        case 0:
            String requestedCoinSymbol = data.getStringExtra("symbol");
            addCoin(requestedCoinSymbol);
        }
    }

    //Add a new coin to the list
    private void addCoin(String requestCode) {
    CoinObject coinObject = coinObjects.get(requestCode);
    CoinValue newCoin = new CoinValue(coinObject.getSymbol(),1.0,Currency.getInstance("USD"), coinObject); //ToDo: Handle other currencies
    coinArrayList.add(newCoin);
    adapter.notifyDataSetChanged();
    new UpdaterAsyncTask(this).execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        SharedPreferences.Editor editor = shPref.edit();
        String coinArrayString = gson.toJson(coinArrayList);
        editor.putString(shPrefCoinArrayKey, coinArrayString);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get shared preferences and Gson to look for coins
        shPref = this.getSharedPreferences(shPrefName,this.MODE_PRIVATE);
        gson = new Gson();

        //
        activity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCoinButton();
            }
        });

        //
        coinArrayList = new ArrayList<>();
        coinObjects = new HashMap<>();

        //Get all coins and information about them
        new InitialDataAsyncTask(this).execute();

        View header = getLayoutInflater().inflate(R.layout.coin_list_header,null);

        //
        adapter = new MyCustomAdapter(this, R.id.coinListViewChild, coinArrayList);
        final ListView coinList = (ListView) findViewById(R.id.coinList);
        coinList.addHeaderView(header);
        coinList.setAdapter(adapter);

        //ToDo: "undo" of deletion
        //Set long press to delete object
        coinList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                informUser("Deleted " + coinArrayList.get(i-1).getFullName());
                coinArrayList.remove(i-1);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        //Set press/tap top open more information


        /** Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * * performs a swipe-to-refresh gesture.
         * */
        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new UpdaterAsyncTask(activity).execute();
                    }
                }
        );

    }

    //Start dialog to pick new coin from list of all possible coins (aka. those found in coinObjects)
    private void addCoinButton() {
        PickCoinDialog pickCoinDialog = new PickCoinDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("coinObjects", coinObjects);
        pickCoinDialog.setArguments(bundle);
       // pickCoinDialog.setTargetFragment(,0);
        pickCoinDialog.show(getFragmentManager(),"PickCoinDialog");

    }

    private void fillCoinArrayList() {
        //Get coins from disk
        CoinValue btcCoin = new CoinValue("BTC", 0.0, Currency.getInstance("USD"), coinObjects.get("BTC"));
        coinArrayList.add(btcCoin);
        String readFromSharedPref = shPref.getString(shPrefCoinArrayKey,"notFound");
        if (readFromSharedPref.equals("notFound")){
            Log.d(TAG, readFromSharedPref);
            //CoinValue btcCoin = new CoinValue("BTC", 0.0, Currency.getInstance("USD"), coinObjects.get("BTC"));
            CoinValue ethCoin = new CoinValue("ETH", 0.0, Currency.getInstance("USD"), coinObjects.get("ETH"));        //ToDo: get coins from disk
            CoinValue adaCoin = new CoinValue("ADA", 0.0, Currency.getInstance("USD"), coinObjects.get("ADA"));        //ToDo: get ticker from coinObject
            //coinArrayList.add(btcCoin);
            coinArrayList.add(ethCoin);
            coinArrayList.add(adaCoin);
        }
        else{
            Log.d(TAG,"readFromSharedPref:" + readFromSharedPref);
            coinArrayList = gson.fromJson(readFromSharedPref, new TypeToken<ArrayList<CoinValue>>() {
            }.getType());
            for (CoinValue cv: coinArrayList){
                Log.d(TAG,"Is one");
                CoinValue newCoin = new CoinValue(cv.getTicker(),0.0, Currency.getInstance("USD"),coinObjects.get(cv.getTicker()));
                coinArrayList.add(newCoin);
            }
        }
        //adapter.notifyDataSetChanged();
        //and update their values with UpdaterAsyncTask
        new UpdaterAsyncTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void informUser(final java.lang.String message){
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    //After updating the rates. Re-render the adapter (list), stop the "update" symbol and write "updated"
    public void postUpdate() {
        adapter.notifyDataSetChanged();
        mySwipeRefreshLayout.setRefreshing(false);
        informUser("Updated");

    }

    //This method is called by "initialDataAsyncTask" on completion. The AsyncTask has now downloaded the name of all coins available at the API, and it is saved in coinObjects.
    //Now, fill in the coinArrayList ( which holds all coins to be displayed)
    public void setCoinObjects(HashMap<java.lang.String, CoinObject> coinObjects) {
        this.coinObjects.clear();
        this.coinObjects.putAll(coinObjects);
        //Fill in the coin-array
        fillCoinArrayList();

    }

    public HashMap<java.lang.String, CoinObject> getCoinObjects() {
        return coinObjects;
    }

    public ArrayList<CoinValue> getCoins() {
        return coinArrayList;
    }
}
