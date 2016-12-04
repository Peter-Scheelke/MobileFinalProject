package com.example.peterscheelke.mtgcollectionmanager;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Scheelke on 12/4/2016.
 */

public class ListFragment extends Fragment {

    private static List<Tuple<String, Integer>> cardsOrDecks;
    private static String labelText = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ListView yourListView = (ListView) findViewById(R.id.itemListView);
        // get data from the table by the ListAdapter
        //ListAdapter customAdapter = new ListAdapter(this, R.layout.itemlistrow, List<yourItem>);
        //yourListView .setAdapter(customAdapter);

        ListView listView = (ListView) getView().findViewById(R.id.cardOrDeckListView);

        ListAdapter adapter = new ListAdapter(getActivity(), cardsOrDecks);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, this.names);
        listView.setAdapter(adapter);
    }

    public void InitializeFragment(List<Tuple<String, Integer>> nameQuantity, String labelText, boolean showQuantities)
    {
        cardsOrDecks = nameQuantity;
        ListFragment.labelText = labelText;
    }
}
