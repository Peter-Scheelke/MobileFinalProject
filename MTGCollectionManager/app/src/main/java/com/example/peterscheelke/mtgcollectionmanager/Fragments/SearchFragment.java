package com.example.peterscheelke.mtgcollectionmanager.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.peterscheelke.mtgcollectionmanager.BackendManager;
import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.Cards.Color;
import com.example.peterscheelke.mtgcollectionmanager.MainActivity;
import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.setUpCheckboxBackgrounds();

        this.SetUpEventHandlers();
    }

    private void SetUpEventHandlers() {
        Button button = (Button) getView().findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchButtonClick(v);
            }
        });
    }

    private void setUpCheckboxBackgrounds()
    {
        // White
        ImageView imageView = (ImageView) getView().findViewById(R.id.whiteCheckboxBackground);
        imageView.setImageResource(R.drawable.whitemana);

        imageView = (ImageView) getView().findViewById(R.id.whiteIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.whitemana);

        // Blue
        imageView = (ImageView) getView().findViewById(R.id.blueCheckboxBackground);
        imageView.setImageResource(R.drawable.bluemana);

        imageView = (ImageView) getView().findViewById(R.id.blueIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.bluemana);

        // Black
        imageView = (ImageView) getView().findViewById(R.id.blackCheckboxBackground);
        imageView.setImageResource(R.drawable.blackmana);

        imageView = (ImageView) getView().findViewById(R.id.blackIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.blackmana);

        // Red
        imageView = (ImageView) getView().findViewById(R.id.redCheckboxBackground);
        imageView.setImageResource(R.drawable.redmana);

        imageView = (ImageView) getView().findViewById(R.id.redIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.redmana);

        // Green
        imageView = (ImageView) getView().findViewById(R.id.greenCheckboxBackground);
        imageView.setImageResource(R.drawable.greenmana);

        imageView = (ImageView) getView().findViewById(R.id.greenIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.greenmana);

        // Colorless
        imageView = (ImageView) getView().findViewById(R.id.colorlessCheckboxBackground);
        imageView.setImageResource(R.drawable.colorlessmana);

        imageView = (ImageView) getView().findViewById(R.id.colorlessIdentityCheckboxBackground);
        imageView.setImageResource(R.drawable.colorlessmana);
    }

    public void onSearchButtonClick(View v)
    {
        Card card = new Card();

        EditText textBox = (EditText) getView().findViewById(R.id.cardNameEditText);
        card.Name = textBox.getText().toString();

        textBox = (EditText) getView().findViewById(R.id.typesEditText);
        String types = textBox.getText().toString();
        String[] splitTypes = types.split(" ");
        card.Types = new ArrayList<>();
        for (String type : splitTypes) {
            if (type.length() > 0) {
                String lowerCase = type.toLowerCase();
                String firstChar = lowerCase.substring(0,1);
                String rest = lowerCase.substring(1, lowerCase.length());
                String complete = firstChar.toUpperCase() + rest;
                card.Types.add(complete);
            }
        }

        textBox = (EditText) getView().findViewById(R.id.subtypesEditText);
        String subtypes = textBox.getText().toString();
        String[] splitSubtypes = subtypes.split(" ");
        card.Subtypes = new ArrayList<>();
        for (String subtype : splitSubtypes) {
            if (subtype.length() > 0) {
                String lowerCase = subtype.toLowerCase();
                String firstChar = lowerCase.substring(0,1);
                String rest = lowerCase.substring(1, lowerCase.length());
                String complete = firstChar.toUpperCase() + rest;
                card.Subtypes.add(complete);
            }
        }

        getSelectedColors(card);
        getSelectedIdentity(card);

        textBox = (EditText) getView().findViewById(R.id.cmcEditText);
        if (textBox.getText().toString().length() > 0) {
            card.CMC = Double.parseDouble(textBox.getText().toString());
        }

        textBox = (EditText) getView().findViewById(R.id.powerEditText);
        card.Power = textBox.getText().toString();

        textBox = (EditText) getView().findViewById(R.id.toughnessEditText);
        card.Toughness = textBox.getText().toString();

        textBox = (EditText) getView().findViewById(R.id.cardTextEditText);
        card.Text = textBox.getText().toString();
    }

    private void getSelectedIdentity(Card card) {
        card.ColorIdentity = new ArrayList<>();

        CheckBox checkBox = (CheckBox) getView().findViewById(R.id.whiteIdentityCheckBox);
        if (checkBox.isChecked())
        {
            card.ColorIdentity.add(Color.White);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.blueIdentityCheckBox);
        if (checkBox.isChecked())
        {
            card.ColorIdentity.add(Color.Blue);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.blackIdentityCheckBox);
        if (checkBox.isChecked())
        {
            card.ColorIdentity.add(Color.Black);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.redIdentityCheckBox);
        if (checkBox.isChecked())
        {
            card.ColorIdentity.add(Color.Red);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.greenIdentityCheckBox);
        if (checkBox.isChecked())
        {
            card.ColorIdentity.add(Color.Green);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.colorlessIdentityCheckBox);
        if (checkBox.isChecked())
        {
            card.ColorIdentity.add(Color.None);
        }
    }

    private void getSelectedColors(Card card) {
        card.Colors = new ArrayList<>();

        CheckBox checkBox = (CheckBox) getView().findViewById(R.id.whiteCheckBox);
        if (checkBox.isChecked())
        {
            card.Colors.add(Color.White);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.blueCheckBox);
        if (checkBox.isChecked())
        {
            card.Colors.add(Color.Blue);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.blackCheckBox);
        if (checkBox.isChecked())
        {
            card.Colors.add(Color.Black);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.redCheckBox);
        if (checkBox.isChecked())
        {
            card.Colors.add(Color.Red);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.greenCheckBox);
        if (checkBox.isChecked())
        {
            card.Colors.add(Color.Green);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.colorlessCheckBox);
        if (checkBox.isChecked())
        {
            card.Colors.add(Color.None);
        }

        checkBox = (CheckBox) getView().findViewById(R.id.collectionCheckBox);
        if (checkBox.isChecked())
        {
            card.CollectionQuantity = 1;
        }

        this.StartSearchResultActivity(card);
    }

    private void StartSearchResultActivity(Card card) {
        if (BackendManager.GetManager().RequestSearchResults(card)) {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
