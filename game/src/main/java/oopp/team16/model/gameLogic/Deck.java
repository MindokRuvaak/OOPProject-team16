package oopp.team16.model.gameLogic;

import java.util.Stack;

public class Deck {

    Stack<Card> cardStack;
    
    private Deck(){
        cardStack = new Stack<>();
    }

    public Deck(boolean createEmpty){
        this();
        if (createEmpty){
            // create an empty deck
        }
        else {
            // create normal deck
            
        }
    }

    public Card[] draw(int n){
        Card[] drawn = new Card[n];
        // draw n cards from deck
        for (int i = 0; i < n; i++) {
            drawn[i] = drawOne();
        }
        return drawn;
    }

    public Card drawOne() {
        // TODO Auto-generated method stub
        // draw one card from deck
        // throw new UnsupportedOperationException("Unimplemented method 'draw'");
        return new Card();//temporary
    }

    public void add(Card c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }
    
    public void add(Deck d) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    public void shuffle(){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shuffle'");
    }

    
}