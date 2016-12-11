package com.example.peterscheelke.mtgcollectionmanager.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.peterscheelke.mtgcollectionmanager.BackendManager;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;
import com.example.peterscheelke.mtgcollectionmanager.ListAdapter;
import com.example.peterscheelke.mtgcollectionmanager.MainActivity;
import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private List<Tuple<String, Integer>> cardsOrDecks;
    private String header1 = "";
    private String header2 = "";

    private static final String CARD_LIST_KEY = "CardListKey";
    private static final String QUANTITY_LIST_KEY = "QuantityListKey";
    private static final String HEADER1_KEY = "Header1";
    private static final String HEADER2_KEY = "Header2";

    private boolean isInCardMode = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        restoreState(savedInstanceState);

        View header = getActivity().getLayoutInflater().inflate(R.layout.listview_row,null);
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

                if (isInCardMode)
                {
                    BackendManager.GetManager().RequestCard(item.first);
                }
                else
                {
                    BackendManager.GetManager().RequestDeck(item.first);
                }

                Intent intent= new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        BackendManager.HideKeyboardFrom(getContext(), new View(getContext()));
    }

    public void InitializeFragment(List<Tuple<String, Integer>> nameQuantity, String header1, String header2)
    {
        this.cardsOrDecks = nameQuantity;
        this.header1 = header1;
        this.header2 = header2;
    }

    public void setOnClickMode(boolean isInCardMode)
    {
        this.isInCardMode = isInCardMode;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<String> cards = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        for (Tuple<String, Integer> value : this.cardsOrDecks)
        {
            cards.add(value.first);
            quantities.add(value.last);
        }

        outState.putStringArrayList(CARD_LIST_KEY, cards);
        outState.putIntegerArrayList(QUANTITY_LIST_KEY, quantities);
        outState.putString(HEADER1_KEY, header1);
        outState.putString(HEADER2_KEY, header2);
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ArrayList<String> cards = savedInstanceState.getStringArrayList(CARD_LIST_KEY);
            ArrayList<Integer> quantities = savedInstanceState.getIntegerArrayList(QUANTITY_LIST_KEY);
            this.cardsOrDecks = new ArrayList<>();
            for (int i = 0; i < cards.size(); ++i)
            {
                this.cardsOrDecks.add(new Tuple<>(cards.get(i), quantities.get(i)));
            }

            this.header1 = savedInstanceState.getString(HEADER1_KEY);
            this.header2 = savedInstanceState.getString(HEADER2_KEY);
        }
    }
}
