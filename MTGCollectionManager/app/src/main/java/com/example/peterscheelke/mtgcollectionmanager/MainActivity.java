package com.example.peterscheelke.mtgcollectionmanager;

import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String SEARCH_FRAGMENT_KEY = "searchFragmentId";
    private static final String LIST_FRAGMENT_KEY = "listFragmentId";
    private static final String CARD_FRAGMENT_KEY = "cardFragmentId";

    private FragmentManager fragmentManager;
    private SearchFragment searchFragment;
    private ListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            //int searchFragmentId = savedInstanceState.getInt(SEARCH_FRAGMENT_KEY);
            //this.searchFragment = (SearchFragment)fragmentManager.findFragmentById(searchFragmentId);

            int listFragmentId = savedInstanceState.getInt(LIST_FRAGMENT_KEY);
            this.listFragment = (ListFragment)fragmentManager.findFragmentById(listFragmentId);
        }
        else {
            //this.searchFragment = new SearchFragment();
            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //fragmentTransaction.add(R.id.fragment_frame, searchFragment);
            //fragmentTransaction.commit();

            this.listFragment = new ListFragment();
            Card card = new Card();
            card.Name = "An";
            try {
                DatabaseManager.InitializeManager(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                List<Tuple<String, Integer>> cardNamesQuantities = DatabaseManager.GetManager().GetAllCardNames(card);
                this.listFragment.InitializeFragment(cardNamesQuantities, "Collection", true);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_frame, this.listFragment);
                fragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        //icicle.putInt(SEARCH_FRAGMENT_KEY, this.searchFragment.getId());
        icicle.putInt(LIST_FRAGMENT_KEY, this.listFragment.getId());
    }
}
