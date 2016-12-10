package com.example.peterscheelke.mtgcollectionmanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_KEY = "FragmentKey";

    private Fragment currentFragment = null;

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
        }
        else {
            BackendManager.InitializeManager(this);
            BackendManager.GetManager().ActivityCreationRequest(this);
            BackendManager.GetManager().RequestNextFragment(this);
        }
    }

    public void SetFragment(Fragment fragment) {
        if (this.currentFragment == null)
        {
            this.currentFragment = fragment;
            FragmentTransaction transaction = this.fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_frame, this.currentFragment);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        icicle.putInt(FRAGMENT_KEY, this.currentFragment.getId());
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onSearchMenuClick(MenuItem item) {
        BackendManager.GetManager().RequestSearchForm();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCollectionMenuClick(MenuItem item) {
        BackendManager.GetManager().RequestCollection();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDecksMenuClick(MenuItem item)
    {
        BackendManager.GetManager().RequestAllDecks();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void showToast(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
