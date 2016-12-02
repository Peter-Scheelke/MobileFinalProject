package com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// A singleton class that accesses the database of MTG cards
public class DatabaseManager
{
    private static DatabaseManager uniqueInstance;

    private DataBaseHelper helper;

    private DatabaseManager(Context context)
    {
        this.helper = new DataBaseHelper(context);
    }

    public static void InitializeManager(Context context)
    {
        if (uniqueInstance == null)
        {
            uniqueInstance = new DatabaseManager(context);
        }
        else
        {
            uniqueInstance.helper.close();
            uniqueInstance.helper = new DataBaseHelper(context);
        }
    }

    public static DatabaseManager GetManager()
    {
        return uniqueInstance;
    }

    public List<String> GetAllCardNames()
    {
        this.helper.openDataBase();
        Cursor cursor = this.helper.executeQuery("SELECT Name FROM Cards", null);
        List<String> names = new ArrayList<String>();
        while (cursor.moveToNext())
        {
            names.add(cursor.getString(0));
        }

        this.helper.close();
        return names;
    }
}
