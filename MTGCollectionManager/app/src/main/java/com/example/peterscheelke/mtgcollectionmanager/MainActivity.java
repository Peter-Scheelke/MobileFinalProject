package com.example.peterscheelke.mtgcollectionmanager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String SEARCH_FRAGMENT_KEY = "searchFragmentId";
    private static final String LIST_FRAGMENT_KEY = "listFragmentId";
    private static final String CARD_FRAGMENT_KEY = "cardFragmentId";

    private FragmentManager fragmentManager;
    private SearchFragment searchFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            int searchFragmentId = savedInstanceState.getInt(SEARCH_FRAGMENT_KEY);
            this.searchFragment = (SearchFragment)fragmentManager.findFragmentById(searchFragmentId);
        }
        else {
            this.searchFragment = new SearchFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_frame, searchFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putInt(SEARCH_FRAGMENT_KEY, this.searchFragment.getId());
    }
}
