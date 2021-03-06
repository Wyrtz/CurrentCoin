package com.example.sofuspeter.currentcoin;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by SofusPeter on 18-01-2018.
 */

//ToDo: bedre navn og beskrivelse
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
            view = layoutInflater.inflate(R.layout.coin_list_element_grid_fixed,null);
        }

        CoinValue p = getItem(position);

        if (p!=null){
            ImageView tickerIcon = view.findViewById(R.id.tickerIcon);
            TextView coinTicker = view.findViewById(R.id.coinTicker);
            TextView value = view.findViewById(R.id.value);
            TextView change = view.findViewById(R.id.change);
            TextView myValue = view.findViewById(R.id.myValue);

            if(tickerIcon != null){
                Glide.with(getContext()).load(p.getTickerImage().toString()).into(tickerIcon);      //ToDo: not all pictures are equal.. ADA ser sjov ud (sort baggrund?) og man kan se ramme på ETH. However! Seems like it's ctypotcompare who got shitty images (https://www.cryptocompare.com/media/12318177/ada.png)

            }

            if(coinTicker != null){
                coinTicker.setText(p.getFullName());
            }

            if(value != null){
                value.setText(String.format(p.getValue().toString()));      //Recommended with String.format to get localication of how to write doubles
            }

            if(change != null){
                change.setText(p.getChange());
            }

            if(myValue != null){
                myValue.setText(String.format(p.getMyValue().toString()));
            }

        }

        return view;
    }
}
