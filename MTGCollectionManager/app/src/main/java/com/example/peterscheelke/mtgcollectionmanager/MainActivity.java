package com.example.peterscheelke.mtgcollectionmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DataBaseHelper helper = new DataBaseHelper(this);
            helper.createDataBase();
            helper.openDataBase();
            helper.TestStuff();
            helper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
