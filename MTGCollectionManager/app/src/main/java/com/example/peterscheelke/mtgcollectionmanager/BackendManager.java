package com.example.peterscheelke.mtgcollectionmanager;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.CardFragment;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.ListFragment;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.SearchFragment;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.UpdateFragment;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

public class BackendManager {

    private static BackendManager uniqueInstance;

    private Stack<MainActivity> backStack;

    private DatabaseManager databaseManager;

    private MainActivity mostRecentActivity = null;
    private Fragment fragmentToAddToMostRecentActivity;

    private BackendManager(Context context) {
        backStack = new Stack<>();
        try {
            DatabaseManager.InitializeManager(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.databaseManager = DatabaseManager.GetManager();
        this.fragmentToAddToMostRecentActivity = new SearchFragment();
    }

    public void ActivityCreationRequest(MainActivity activity) {
        this.mostRecentActivity = activity;
        activity.SetFragment(fragmentToAddToMostRecentActivity);
        fragmentToAddToMostRecentActivity = null;
    }

    public static void InitializeManager(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new BackendManager(context);
        }
    }

    public static BackendManager GetManager()
    {
        return uniqueInstance;
    }

    public void RequestNextFragment(MainActivity activity)
    {
        SearchFragment fragment = new SearchFragment();
        activity.SetFragment(fragment);
    }

    public void RequestSearchForm() {
        SearchFragment fragment = new SearchFragment();
        this.fragmentToAddToMostRecentActivity = fragment;
    }

    public void RequestSearchResults(Card card) {
        List<Tuple<String, Integer>> searchResults = this.databaseManager.GetAllCardNames(card);
        ListFragment fragment = new ListFragment();
        fragment.InitializeFragment(searchResults, "Card", "Quantity");
        this.fragmentToAddToMostRecentActivity = fragment;
    }

    public void RequestAllDecks() {
        List<Tuple<String, Integer>> decks = this.databaseManager.GetDecks();
        ListFragment fragment = new ListFragment();
        fragment.InitializeFragment(decks, "Deck", "Cards");
        fragment.setOnClickMode(false); // Will open a deck instead of a card if this is false
        this.fragmentToAddToMostRecentActivity = fragment;
    }

    public void RequestDeck(String deckName) {
        List<Tuple<String, Integer>> cards = this.databaseManager.GetDeck(deckName);
        ListFragment fragment = new ListFragment();
        fragment.InitializeFragment(cards, deckName, "Quantity");
        this.fragmentToAddToMostRecentActivity = fragment;
    }

    public void RequestCollection() {
        Card card = new Card();
        card.CollectionQuantity = 1;
        List<Tuple<String, Integer>> cards = this.databaseManager.GetAllCardNames(card);
        ListFragment fragment = new ListFragment();
        fragment.InitializeFragment(cards, "Card", "Quantity");
        this.fragmentToAddToMostRecentActivity = fragment;
    }

    public void RequestCard(String cardName) {
        Card card = this.databaseManager.GetCardByName(cardName);
        CardFragment.SetCard(card);
        CardFragment fragment = new CardFragment();
        this.fragmentToAddToMostRecentActivity = fragment;
    }

    public void RequestCardUpdate(String cardName) {
        List<Tuple<String, Integer>> deckQuantities = this.databaseManager.GetDecksWithCardQuantities(cardName);
        int collectionQuantity = this.databaseManager.GetCardByName(cardName).CollectionQuantity;
        UpdateFragment fragment = new UpdateFragment();
        fragment.initializeFragment(cardName, deckQuantities, collectionQuantity);
        this.fragmentToAddToMostRecentActivity = fragment;
    }

    public void UpdateCollectionQuantity(String cardName, int quantity) {
        this.databaseManager.UpdateCollectionQuantity(cardName, quantity);
    }

    public void UpdateDeckQuantity(String cardName, String deckName, int quantity) {

    }

    public void CreateDeck(String deckName) {
        if (this.databaseManager.CreateDeck(deckName)) {

        }
    }

    public void RemoveDeck(String deckName) {
        if (this.databaseManager.DeleteDeck(deckName)) {

        }
    }
}
