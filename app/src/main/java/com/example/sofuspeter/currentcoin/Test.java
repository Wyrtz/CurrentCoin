package com.example.sofuspeter.currentcoin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by SofusPeter on 19-01-2018.
 */

public class Test extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_list_element);

        TextView coinTicker = (TextView) findViewById(R.id.coinTicker);
        TextView value = (TextView) findViewById(R.id.value);
        TextView currency = (TextView) findViewById(R.id.currency);
        TextView change = (TextView) findViewById(R.id.change);
        TextView myValue = (TextView) findViewById(R.id.myValue);
        TextView more = (TextView) findViewById(R.id.more);

        coinTicker.setText("BTC");
    }
}
