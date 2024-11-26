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
        hand.add(card);
    }

    public void playCard(int index){
        hand.remove(index);
    }

    public List<Card> getHand(){
        return this.hand;
    }

    public int getHandSize(){
        return this.hand.size();
    }

    public boolean hasUno(){
        return hand.size() == 1;
    }

    public boolean hasCards(){
        return hand.isEmpty();
    }

    public String getName(){
        return this.name;
    }


}
