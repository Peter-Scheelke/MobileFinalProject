package com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// A singleton class that accesses the database of MTG cards
public class DatabaseManager
{
    private static DatabaseManager uniqueInstance;

    private DataBaseHelper helper;

    private DatabaseManager(Context context) throws IOException
    {
        this.helper = new DataBaseHelper(context);
        this.helper.createDataBase();
    }

    public static void InitializeManager(Context context) throws IOException
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

    public List<String> GetAllCardNames(Card searchCard)
    {
        QueryFactory factory = new QueryFactory();
        Query query = factory.CreateQuery(searchCard, false);

        this.helper.openDataBase();
        Cursor cursor = this.helper.executeQuery(query.query, query.parameters);
        List<String> names = new ArrayList<>();
        while (cursor.moveToNext())
        {
            names.add(cursor.getString(0));
        }

        this.helper.close();
        return names;
    }
}
