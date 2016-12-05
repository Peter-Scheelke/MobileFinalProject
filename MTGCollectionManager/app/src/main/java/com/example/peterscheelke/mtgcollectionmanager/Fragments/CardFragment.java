package com.example.peterscheelke.mtgcollectionmanager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.Cards.Symbols;
import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Scheelke on 12/4/2016.
 */

public class CardFragment extends Fragment {

    private static Card card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ImageView imageView = (ImageView) getView().findViewById(R.id.whiteCheckboxBackground);
        //imageView.setImageResource(R.drawable.whitemana);

        if (card != null)
        {
            TextView name = (TextView) getView().findViewById(R.id.cardName);
            name.setText(card.Name);

            TextView completeType = (TextView) getView().findViewById(R.id.completeType);
            completeType.setText(card.CompleteType);

            TextView text = (TextView) getView().findViewById(R.id.cardText);
            text.setText(card.Text);

            TextView stats = (TextView) getView().findViewById(R.id.powerToughness);
            if (card.Power != null && card.Toughness != null) {
                stats.setText(card.Power + "/" + card.Toughness);
            }
            else
            {
                stats.setText("");
            }

            addManaCost();
        }

    }

    public void SetCard(Card card)
    {
        CardFragment.card = card;
    }

    private void addManaCost(){
        LinearLayout manaCost = (LinearLayout) getView().findViewById(R.id.manaCost);
        List<String> symbols = getColorStrings(card.ManaCost);

        for (String symbol : symbols) {
            int id = Symbols.getIdFromSymbol(symbol);

            if (id != 0) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                imageView.setImageResource(id);
                manaCost.addView(imageView);

                imageView.getLayoutParams().height = 55;
                imageView.getLayoutParams().width = 55;
            }
        }
    }

    private List<String> getColorStrings(String manaString)
    {
        List<String> symbols = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        if (manaString != null)
        {
            for (int i = 0; i < manaString.length(); ++i)
            {
                builder.append(manaString.charAt(i));
                if (manaString.charAt(i) == '}')
                {
                    symbols.add(builder.toString());
                    builder.setLength(0);
                }
            }
        }

        return symbols;
    }
}
