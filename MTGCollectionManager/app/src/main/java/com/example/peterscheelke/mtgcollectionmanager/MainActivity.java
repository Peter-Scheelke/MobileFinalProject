package com.example.peterscheelke.mtgcollectionmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.CardFragment;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.ListFragment;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.SearchFragment;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_KEY = "FragmentKey";

    private Fragment currentFragment;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fragmentManager = getSupportFragmentManager();


        if (savedInstanceState != null) {
            int fragmentId = savedInstanceState.getInt(FRAGMENT_KEY);
            this.currentFragment = this.fragmentManager.findFragmentById(fragmentId);
        }
        else
        {
            try {
                FragmentManagementSystem.Initialize(this, this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.currentFragment = FragmentManagementSystem.getCurrentFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_frame, this.currentFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putInt(FRAGMENT_KEY, this.currentFragment.getId());
    }

    public void inform()
    {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.remove(currentFragment);
        transaction.commit();

        this.currentFragment = FragmentManagementSystem.getCurrentFragment();

        transaction = this.fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_frame, this.currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
