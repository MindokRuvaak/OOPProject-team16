package oopp.team16.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;

class PlayerHandler {
    private final LinkedList<Player> players;
    private ListIterator<Player> turnOrder;
    private Player currentPlayer;

    PlayerHandler() {
        this.players = new LinkedList<>();
    }

    void init(Collection<Player> players) {
        this.players.addAll(players);
        this.turnOrder = this.players.listIterator();
    }

    LinkedList<Player> getPlayers() {
        return this.players;
    }

    Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    Card[] currentPlayerHand() {
        return this.currentPlayer.getHand();
    }

    boolean playerStillTakingTurn() {
        return this.currentPlayer.stillTakingTurn();
    }

    boolean playerHandEmpty() {
        return !this.currentPlayer.hasCards();
    }

    String playerName() {
        return currentPlayer.getName();
    }

    int playerScore() {
        return currentPlayer.getScore();
    }

    void calculateWinningScore() {
        int score = 0;
        for (Player player : players) {
            score += player.getHandValue();
        }
        currentPlayer.setScore(score);
    }

    public void startPlayerTurn() {
        currentPlayer.startTurn();
    }

    public void endPlayerTurn() {
        this.currentPlayer.resetTurnInfo();
        this.currentPlayer.endTurn();
    }

    public void nextTurn() {
        if (!this.turnOrder.hasNext()) {// not hasNext => current is last player
            this.turnOrder = this.players.listIterator(); // reset iterator
        }
        this.currentPlayer = this.turnOrder.next();// get next
    }

    public boolean firstPlayedCard() {
        return !currentPlayer.hasPlayedCard();
    }

    public int playerHandSize() {
        return currentPlayer.getHandSize();
    }

    public Card getPlayerCard(int index) {
        return currentPlayer.getCard(index);
    }

    public Card playCard(int index) {
        return currentPlayer.playCard(index);
    }

    public void playerDrawCard(Card drawCard) {
        currentPlayer.drawCard(drawCard);
    }

    public boolean canEndTurn() {
        return currentPlayer.hasPlayedCard() || currentPlayer.drawnThree();
    }

    public void reverseTurn() {
        int currentPlayerIndex = players.indexOf(currentPlayer);// Get the index of the current player before reversing
        Collections.reverse(players); // Reverse the list
        int newCurrentPlayerIndex = players.size() - 1 - currentPlayerIndex; // Calculate the new position of the
                                                                             // current player
        turnOrder = players.listIterator(newCurrentPlayerIndex); // Set the iterator to start right after the current
                                                                 // player
        this.currentPlayer = turnOrder.next(); // Ensure the current player plays again
    }

    public void nextPlayerDraws(Card[] cards) {
        int nextPlayerIndex = (players.indexOf(currentPlayer) + 1) % players.size();

        Player nextPlayer = players.get(nextPlayerIndex);
        for (Card c : cards) {
            nextPlayer.drawCard(c);
        }
    }

}
