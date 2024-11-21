package oopp.team16.model.gameLogic;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<StdCard> hand;   //vart ger vi 7 kort, satte i konstruktorn?
    int score;  // hur räknar vi score
    String name;  //?

    public Player (String name){
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void drawCard(StdCard card){
        hand.add(card);
    }

    public void playCard(int index){
        hand.remove(index);
    }

    public List<StdCard> getHand(){
        return this.hand;
    }

    // boolean hasUno maybe? return true if size equals 1
    //boolean hasCards

    public boolean hasCards(){
        return hand.isEmpty();
    }

    public String getName(){
        return this.name;
    }


}
