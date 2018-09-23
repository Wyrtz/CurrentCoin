package com.example.sofuspeter.currentcoin;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by SofusPeter on 22-09-2018.
 * Class to handle the activity of picking a new coin to add to the main view
 */

public class PickCoinDialog extends DialogFragment{
    private AddCoinAdapter adapter;
    private HashMap<String, CoinObject> coinObjects;
    private ArrayList<CoinObject> coinObjectsList;
    private static  final  String TAG = "PickCoinDialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coinObjects = (HashMap<String, CoinObject>) getArguments().getSerializable("coinObjects"); // ToDo: Make it take an arraylist instead
        coinObjectsList = new ArrayList<>();
        coinObjectsList.addAll(coinObjects.values());
        Collections.sort(coinObjectsList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pick_coin_activity, container, false);

        //Populate coinArrayList
        adapter = new AddCoinAdapter(getActivity(), R.id.addCoinListViewChild, coinObjectsList);
        ListView addCoinListView = (ListView) view.findViewById(R.id.addCoinList);
        addCoinListView.setAdapter(adapter);

        //Listen for what is selected
        addCoinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CoinObject co = coinObjectsList.get((int) id);
                //Toast.makeText(getContext(),co.getSymbol(),Toast.LENGTH_SHORT).show();
                Intent data = new Intent();
                data.putExtra("symbol", co.getSymbol());
                ((MainActivity)getActivity()).onActivityResult(0,0, data);      //ToDo: Not propper method of returning result!
                getDialog().dismiss();
            }
        });


        return view;
    }

}
