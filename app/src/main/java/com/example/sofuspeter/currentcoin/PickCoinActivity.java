package com.example.sofuspeter.currentcoin;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by SofusPeter on 22-09-2018.
 * Class to handle the activity of picking a new coin to add to the main view
 */

public class PickCoinActivity extends AppCompatActivity{
    private AddCoinAdapter adapter;
    ArrayList<CoinObject> coinArrayList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ListView addCoinListView = (ListView) findViewById(R.id.addCoinList);
        coinArrayList = new ArrayList<>();
        //Populate coinArrayList
        adapter = new AddCoinAdapter(this,R.id.addCoinListViewChild,coinArrayList);
        addCoinListView.setAdapter(adapter);
    }
}
