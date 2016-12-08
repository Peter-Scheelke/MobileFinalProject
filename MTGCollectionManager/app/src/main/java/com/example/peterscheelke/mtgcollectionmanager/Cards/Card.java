package com.example.peterscheelke.mtgcollectionmanager.Cards;

import java.util.ArrayList;

public class Card
{
    public String Name = "";

    public String ManaCost = "";

    public Double CMC = -1.0;

    public String CompleteType = "";

    public String Text = "";

    public String Power = "";

    public String Toughness = "";

    public ArrayList<String> Types = null;

    public ArrayList<String> Subtypes = null;

    public ArrayList<Color> Colors = null;

    public ArrayList<Color> ColorIdentity = null;

    public int DeckQuantity = 0;

    public int CollectionQuantity = 0;
}
