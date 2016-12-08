package com.example.peterscheelke.mtgcollectionmanager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

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

public class FragmentManagementSystem {

    private static FragmentManagementSystem uniqueInstance;

    private CardFragment cardFragment;
    private ListFragment listFragment;
    private SearchFragment searchFragment;
    private Fragment currentFragment;
    private MainActivity clientActivity;
    private DatabaseManager databaseManager;
    private UpdateFragment updateFragment;

    private Stack<Fragment> backStack;

    private FragmentManagementSystem(Context context, MainActivity clientActivity) throws IOException {
        this.cardFragment = new CardFragment();
        this.listFragment = new ListFragment();
        this.searchFragment = new SearchFragment();
        this.updateFragment = new UpdateFragment();
        this.currentFragment = this.searchFragment;

        this.clientActivity = clientActivity;
        DatabaseManager.InitializeManager(context);
        this.databaseManager = DatabaseManager.GetManager();
        this.backStack = new Stack<>();
    }

    static Fragment getCurrentFragment()
    {
        return uniqueInstance.currentFragment;
    }


    static void Initialize(Context context, MainActivity clientActivity) throws IOException {
        if (uniqueInstance == null)
        {
            uniqueInstance = new FragmentManagementSystem(context, clientActivity);
        }
    }

    public static void RequestCard(String name)
    {
        HideKeyboard();
        Card card = uniqueInstance.databaseManager.GetCardByName(name);
        uniqueInstance.cardFragment.SetCard(card);
        uniqueInstance.backStack.add(uniqueInstance.currentFragment);
        uniqueInstance.currentFragment = uniqueInstance.cardFragment;
        uniqueInstance.clientActivity.inform();
    }

    public static void RequestDeck(String name)
    {
        HideKeyboard();
        List<Tuple<String, Integer>> cards = uniqueInstance.databaseManager.GetDeck(name);
        uniqueInstance.backStack.add(uniqueInstance.currentFragment);
        uniqueInstance.listFragment.InitializeFragment(cards, "Cards", "Quantity");
        uniqueInstance.listFragment.setOnClickMode(true);

        uniqueInstance.currentFragment = uniqueInstance.listFragment;
        uniqueInstance.clientActivity.inform();
    }

    public static void RequestSearch(Card card)
    {
        HideKeyboard();
        List<Tuple<String, Integer>> cards = uniqueInstance.databaseManager.GetAllCardNames(card);
        uniqueInstance.listFragment.setOnClickMode(true);

        if (cards.size() == 1)
        {
            Card cardWithAllInfo = uniqueInstance.databaseManager.GetCardByName(cards.get(0).first);
            uniqueInstance.cardFragment.SetCard(cardWithAllInfo);
            uniqueInstance.backStack.add(uniqueInstance.currentFragment);
            uniqueInstance.currentFragment = uniqueInstance.cardFragment;
            uniqueInstance.clientActivity.inform();
            uniqueInstance.clientActivity.showToast("One Card Found");
        }
        else if (cards.size() > 1)
        {
            String header1;
            if (card.CollectionQuantity > 0)
            {
                header1 = "Collection";
            }
            else
            {
                header1 = "Card";
            }

            String header2 = "Quantity";
            uniqueInstance.listFragment.InitializeFragment(cards, header1, header2);
            uniqueInstance.backStack.add(uniqueInstance.currentFragment);
            uniqueInstance.currentFragment = uniqueInstance.listFragment;
            uniqueInstance.clientActivity.inform();
            uniqueInstance.clientActivity.showToast(Integer.toString(cards.size()) + " Cards Found");
        }
        else
        {
            uniqueInstance.clientActivity.showToast("No Cards Found");
        }
    }

    public static void RequestUpdate(String cardName) {
        Card card = uniqueInstance.databaseManager.GetCardByName(cardName);
        List<Tuple<String, Integer>> decksAndQuantities = uniqueInstance.databaseManager.GetDecksWithCardQuantities(cardName);
        uniqueInstance.updateFragment.initializeFragment(card.Name, decksAndQuantities, card.CollectionQuantity);
        uniqueInstance.backStack.add(uniqueInstance.currentFragment);
        uniqueInstance.currentFragment = uniqueInstance.updateFragment;
        uniqueInstance.clientActivity.inform();
    }

    public static void UpdateCollectionQuantity(String cardName, int quantity) {
        try {
            uniqueInstance.databaseManager.UpdateCollectionQuantity(cardName, quantity);
            String message = "Quantity of %1$s updated to %2$s.";
            message = String.format(message, cardName, quantity);
            uniqueInstance.clientActivity.showToast(message);
        } catch (Exception e) {
            e.printStackTrace();
            uniqueInstance.clientActivity.showToast("There was an error updating the database.");
        }
    }

    public static void UpdateDeckQuantity(String cardName, String deckName, int quantity) {
        try {
            uniqueInstance.databaseManager.UpdateDeckQuantity(cardName, deckName, quantity);
            String message = "Quantity of %1$s in %2$s updated to %3$s.";
            message = String.format(message, cardName, deckName, quantity);
            uniqueInstance.clientActivity.showToast(message);
        } catch (Exception e) {
            e.printStackTrace();
            uniqueInstance.clientActivity.showToast("There was an error updating the database.");
        }
    }


    static void RequestDecks()
    {
        HideKeyboard();
        List<Tuple<String, Integer>> decks = uniqueInstance.databaseManager.GetDecks();

        if (decks.size() == 0) {
            uniqueInstance.clientActivity.showToast("No Decks Found");
        }
        else {
            ListFragment decksFragment = new ListFragment();
            decksFragment.setOnClickMode(false);
            decksFragment.InitializeFragment(decks, "Deck", "Quantity");
            uniqueInstance.backStack.add(uniqueInstance.currentFragment);
            uniqueInstance.currentFragment = decksFragment;
            uniqueInstance.clientActivity.inform();

            if (decks.size() == 1)
            {
                uniqueInstance.clientActivity.showToast("One Deck Found");
            }
            else
            {
                uniqueInstance.clientActivity.showToast(Integer.toString(decks.size()) + " Decks Found");
            }
        }
    }

    static boolean GoBack()
    {
        boolean hasBackFrame = false;
        if (uniqueInstance.backStack.size() > 0) {
            uniqueInstance.currentFragment = uniqueInstance.backStack.pop();
            hasBackFrame = true;
        }

        uniqueInstance.clientActivity.inform();
        return hasBackFrame;
    }

    static void GoToHome()
    {
        uniqueInstance.backStack.clear();
        uniqueInstance.searchFragment = new SearchFragment();
        uniqueInstance.currentFragment = uniqueInstance.searchFragment;
        uniqueInstance.clientActivity.inform();
    }

    private static void HideKeyboard()
    {
        if (uniqueInstance.currentFragment.getView().getWindowToken() != null) {
            final InputMethodManager imm = (InputMethodManager) uniqueInstance.currentFragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(uniqueInstance.currentFragment.getView().getWindowToken(), 0);
        }

    }
}
