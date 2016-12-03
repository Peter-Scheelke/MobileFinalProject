package com.example.peterscheelke.mtgcollectionmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            DatabaseManager.InitializeManager(this);
            DatabaseManager manager = DatabaseManager.GetManager();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
