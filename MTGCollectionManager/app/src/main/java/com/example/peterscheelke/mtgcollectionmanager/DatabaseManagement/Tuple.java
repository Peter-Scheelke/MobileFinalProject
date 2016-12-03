package com.example.peterscheelke.mtgcollectionmanager.DatabaseManagement;

/**
 * Created by Peter Scheelke on 12/3/2016.
 */

// http://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java
public class Tuple<X, Y> {
    public final X first;
    public final Y last;
    public Tuple(X first, Y last) {
        this.first = first;
        this.last = last;
    }
}