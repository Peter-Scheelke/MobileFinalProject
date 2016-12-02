package com.example.peterscheelke.mtgcollectionmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DatabaseManager.InitializeManager(this);
            DatabaseManager manager = DatabaseManager.GetManager();

            Card card = new Card();
            card.ColorIdentity = new ArrayList<>();
            card.Colors = new ArrayList<>();
            card.Types = new ArrayList<>();
            card.Subtypes = new ArrayList<>();
            List<String> names = manager.GetAllCardNames(card);
            Log.d("Names", "onCreate: " + Integer.toString(names.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
