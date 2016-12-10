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
import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.ArrayList;
import java.util.List;


public class UpdateFragment extends Fragment {

    private String cardName;
    private static final String CARD_NAME_KEY = "CardNameKey";

    private List<Tuple<String, Integer>> deckQuantities;
    private static final String DECKS_KEY = "DecksKey";
    private static final String QUANTITIES_KEY = "QuantitiesKey";

    private int collectionQuantity;
    private static final String COLLECTION_QUANTITY_KEY = "CollectionQuantityKey";

    private int currentIndex;
    private static final String CURRENT_INDEX_KEY = "CurrentIndexKey";

    private String currentDeck;
    private static final String CURRENT_DECK_KEY = "CurrentDeckKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.restoreState(savedInstanceState);

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

        InitializeSpinner();

        button = (Button) getView().findViewById(R.id.removeDeckButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRemoveDeckClick(v);
            }
        });

        button = (Button) getView().findViewById(R.id.newDeckButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddDeckClick(v);
            }
        });

        button = (Button) getView().findViewById(R.id.updateDeckQuantityButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateDeckClick(v);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void InitializeSpinner() {
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
                EditText deckQuantityEditText = (EditText)getView().findViewById(R.id.updateDeckQuantityEditText);
                deckQuantityEditText.setText(String.format("%1$s", 0));
            }
        });
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
        //FragmentManagementSystem.UpdateCollectionQuantity(this.cardName, quantity);
    }

    public void onRemoveDeckClick(View v) {
        if (currentIndex != -1) {
            /*
            if (FragmentManagementSystem.DeleteDeck(currentDeck)) {
                deckQuantities.remove(currentIndex);
                currentIndex = -1;
                currentDeck = "";

                Spinner spinner = (Spinner) getView().findViewById(R.id.deckSpinner);
                List<String> deckNames = new ArrayList<>();
                for (Tuple<String, Integer> deck : deckQuantities) {
                    deckNames.add(deck.first);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, deckNames);
                spinner.setAdapter(adapter);
            }*/
        }
    }

    public void onAddDeckClick(View v) {
        EditText editText = (EditText) getView().findViewById(R.id.deckNameInputEditText);
        String newDeckName = editText.getText().toString();

        /*
        if (FragmentManagementSystem.CreateDeck(newDeckName)) {
            editText.setText("");
            deckQuantities.add(new Tuple<>(newDeckName, 0));
            Spinner spinner = (Spinner) getView().findViewById(R.id.deckSpinner);
            List<String> deckNames = new ArrayList<>();
            for (Tuple<String, Integer> deck : deckQuantities) {
                deckNames.add(deck.first);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, deckNames);
            spinner.setAdapter(adapter);
        }*/
    }

    public void onUpdateDeckClick(View v) {
        if (currentIndex != -1)
        {
            EditText editText = (EditText) getView().findViewById(R.id.updateDeckQuantityEditText);
            int quantity = Integer.parseInt(editText.getText().toString());
            //FragmentManagementSystem.UpdateDeckQuantity(cardName, deckQuantities.get(currentIndex).first, quantity);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putString(CARD_NAME_KEY, this.cardName);

        ArrayList<String> decks = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        for (Tuple<String, Integer> deckQuantity : this.deckQuantities) {
            decks.add(deckQuantity.first);
            quantities.add(deckQuantity.last);
        }

        state.putIntegerArrayList(QUANTITIES_KEY, quantities);
        state.putStringArrayList(DECKS_KEY, decks);

        state.putInt(COLLECTION_QUANTITY_KEY, this.collectionQuantity);
        state.putInt(CURRENT_INDEX_KEY, this.currentIndex);
        state.putString(CURRENT_DECK_KEY, this.currentDeck);
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.cardName = savedInstanceState.getString(CARD_NAME_KEY);

            ArrayList<String> decks = savedInstanceState.getStringArrayList(DECKS_KEY);
            ArrayList<Integer> quantities = savedInstanceState.getIntegerArrayList(QUANTITIES_KEY);
            this.deckQuantities = new ArrayList<>();
            for (int i = 0; i < decks.size(); ++i) {
                this.deckQuantities.add(new Tuple<>(decks.get(i), quantities.get(i)));
            }

            this.collectionQuantity = savedInstanceState.getInt(COLLECTION_QUANTITY_KEY);
            this.currentIndex = savedInstanceState.getInt(CURRENT_INDEX_KEY);
            this.currentDeck = savedInstanceState.getString(CURRENT_DECK_KEY);
        }
    }
}