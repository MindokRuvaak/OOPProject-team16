package oopp.team16.model;

import java.util.*;

public class Deck {

    Stack<Card> deck;

    public Deck(){
        this.deck = CreateDeck.createUnoDeck();
        Collections.shuffle(deck);
    }

    public Card drawCard(){
        return deck.pop();
}

    public int getSize() {
        return deck.size();
    }

    //shuffle method?, shuffle in constructor?



}
