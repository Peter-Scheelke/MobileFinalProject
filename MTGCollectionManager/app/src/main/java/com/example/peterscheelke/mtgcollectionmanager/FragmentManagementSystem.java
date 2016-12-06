package com.example.peterscheelke.mtgcollectionmanager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.DatabaseManager;
import com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement.Tuple;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.CardFragment;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.ListFragment;
import com.example.peterscheelke.mtgcollectionmanager.Fragments.SearchFragment;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 * Created by Peter Scheelke on 12/4/2016.
 */

public class FragmentManagementSystem {

    private static FragmentManagementSystem uniqueInstance;

    private CardFragment cardFragment;
    private ListFragment listFragment;
    private SearchFragment searchFragment;
    private Fragment currentFragment;
    private MainActivity clientActivity;
    private Context context;
    private DatabaseManager databaseManager;

    private Stack<Fragment> backStack;

    private FragmentManagementSystem(Context context, MainActivity clientActivity) throws IOException {
        this.cardFragment = new CardFragment();
        this.listFragment = new ListFragment();
        this.searchFragment = new SearchFragment();
        this.currentFragment = this.searchFragment;

        this.context = context;
        this.clientActivity = clientActivity;
        DatabaseManager.InitializeManager(context);
        this.databaseManager = DatabaseManager.GetManager();
        this.backStack = new Stack<>();
    }

    public static Fragment getCurrentFragment()
    {
        return uniqueInstance.currentFragment;
    }


    public static void Initialize(Context context, MainActivity clientActivity) throws IOException {
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
            Toast.makeText(uniqueInstance.context, "One Card Found", Toast.LENGTH_SHORT).show();
        }
        else if (cards.size() > 1)
        {
            String header1 = "";
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
            Toast.makeText(uniqueInstance.context, Integer.toString(cards.size()) + " Cards Found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(uniqueInstance.context, "No Cards Found", Toast.LENGTH_LONG).show();
        }
    }

    public static void RequestDecks()
    {
        HideKeyboard();
        List<Tuple<String, Integer>> decks = uniqueInstance.databaseManager.GetDecks();

        if (decks.size() == 0) {
            Toast.makeText(uniqueInstance.context, "No Decks Found", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(uniqueInstance.context, "One Deck Found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(uniqueInstance.context, Integer.toString(decks.size()) + " Decks Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean GoBack()
    {
        boolean hasBackFrame = false;
        if (uniqueInstance.backStack.size() > 0) {
            uniqueInstance.currentFragment = uniqueInstance.backStack.pop();
            hasBackFrame = true;
        }

        uniqueInstance.clientActivity.inform();
        return hasBackFrame;
    }

    public static void GoToHome()
    {
        uniqueInstance.backStack.clear();
        uniqueInstance.searchFragment = new SearchFragment();
        uniqueInstance.currentFragment = uniqueInstance.searchFragment;
        uniqueInstance.clientActivity.inform();
    }

    public static void HideKeyboard()
    {
        final InputMethodManager imm = (InputMethodManager) uniqueInstance.currentFragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(uniqueInstance.currentFragment.getView().getWindowToken(), 0);
    }

}
