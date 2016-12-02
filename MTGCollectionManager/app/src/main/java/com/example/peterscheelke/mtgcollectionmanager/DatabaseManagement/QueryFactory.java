package com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement;

import com.example.peterscheelke.mtgcollectionmanager.Cards.Card;

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
    private static final String CARD_COLUMNS = "Layout, Name, ManaCost, CMC, CompleteType, Text, Power, Toughness, ImageName";

    // Query strings
    private static final String BASE_QUERY = "SELECT %1$s FROM %2$s";
    private static final String JOIN_QUERY = "SELECT %1$s FROM (%2$s) AS temp JOIN %3$s ON Card = Name";
    private static final String SELECT_QUERY = "SELECT %1$s FROM %2$s WHERE %3$s = '%4$s'";


    // JOIN for colors
    // SELECT Layout, Name, ManaCost, CMC, CompleteType, Text, Power, Toughness, ImageName, %0$s FROM %1$s AS cards JOIN %2$s ON Card = Name;

    public QueryFactory()
    {
    }

    public Query CreateQuery(Card card, boolean fromCollection)
    {
        String columns = CARD_COLUMNS;
        String queryString = String.format(BASE_QUERY, columns, CARDS);

        if (card != null)
        {
            if (card.Types != null && card.Types.size() > 0)
            {
                columns += ", Type";
                queryString = String.format(JOIN_QUERY, columns, queryString, TYPES);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "Types", "Type", card.Types.get(0)) + ")";
                for (int i = 1; i < card.Types.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "Types", "Type", card.Types.get(i)) + ")";
                }
            }

            // private static final String SELECT_QUERY = "SELECT %1$s FROM %2$s WHERE %3$s = '%4$s'";
            if (card.Subtypes != null && card.Subtypes.size() > 0)
            {
                columns += ", Subtype";
                queryString = String.format(JOIN_QUERY, columns, queryString, SUBTYPES);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "Subtypes", "Subtype", card.Subtypes.get(0)) + ")";
                for (int i = 1; i < card.Subtypes.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "Subtypes", "Subtype", card.Subtypes.get(i)) + ")";
                }
            }

            if (card.Colors != null && card.Colors.size() > 0)
            {
                columns += ", Color";
                queryString = String.format(JOIN_QUERY, columns, queryString, COLORS);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "Colors", "Color", card.Colors.get(0).toString()) + ")";
                for (int i = 1; i < card.Colors.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "Colors", "Color", card.Colors.get(i).toString()) + ")";
                }
            }

            if (card.ColorIdentity != null)
            {
                columns += ", ColorIdentity";
                queryString = String.format(JOIN_QUERY, columns, queryString, COLOR_IDENTITIES);
                queryString += " WHERE Name IN (" + String.format(SELECT_QUERY, "Card", "ColorIdentities", "ColorIdentity", card.ColorIdentity.get(0).toString()) + ")";
                for (int i = 1; i < card.ColorIdentity.size(); ++i)
                {
                    queryString += " AND Name IN (" + String.format(SELECT_QUERY, "Card", "ColorIdentities", "ColorIdentity", card.ColorIdentity.get(i).toString()) + ")";
                }
            }
        }

        Query query = new Query();
        query.query = queryString + ";";
        return query;
    }

    public Query CreateNonQuery() { return null;}

}
