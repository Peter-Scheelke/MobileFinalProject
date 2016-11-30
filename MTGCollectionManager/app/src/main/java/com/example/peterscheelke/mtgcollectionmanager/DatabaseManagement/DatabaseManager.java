package com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

// A singleton class that accesses the database of MTG cards
public class DatabaseManager
{
    /*
    // The single instance of the com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager class
    private static DatabaseManager uniqueInstance;

    // The database containing the MTG cards
    private static final String DATABASE = "mtgdatabase";

    // Used to make sure the com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager is correctly synchronized
    private static Object databaseLock = new Object();

    // Used to access the database of MTG cards
    private SQLiteOpenHelper helper;

    // The database of MTG cards
    private SQLiteDatabase database = null;

    // Prevents a default instance of com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager
    // from being created
    private DatabaseManager(Context context)
    {
        helper = new DBHelper(context);
    }

    // Gets a reference to the single com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager object
    public static DatabaseManager GetManager(Context context)
    {
        synchronized (databaseLock)
        {
            if (uniqueInstance == null)
            {
                uniqueInstance = new DatabaseManager(context);
            }

            return uniqueInstance;
        }
    }

    public void Test()
    {
        synchronized (databaseLock)
        {

        }
    }*/
}
