package com.example.sofuspeter.currentcoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

//TODO: Get ticker icon
//TODO: make currency spinner (so you can pick currency)
//TODO: Create "more" option
//TODO: Swipe down updates
//TODO: add price change
//TODO: Plus to add new currency (how to update then?)

public class MainActivity extends AppCompatActivity {

    private String updateSuccessText = "Updated rates";
    private FloatingActionButton fab;
    private View rootView;
    private ConstraintLayout layout;
    private MainActivity activity;
    private ArrayList<CoinValue> coinArrayList;
    private ArrayList<CoinObject> coinObjects;
    MyCustomAdapter adapter;
    private String TAG = "-->";

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
                Snackbar.make(view, updateSuccessText, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new UpdaterAsyncTask(activity).execute();
            }
        });
        new UpdaterAsyncTask(this).execute();
        new InitialDataAsyncTask(this).execute();

        //
        coinArrayList = new ArrayList<>();
        coinObjects = new ArrayList<>();

        //
        adapter = new MyCustomAdapter(this, R.id.coinListViewChild, coinArrayList);
        ListView coinList = (ListView) findViewById(R.id.coinList);
        coinList.setAdapter(adapter);
//        adapter.add(testCoin4);

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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void changeView(View view) {
        Intent navIntent = new Intent(this, Test.class);
        startActivity(navIntent);
    }

    public void setCoinArrayList(ArrayList<CoinValue> coinArrayList) {
        this.coinArrayList.clear();
        this.coinArrayList.addAll(coinArrayList);
        adapter.notifyDataSetChanged();
    }

    public void setCoinObjects(ArrayList<CoinObject> coinObjects) {
        this.coinObjects.clear();
        this.coinObjects.addAll(coinObjects);
    }
}
