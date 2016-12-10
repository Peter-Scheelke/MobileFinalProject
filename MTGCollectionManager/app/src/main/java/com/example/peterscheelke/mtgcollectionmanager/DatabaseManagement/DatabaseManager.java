package com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement;

import android.database.Cursor;
import android.content.Context;
import android.database.SQLException;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.Cards.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void Close()
    {
        this.helper.close();
    }

    // Get all of the cards from the database that match the given card
    public List<Tuple<String, Integer>> GetAllCardNames(Card searchCard)
    {
        QueryBuilder factory = new QueryBuilder();
        Query query = factory.CreateQuery(searchCard);
        List<Tuple<String, Integer>> names = new ArrayList<>();
        try
        {
            this.helper.openDataBase();
            Cursor cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                Tuple<String, Integer> nameQuantity = new Tuple<>(cursor.getString(0), cursor.getInt(1));
                names.add(nameQuantity);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.helper.close();
        }

        return names;
    }

    // Get all the cards for a single deck
    public List<Tuple<String, Integer>> GetDeck(String deckname)
    {
        List<Tuple<String, Integer>> deck = new ArrayList<>();

        String queryString = "SELECT Card, Quantity FROM Decks WHERE Name = ? AND NOT Card IS NULL;";
        String[] parameters = {deckname};

        Query query = new Query();
        query.query = queryString;
        query.parameters = parameters;

        try
        {
            this.helper.openDataBase();
            Cursor cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                String name = cursor.getString(0);
                int deckQuantity = cursor.getInt(1);
                Tuple<String, Integer> deckData = new Tuple<>(name, deckQuantity);
                deck.add(deckData);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            this.helper.close();
        }

        return deck;
    }

    // Get a list of the names/card counts of all decks
    public List<Tuple<String, Integer>> GetDecks()
    {
        List<Tuple<String, Integer>> deckNames = new ArrayList<>();

        try
        {
            this.helper.openDataBase();
            Query query = new Query();
            query.query = "SELECT Name, SUM(Quantity) FROM Decks GROUP BY Name;";
            Cursor cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                Tuple<String, Integer> deck = new Tuple<>(cursor.getString(0), cursor.getInt(1));
                deckNames.add(deck);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.helper.close();
        }

        return deckNames;
    }

    public List<Tuple<String, Integer>> GetDecksWithCardQuantities(String cardName)
    {
        List<Tuple<String, Integer>> decksAndQuantities = new ArrayList<>();

        // Get all the decks the card is in
        String queryString = "SELECT Name, Quantity FROM Decks WHERE Card = ?";
        List<String> parameters = new ArrayList<>();
        parameters.add(cardName);

        Query query = new Query();
        query.query = queryString;
        query.parameters = parameters.toArray(new String[0]);

        uniqueInstance.helper.openDataBase();
        Cursor cursor = uniqueInstance.helper.executeQuery(query.query, query.parameters);

        Map<String, Integer> deckMap = new HashMap<>();
        while (cursor.moveToNext())
        {
            deckMap.put(cursor.getString(0), cursor.getInt(1));
        }

        query.query = "SELECT DISTINCT Name FROM Decks";
        cursor = uniqueInstance.helper.executeQuery(query.query, null);
        while (cursor.moveToNext())
        {
            String deck = cursor.getString(0);
            int quantity = 0;
            if (deckMap.containsKey(deck))
            {
                quantity = deckMap.get(deck);
            }

            decksAndQuantities.add(new Tuple<>(deck, quantity));
        }

        uniqueInstance.helper.close();
        return decksAndQuantities;
    }

    // Get the details of a single card
    public Card GetCardByName(String name)
    {
        Card card = new Card();

        // Get main data
        Query query = new Query();
        query.query = "SELECT Name, ManaCost, CMC, CompleteType, Text, Power, Toughness, Quantity FROM Cards WHERE Name = ?;";
        String[] parameters = {name};
        query.parameters = parameters;

        try
        {
            this.helper.openDataBase();

            // Get data from the Cards table
            Cursor cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                card.Name = cursor.getString(0);
                card.ManaCost = cursor.getString(1);
                card.CMC = cursor.getDouble(2);
                card.CompleteType = cursor.getString(3);
                card.Text = cursor.getString(4);
                card.Power = cursor.getString(5);
                card.Toughness = cursor.getString(6);
                card.CollectionQuantity = cursor.getInt(7);
            }

            // Get Types
            query.query = "SELECT Type FROM Types WHERE Card = ?;";
            query.parameters = parameters;
            card.Types = new ArrayList<>();
            cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                card.Types.add(cursor.getString(0));
            }

            // Get Subtypes
            query.query = "SELECT Subtype FROM Subtypes WHERE Card = ?;";
            query.parameters = parameters;
            card.Subtypes = new ArrayList<>();
            cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                card.Subtypes.add(cursor.getString(0));
            }

            // Get Colors
            query.query = "SELECT Color FROM Colors WHERE Card = ?;";
            query.parameters = parameters;
            card.Colors = new ArrayList<>();
            cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                card.Colors.add(Color.valueOf(cursor.getString(0)));
            }

            // Get ColorIdentity
            query.query = "SELECT ColorIdentity FROM ColorIdentities WHERE Card = ?;";
            query.parameters = parameters;
            card.ColorIdentity = new ArrayList<>();
            cursor = this.helper.executeQuery(query.query, query.parameters);
            while (cursor.moveToNext())
            {
                card.ColorIdentity.add(Color.valueOf(cursor.getString(0)));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.helper.close();
        }

        return card;
    }

    // Update a card's collection quantity
    public void UpdateCollectionQuantity(String name, int quantity)
    {
        Query query = new Query();
        query.query = "UPDATE Cards SET Quantity = ? WHERE Name = ?";
        if (quantity < 0)
        {
            quantity = 0;
        }

        query.parameters = new String[] {Integer.toString(quantity), name};

        try
        {
            this.helper.openDataBase();
            this.helper.executeNonQuery(query.query, query.parameters);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.helper.close();
        }
    }

    // Update the quantity of a card in a deck
    public void UpdateDeckQuantity(String card, String deck, int quantity)
    {
        try
        {
            this.helper.openDataBase();
            if (quantity < 0)
            {
                quantity = 0;
            }

            Query query = new Query();
            query.query = "DELETE FROM Decks WHERE Name = ? AND Card = ?;";
            query.parameters = new String[] {deck, card};
            this.helper.executeNonQuery(query.query, query.parameters);

            if (quantity > 0)
            {
                query.query = "INSERT INTO Decks (Name, Card, Quantity) VALUES (?, ?, ?);";
                query.parameters = new String[]{deck, card, Integer.toString(quantity)};
                this.helper.executeNonQuery(query.query, query.parameters);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.helper.close();
        }
    }

    public boolean CreateDeck(String deckName)
    {
        try {
            Query query = new Query();
            query.query = "SELECT Name FROM Decks WHERE Name = ?;";
            query.parameters = new String[] {deckName};

            uniqueInstance.helper.openDataBase();

            Cursor cursor = uniqueInstance.helper.executeQuery(query.query, query.parameters);
            if (cursor.getCount() == 0)
            {
                query.query = "INSERT INTO Decks (Name) VALUES (?);";
                uniqueInstance.helper.executeNonQuery(query.query, query.parameters);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            uniqueInstance.helper.close();
        }

        return false;
    }

    public boolean DeleteDeck(String deckName) {
        try
        {
            uniqueInstance.helper.openDataBase();
            Query query = new Query();
            query.query = "DELETE FROM Decks WHERE Name = ?;";
            query.parameters = new String[] {deckName};
            uniqueInstance.helper.executeNonQuery(query.query, query.parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            uniqueInstance.helper.close();
        }

        return true;
    }
}
