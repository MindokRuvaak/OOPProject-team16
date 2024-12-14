package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private int score;  // hur räknar vi score
    private String name;
    // turn taken info
    private boolean playedCard;
    private int cardsDrawn;
    private boolean takingTurn;

    public Player (String name){
        this.name = name;
        this.hand = new ArrayList<>();
        this.playedCard = false;
        this.cardsDrawn = 0;
        this.takingTurn = false;
    }

    public void drawCard(Card card){
        //TODO: ensure hand alsways sorted
        this.hand.add(card);
        this.cardsDrawn++;
    }

    public Card playCard(int index){
        playedCard = true;
        return this.hand.remove(index);
    }

    public Card[] getHand(){
        return this.hand.toArray(new Card[0]);
    }

    public Card getCard(int index){
        return this.hand.get(index);
    }

    public int getHandSize(){
        return this.hand.size();
    }

    public boolean hasUno(){
        return this.hand.size() == 1;
    }

    public boolean hasCards(){
        return !this.hand.isEmpty();
    }

    public String getName(){
        return this.name;
    }

    public boolean hasPlayedCard(){
        return this.playedCard;

    }

    public boolean drawnThree(){
        return this.cardsDrawn >= 3;
    }

    public void resetTurnInfo(){
        this.playedCard = false;
        this.cardsDrawn = 0;
    }

    public void startTurn() {
        resetTurnInfo();
        this.takingTurn = true;
    }

    public void endTurn() {
        this.takingTurn = false;
    }

    public boolean stillTakingTurn() {
        return this.takingTurn;
    }

    // returns string containing the name of player and numer of cards in their hand
    // seperated by a '':''
    @Override
    public String toString() {
        return getName() + ":" + getHandSize();
    }
}
