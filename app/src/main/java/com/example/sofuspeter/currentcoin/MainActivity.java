package com.example.sofuspeter.currentcoin;

import android.content.Intent;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;

//TODO: make currency spinner (so you can pick currency)
//TODO: Create "more" option
//TODO: add price change
//TODO: Plus to add new currency (how to update then?)

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ConstraintLayout layout;
    private MainActivity activity;
    private ArrayList<CoinValue> coinArrayList;
    private HashMap<String, CoinObject> coinObjects;
    private MyCustomAdapter adapter;
    private String TAG = "------->";
    private SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informUser("No action (yet!)");
            }
        });
        //
        coinArrayList = new ArrayList<>();
        coinObjects = new HashMap<>();

        //Get all coins and information about them
        new InitialDataAsyncTask(this).execute();


        //
        adapter = new MyCustomAdapter(this, R.id.coinListViewChild, coinArrayList);
        ListView coinList = (ListView) findViewById(R.id.coinList);
        coinList.setAdapter(adapter);

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

    private void fillCoinArrayList() {
        CoinValue btcCoin = new CoinValue(TICKER.BTC, 0.0, Currency.getInstance("USD"), coinObjects.get("BTC"));
        CoinValue ethCoin = new CoinValue(TICKER.ETH, 0.0, Currency.getInstance("USD"), coinObjects.get("ETH"));
        CoinValue adaCoin = new CoinValue(TICKER.ADA, 0.0, Currency.getInstance("USD"), coinObjects.get("ADA"));        //ToDo: get ticker from coinObject
        coinArrayList.add(btcCoin);
        coinArrayList.add(ethCoin);
        coinArrayList.add(adaCoin);

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

    public void informUser(final String message){
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
    public void setCoinObjects(HashMap<String, CoinObject> coinObjects) {
        this.coinObjects.clear();
        this.coinObjects.putAll(coinObjects);
        //Fill in the coin-array
        fillCoinArrayList();

    }

    public HashMap<String, CoinObject> getCoinObjects() {
        return coinObjects;
    }

    public ArrayList<CoinValue> getCoins() {
        return coinArrayList;
    }
}
