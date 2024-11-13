package oopp.team16.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Card> hand = new ArrayList<>();    //vart ger vi 7 kort
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




}
