package com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement;

import android.support.annotation.NonNull;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter Scheelke on 12/2/2016.
 */

class QueryFactory
{
    // Table names
    private static final String CARDS = "Cards";
    private static final String TYPES = "Types";
    private static final String SUBTYPES = "Subtypes";
    private static final String COLORS = "Colors";
    private static final String COLOR_IDENTITIES = "ColorIdentities";

    // Columns
    private static final String CARD_COLUMNS = "Name";

    // Query strings
    private static final String BASE_QUERY = "SELECT DISTINCT %1$s FROM %2$s";
    private static final String JOIN_QUERY = "SELECT DISTINCT %1$s FROM (%2$s) AS temp JOIN %3$s ON Card = Name";
    private static final String SELECT_QUERY = "SELECT DISTINCT %1$s FROM %2$s WHERE %3$s = ?";


    // JOIN for colors
    // SELECT Layout, Name, ManaCost, CMC, CompleteType, Text, Power, Toughness, ImageName, %0$s FROM %1$s AS cards JOIN %2$s ON Card = Name;

    public QueryFactory()
    {
    }

    public Query CreateQuery(Card card, boolean fromCollection)
    {
        String columns = CARD_COLUMNS;
        String queryString = String.format(BASE_QUERY, columns, CARDS);

        List<String> searches = new ArrayList<>();
        List<String> parameters = new ArrayList<>();

        queryString = GetQueryFromCardArgs(card, queryString, searches, parameters);
        queryString = getQueryFromJoins(card, columns, queryString, parameters);

        Query query = new Query();
        query.query = queryString + ";";
        query.parameters = parameters.toArray(new String[0]);
        return query;
    }

    @NonNull
    private String GetQueryFromCardArgs(Card card, String queryString, List<String> searches, List<String> parameters) {
        if (card != null) {
            if (card.Name != "") {
                searches.add("Name LIKE ?");
                parameters.add("%" + card.Name + "%");
            }

            if (card.ManaCost != "") {
                searches.add("ManaCost = ?");
                parameters.add(card.ManaCost);
            }

            if (card.CMC >= 0.0) {
                searches.add("CMC = ?");
                parameters.add(card.CMC.toString());
            }

            if (card.CompleteType != "") {
                searches.add("CompleteType LIKE ?");
                parameters.add("%" + card.CompleteType + "%");
            }

            if (card.Text != "") {
                searches.add("Text LIKE ?");
                parameters.add("%" + card.Text + "%");
            }

            if (card.Power != "") {
                searches.add("Power = ?");
                parameters.add(card.Power);
            }

            if (card.Toughness != "") {
                searches.add("Toughness = ?");
                parameters.add(card.Toughness);
            }

            boolean hasWhere = false;
            StringBuilder whereClause = new StringBuilder();
            for (String search : searches) {
                if (!hasWhere) {
                    hasWhere = true;
                    whereClause.append(" WHERE ");
                } else {
                    whereClause.append(" AND ");
                }

                whereClause.append(search);
            }

            queryString += whereClause.toString();
        }
        return queryString;
    }

    private String getQueryFromJoins(Card card, String columns, String queryString, List<String> parameters) {
        if (card != null)
        {
            if (card.Types != null && card.Types.size() > 0)
            {
                queryString = String.format(JOIN_QUERY, columns, queryString, TYPES);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "Types", "Type") + ")";
                parameters.add(card.Types.get(0));
                for (int i = 1; i < card.Types.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "Types", "Type") + ")";
                    parameters.add(card.Types.get(i));
                }
            }

            if (card.Subtypes != null && card.Subtypes.size() > 0)
            {
                queryString = String.format(JOIN_QUERY, columns, queryString, SUBTYPES);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "Subtypes", "Subtype") + ")";
                parameters.add(card.Subtypes.get(0));
                for (int i = 1; i < card.Subtypes.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "Subtypes", "Subtype") + ")";
                    parameters.add(card.Subtypes.get(i));
                }
            }

            if (card.Colors != null && card.Colors.size() > 0)
            {
                queryString = String.format(JOIN_QUERY, columns, queryString, COLORS);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "Colors", "Color", card.Colors.get(0).toString()) + ")";
                parameters.add(card.Colors.get(0).toString());
                for (int i = 1; i < card.Colors.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "Colors", "Color", card.Colors.get(i).toString()) + ")";
                    parameters.add(card.Colors.get(i).toString());
                }
            }

            if (card.ColorIdentity != null)
            {
                queryString = String.format(JOIN_QUERY, columns, queryString, COLOR_IDENTITIES);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "ColorIdentities", "ColorIdentity", card.ColorIdentity.get(0).toString()) + ")";
                parameters.add(card.ColorIdentity.get(0).toString());
                for (int i = 1; i < card.ColorIdentity.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "ColorIdentities", "ColorIdentity", card.ColorIdentity.get(i).toString()) + ")";
                    parameters.add(card.ColorIdentity.get(i).toString());
                }
            }
        }
        return queryString;
    }

    public Query CreateNonQuery() { return null;}

}
