package oopp.team16.model.gameLogic;

import java.util.Collections;
import java.util.Stack;

import oopp.team16.model.gameLogic.Cards.Card;

public class Deck {
    Stack<Card> cardStack;


    private Deck(){
        cardStack = new Stack<>();
    }

    Deck(Stack<Card> stack){
        this();
        cardStack.addAll(stack);
    }


    public Card drawCard(){
        return cardStack.pop();
}

    public int getSize() {
        return cardStack.size();
    }

    public void add(Card c) {
        cardStack.push(c);
    }

    public void add(Stack<Card> s) {
        cardStack.addAll(s);
        this.shuffle();
    }

    public void shuffle(){
        Collections.shuffle(cardStack);
    }

    public boolean isEmpty(){
        return cardStack.isEmpty();
    }


}
