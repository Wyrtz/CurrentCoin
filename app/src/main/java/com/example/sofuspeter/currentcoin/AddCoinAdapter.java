package com.example.sofuspeter.currentcoin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by SofusPeter on 22-09-2018.
 */

public class AddCoinAdapter extends ArrayAdapter<CoinObject> {

    private static final String TAG = "--->";

    public AddCoinAdapter(Context context, int resource, ArrayList<CoinObject> coins){
        super(context,0, coins);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.pick_coin_list_element,null);
        }

        CoinObject coinObject = getItem(position);

        if(coinObject != null){
            ImageView tickerIcon = view.findViewById(R.id.tickerIcon);
            TextView coinName = view.findViewById(R.id.coinName);
            TextView coinTicker = view.findViewById(R.id.coinTicker);


//            if(tickerIcon != null){
//                Glide.with(getContext()).load(p.getTi) //ToDo: get the picture! No getTickerImage in coinObject...
//            }

            if(coinName != null) {coinName.setText(String.format(coinObject.getCoinName()));}
            if(coinTicker != null) {coinTicker.setText(String.format(coinObject.getSymbol()));}

        }

        return view;
    }
}
