package com.example.peterscheelke.mtgcollectionmanager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;
import com.example.peterscheelke.mtgcollectionmanager.FragmentManagementSystem;
import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.ArrayList;
import java.util.List;


public class UpdateFragment extends Fragment {

    private String cardName;

    private List<Tuple<String, Integer>> deckQuantities;

    private int collectionQuantity;

    private int currentIndex;
    private String currentDeck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textView = (TextView) getView().findViewById(R.id.nameOfCardTextView);
        textView.setText(this.cardName);

        EditText editText = (EditText) getView().findViewById(R.id.updateCollectionQuantityEditText);
        editText.setText(String.format("%1$s", this.collectionQuantity));

        editText = (EditText) getView().findViewById(R.id.updateDeckQuantityEditText);

        int deckQuantity = 0;
        if (this.deckQuantities.size() > 0)
        {
            deckQuantity = this.deckQuantities.get(0).last;
            this.currentIndex = 0;
            this.currentDeck = this.deckQuantities.get(0).first;
        }
        else
        {
            this.currentIndex = -1;
            this.currentDeck = "";
        }

        editText.setText(String.format("%1$s", deckQuantity));

        Button button = (Button) getView().findViewById(R.id.updateCollectionQuantityButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCollectionUpdateClick(v);
            }
        });

        Spinner spinner = (Spinner) getView().findViewById(R.id.deckSpinner);
        List<String> decknames = new ArrayList<>();
        for (Tuple<String, Integer> deck : deckQuantities) {
            decknames.add(deck.first);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, decknames);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentIndex = position;
                currentDeck = deckQuantities.get(currentIndex).first;
                EditText deckQuantityEditText = (EditText)getView().findViewById(R.id.updateDeckQuantityEditText);
                deckQuantityEditText.setText(String.format("%1$s", deckQuantities.get(currentIndex).last));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentIndex = -1;
                currentDeck = "";
            }
        }
    }

    public void initializeFragment(String cardName, List<Tuple<String, Integer>> deckQuantities, int collectionQuantity)
    {
        this.cardName = cardName;
        this.deckQuantities = deckQuantities;
        this.collectionQuantity = collectionQuantity;
    }

    public void onCollectionUpdateClick(View v)
    {
        EditText editText = (EditText) getView().findViewById(R.id.updateCollectionQuantityEditText);
        int quantity = Integer.parseInt(editText.getText().toString());
        FragmentManagementSystem.UpdateCollectionQuantity(this.cardName, quantity);
    }

    public void onUpdateDeckClick(View v) {
        if (currentIndex != -1)
        {
            EditText editText = (EditText) getView().findViewById(R.id.updateDeckQuantityEditText);
            //int quantity = Integer.parseInt(editText.getText().toString());
            //FragmentManagementSystem.UpdateDeckQuantity();
        }
    }
}
