package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the game.
 * Keeps track of the player's hand, score and turn status.
 */
public class Player {
    private List<Card> hand;
    private int score;  // hur räknar vi score
    private final int id;
    // turn taken info
    private boolean playedCard;
    private int cardsDrawn;
    private boolean takingTurn;

    public Player (int id){
        this.id = id;
        this.hand = new ArrayList<>();
        this.playedCard = false;
        this.cardsDrawn = 0;
        this.takingTurn = false;
    }

    public void drawCard(Card card){
        //TODO: ensure hand alsways sorted !! later if have time
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

    public int getHandValue() {
        int value = 0;
        for (Card card : hand) {
            value += card.getValue();
        }
        return value;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Player " + id + " - Cards: " + getHandSize();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        return id == other.id;
    }
}
