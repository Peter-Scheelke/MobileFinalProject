package com.example.peterscheelke.mtgcollectionmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_KEY = "FragmentKey";

    private Fragment currentFragment;

    private FragmentManager fragmentManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.fragmentManager = getSupportFragmentManager();


        if (savedInstanceState != null) {
            int fragmentId = savedInstanceState.getInt(FRAGMENT_KEY);
            this.currentFragment = this.fragmentManager.findFragmentById(fragmentId);
        } else {
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

    @Override
    public void onBackPressed() {

        if (!FragmentManagementSystem.GoBack()) {
            this.moveTaskToBack(true);
        }
    }

    public void inform() {
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.remove(currentFragment);
        transaction.commit();

        this.currentFragment = FragmentManagementSystem.getCurrentFragment();

        transaction = this.fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_frame, this.currentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onSearchClick(MenuItem item) {
        FragmentManagementSystem.GoToHome();
    }

    public void onCollectionClick(MenuItem item) {
        Card card = new Card();
        card.CollectionQuantity = 1;
        FragmentManagementSystem.RequestSearch(card);
    }

    public void onDecksClick(MenuItem item)
    {
        FragmentManagementSystem.RequestDecks();
    }

    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
