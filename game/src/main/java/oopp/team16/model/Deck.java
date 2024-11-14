package oopp.team16.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Deck {

    List<Card> deck;   // can make stack

    public Deck(){
        this.deck = CreateDeck.createUnoDeck();
    }

    public Card drawCard(){
        return deck.remove(deck.size() - 1);
}

    public int getSize() {
        return deck.size();
    }

    //shuffle method?, shuffle in constructor?



}
