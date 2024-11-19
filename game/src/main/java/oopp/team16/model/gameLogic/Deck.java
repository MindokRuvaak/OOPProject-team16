package oopp.team16.model.gameLogic;

import java.util.Collections;
import java.util.Stack;

import oopp.team16.model.gameLogic.Colors.Red;

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

    public Card drawCard(){
        return cardStack.pop();
}

    public int getSize() {
        return cardStack.size();
    }

    public Card drawOne() {
        // TODO Auto-generated method stub
        // draw one card from deck
        // throw new UnsupportedOperationException("Unimplemented method 'draw'");
        return new StdCard(Red.getColor(), Value.NINE);//temporary
    }

    public void add(Card c) {
        cardStack.push(c);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    public void add(Deck d) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    public void shuffle(){
        Collections.shuffle(cardStack);
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'shuffle'");
    }


}
