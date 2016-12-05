package com.example.peterscheelke.mtgcollectionmanager;

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

import java.io.IOException;
import java.util.List;

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

    private FragmentManagementSystem(Context context, MainActivity clientActivity) throws IOException {
        this.cardFragment = new CardFragment();
        this.listFragment = new ListFragment();
        this.searchFragment = new SearchFragment();
        this.currentFragment = this.searchFragment;

        this.context = context;
        this.clientActivity = clientActivity;
        DatabaseManager.InitializeManager(context);
        this.databaseManager = DatabaseManager.GetManager();
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
        Card card = uniqueInstance.databaseManager.GetCardByName(name);
        uniqueInstance.cardFragment.SetCard(card);
        uniqueInstance.currentFragment = uniqueInstance.cardFragment;
        uniqueInstance.clientActivity.inform();
    }

    public static void RequestSearch(Card card)
    {
        List<Tuple<String, Integer>> cards = uniqueInstance.databaseManager.GetAllCardNames(card);

        if (cards.size() == 1)
        {

            Card cardWithAllInfo = uniqueInstance.databaseManager.GetCardByName(cards.get(0).first);
            uniqueInstance.cardFragment.SetCard(cardWithAllInfo);
            uniqueInstance.currentFragment = uniqueInstance.cardFragment;
            uniqueInstance.clientActivity.inform();
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
                header1 = "Cards";
            }

            String header2 = "Quantity";
            uniqueInstance.listFragment.InitializeFragment(cards, header1, header2);
            uniqueInstance.currentFragment = uniqueInstance.listFragment;
            uniqueInstance.clientActivity.inform();
        }
        else
        {
            Toast.makeText(uniqueInstance.context, "No Cards Found", Toast.LENGTH_LONG).show();
        }
    }
}
