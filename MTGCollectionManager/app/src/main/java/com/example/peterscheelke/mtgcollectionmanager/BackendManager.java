package com.example.peterscheelke.mtgcollectionmanager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

    private boolean shouldClearBackStack;

    private BackendManager(Context context) {
        backStack = new Stack<>();
        try {
            DatabaseManager.InitializeManager(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.databaseManager = DatabaseManager.GetManager();
        this.fragmentToAddToMostRecentActivity = new SearchFragment();
        this.shouldClearBackStack = false;
    }

    public void ActivityCreationRequest(MainActivity activity) {
        if (this.shouldClearBackStack)
        {
            while (this.backStack.size() > 0)
            {
                MainActivity topActivity = this.backStack.pop();
                topActivity.finish();
            }
        }

        this.mostRecentActivity = activity;
        this.backStack.add(activity);

        if (fragmentToAddToMostRecentActivity != null) {
            activity.SetFragment(fragmentToAddToMostRecentActivity);
        }
        else
        {
            activity.SetFragment(new SearchFragment());
        }

        fragmentToAddToMostRecentActivity = null;
        this.shouldClearBackStack = false;
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

    public boolean RequestSearchForm() {
        SearchFragment fragment = new SearchFragment();
        this.fragmentToAddToMostRecentActivity = fragment;
        return true;
    }

    public boolean RequestSearchResults(Card card) {
        List<Tuple<String, Integer>> searchResults = this.databaseManager.GetAllCardNames(card);
        if (searchResults.size() == 0) {
            this.ShowMessageShort(mostRecentActivity, "No cards found");
            return false;
        }
        else if (searchResults.size() == 1)
        {
            this.ShowMessageShort(mostRecentActivity, "One card found");
            Card completeCard = this.databaseManager.GetCardByName(searchResults.get(0).first);
            CardFragment.SetCard(completeCard);
            this.fragmentToAddToMostRecentActivity = new CardFragment();
            return true;
        }
        else
        {
            this.ShowMessageShort(mostRecentActivity, String.format("%1$s cards found", searchResults.size()));
            ListFragment fragment = new ListFragment();
            fragment.InitializeFragment(searchResults, "Card", "Quantity");
            this.fragmentToAddToMostRecentActivity = fragment;
            return true;
        }
    }

    public boolean RequestAllDecks() {
        List<Tuple<String, Integer>> decks = this.databaseManager.GetDecks();
        if (decks.size() > 0) {
            ListFragment fragment = new ListFragment();
            fragment.InitializeFragment(decks, "Deck", "Cards");
            fragment.setOnClickMode(false); // Will open a deck instead of a card if this is false
            this.fragmentToAddToMostRecentActivity = fragment;
            if (decks.size() == 1) {
                this.ShowMessageShort(this.mostRecentActivity, String.format("One deck found",decks.size()));
            }
            else
            {
                this.ShowMessageShort(this.mostRecentActivity, String.format("%1$s decks found",decks.size()));
            }

            return true;
        }
        else
        {
            this.ShowMessageShort(this.mostRecentActivity, "No decks found");
            return false;
        }
    }

    public boolean RequestDeck(String deckName) {
        List<Tuple<String, Integer>> cards = this.databaseManager.GetDeck(deckName);
        ListFragment fragment = new ListFragment();
        fragment.InitializeFragment(cards, deckName, "Quantity");
        this.fragmentToAddToMostRecentActivity = fragment;
        return true;
    }

    public boolean RequestCollection() {
        Card card = new Card();
        card.CollectionQuantity = 1;
        List<Tuple<String, Integer>> cards = this.databaseManager.GetAllCardNames(card);
        if (cards.size() == 0) {
            this.ShowMessageShort(this.mostRecentActivity, "No cards found");
            return false;
        }
        else if (cards.size() == 1)
        {
            ListFragment fragment = new ListFragment();
            fragment.InitializeFragment(cards, "Card", "Quantity");
            this.fragmentToAddToMostRecentActivity = fragment;
            this.ShowMessageShort(this.mostRecentActivity, String.format("One card found", cards.size()));
            return true;
        }
        else
        {
            ListFragment fragment = new ListFragment();
            fragment.InitializeFragment(cards, "Card", "Quantity");
            this.fragmentToAddToMostRecentActivity = fragment;
            this.ShowMessageShort(this.mostRecentActivity, String.format("%1$s cards found", cards.size()));
            return true;
        }
    }

    public boolean RequestCard(String cardName) {
        Card card = this.databaseManager.GetCardByName(cardName);
        CardFragment.SetCard(card);
        CardFragment fragment = new CardFragment();
        this.fragmentToAddToMostRecentActivity = fragment;
        return true;
    }

    public boolean RequestCardUpdate(String cardName) {
        List<Tuple<String, Integer>> deckQuantities = this.databaseManager.GetDecksWithCardQuantities(cardName);
        int collectionQuantity = this.databaseManager.GetCardByName(cardName).CollectionQuantity;
        UpdateFragment fragment = new UpdateFragment();
        fragment.initializeFragment(cardName, deckQuantities, collectionQuantity);
        this.fragmentToAddToMostRecentActivity = fragment;
        return true;
    }

    public void UpdateCollectionQuantity(String cardName, int quantity) {
        this.databaseManager.UpdateCollectionQuantity(cardName, quantity);
        this.ShowMessageShort(this.mostRecentActivity, String.format("Quantity of %1$s updated to %2$s", cardName, quantity));
    }

    public void UpdateDeckQuantity(String cardName, String deckName, int quantity) {
        this.databaseManager.UpdateDeckQuantity(cardName, deckName, quantity);
        this.ShowMessageShort(this.mostRecentActivity, String.format("Quantity of %1$s updated to %2$s", cardName, quantity));
    }

    public boolean CreateDeck(String deckName) {
        return this.databaseManager.CreateDeck(deckName);
    }

    public boolean RemoveDeck(String deckName) {
        return this.databaseManager.DeleteDeck(deckName);
    }

    public void ShowMessageLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void ShowMessageShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void HideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void GoBack() {
        if (this.backStack.size() > 0) {
            this.backStack.pop();
        }
    }
}
