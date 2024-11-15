package oopp.team16.model.gameLogic;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Card> hand;
    String id;

    public Player(String id) {
        this.hand = new ArrayList<>();
        this.id = id;
    }

    public void add(Card c){
        hand.add(c);
    }
    
    public String getPlayerID(){
        return id;
    }
}