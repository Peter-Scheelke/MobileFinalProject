package com.example.peterscheelke.mtgcollectionmanager.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;
import com.example.peterscheelke.mtgcollectionmanager.FragmentManagementSystem;
import com.example.peterscheelke.mtgcollectionmanager.ListAdapter;
import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.List;

/**
 * Created by Peter Scheelke on 12/4/2016.
 */

public class ListFragment extends Fragment {

    private static List<Tuple<String, Integer>> cardsOrDecks;
    private static String header1 = "";
    private static String header2 = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View header = (View)getActivity().getLayoutInflater().inflate(R.layout.listview_row,null);
        TextView name = (TextView) header.findViewById(R.id.name);
        name.setText(header1);
        name.setTypeface(name.getTypeface(), Typeface.BOLD);

        TextView quantity = (TextView) header.findViewById(R.id.quantity);
        quantity.setText(header2);
        quantity.setTypeface(quantity.getTypeface(), Typeface.BOLD);

        final ListView listView = (ListView) getView().findViewById(R.id.cardOrDeckListView);
        listView.setHeaderDividersEnabled(true);
        listView.addHeaderView(header, null, false);

        ListAdapter adapter = new ListAdapter(getActivity(), cardsOrDecks);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                Tuple<String, Integer> item = (Tuple<String, Integer>) (listView.getItemAtPosition(myItemInt));

                FragmentManagementSystem.RequestCard(item.first);
            }
        });
    }

    public void InitializeFragment(List<Tuple<String, Integer>> nameQuantity, String header1, String header2)
    {
        cardsOrDecks = nameQuantity;
        ListFragment.header1 = header1;
        ListFragment.header2 = header2;
    }
}
