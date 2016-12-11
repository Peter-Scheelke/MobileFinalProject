package com.example.peterscheelke.mtgcollectionmanager.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peterscheelke.mtgcollectionmanager.BackendManager;
import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;
import com.example.peterscheelke.mtgcollectionmanager.Cards.Symbols;
import com.example.peterscheelke.mtgcollectionmanager.MainActivity;
import com.example.peterscheelke.mtgcollectionmanager.R;

import java.util.ArrayList;
import java.util.List;

public class CardFragment extends Fragment {

    private static Card templateCard = null;

    private static final String NAME_KEY = "NameKey";
    private static final String TEXT_KEY = "TextKey";
    private static final String COMPLETE_TYPE_KEY = "CompleteTypeKey";
    private static final String POWER_KEY = "Power_Key";
    private static final String TOUGHNESS_KEY = "ToughnessKey";
    private static final String MANA_COST_KEY = "ManaCostKey";
    private static final String CARD_IS_INITIALIZED_KEY = "CardIsInitializedKey";

    private  boolean cardInitialized = false;
    private Card card = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null)
        {
            this.cardInitialized = savedInstanceState.getBoolean(CARD_IS_INITIALIZED_KEY);
        }

        if (!cardInitialized)
        {
            card = templateCard;
            templateCard = null;
            cardInitialized = true;
        }
        else
        {
            this.restoreState(savedInstanceState);
        }

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

        Button button = (Button) getView().findViewById(R.id.updateQuantitiesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackendManager.GetManager().RequestCardUpdate(card.Name);
                Intent intent= new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        BackendManager.HideKeyboardFrom(getContext(), new View(getContext()));
    }

    public static void SetCard(Card card)
    {
        CardFragment.templateCard = card;
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

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        state.putString(NAME_KEY, this.card.Name);
        state.putString(TEXT_KEY, this.card.Text);
        state.putString(COMPLETE_TYPE_KEY, this.card.CompleteType);
        state.putString(POWER_KEY, this.card.Power);
        state.putString(TOUGHNESS_KEY, this.card.Toughness);
        state.putString(MANA_COST_KEY, this.card.ManaCost);
        state.putBoolean(CARD_IS_INITIALIZED_KEY, this.cardInitialized);
    }

    private void restoreState(Bundle state) {
        if (state != null) {
            this.card = new Card();
            this.card.Name = state.getString(NAME_KEY);
            this.card.Text = state.getString(TEXT_KEY);
            this.card.CompleteType = state.getString(COMPLETE_TYPE_KEY);
            this.card.Power = state.getString(POWER_KEY);
            this.card.Toughness = state.getString(TOUGHNESS_KEY);
            this.card.ManaCost = state.getString(MANA_COST_KEY);
        }
    }
}
