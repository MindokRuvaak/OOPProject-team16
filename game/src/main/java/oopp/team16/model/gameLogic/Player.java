package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Card> hand;
    int score;  // hur räknar vi score
    String name;  //?

    public Player (String name){
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void drawCard(Card card){
        //TODO: sorted hand
        hand.add(card);
    }

    public Card playCard(int index){
        return hand.remove(index);
    }

    public Card[] getHand(){
        return this.hand.toArray(new Card[0]);
    }

    public int getHandSize(){
        return this.hand.size();
    }

    public boolean hasUno(){
        return hand.size() == 1;
    }

    public boolean hasCards(){
        return !hand.isEmpty();
    }

    public String getName(){
        return this.name;
    }


}
