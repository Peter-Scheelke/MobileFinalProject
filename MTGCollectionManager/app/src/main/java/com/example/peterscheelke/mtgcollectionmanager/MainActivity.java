package com.example.peterscheelke.mtgcollectionmanager;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
