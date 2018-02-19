package com.example.sofuspeter.currentcoin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SofusPeter on 18-01-2018.
 */
//https://stackoverflow.com/questions/8166497/custom-adapter-for-list-view
public class MyCustomAdapter extends ArrayAdapter<CoinValue> {


    private static final String TAG = "--->";

    public MyCustomAdapter(Context context, int resource, ArrayList<CoinValue> coinList) {
        super(context, 0, coinList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.simple_coin_list_element,null);
        }

        CoinValue p = getItem(position);

        if (p!=null){
            TextView coinTicker = (TextView) view.findViewById(R.id.coinTicker);
            TextView value = (TextView) view.findViewById(R.id.value);
            TextView currency = (TextView) view.findViewById(R.id.currency);
            TextView change = (TextView) view.findViewById(R.id.change);
            TextView myValue = (TextView) view.findViewById(R.id.myValue);
            TextView more = (TextView) view.findViewById(R.id.more);

            if(coinTicker != null){
                coinTicker.setText("" + p.getTicker());
                Log.i(TAG, "Setting coinTicker:" + p.getTicker());
//                Log.i(TAG, "Setting :" + );

            }

            if(value!= null){
                value.setText("" + p.getValue());
            }

            if(currency!= null){
                currency.setText("" + p.getCurrency());
            }

            if(change != null){
                coinTicker.setText("" + p.getChange());
            }

            if(myValue!= null){
                myValue.setText("" + p.getMyValue());
            }

            if(more != null){
                coinTicker.setText("" + p.getMore());
            }

        }

        return view;
    }
}
