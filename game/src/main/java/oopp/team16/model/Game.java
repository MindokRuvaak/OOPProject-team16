package oopp.team16.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import oopp.team16.model.gameLogic.Card;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;

public class Game {

    private final ArrayList<GameListener> listeners;
    private final LinkedList<Player> players;
    private Player currentPlayer; 
    private final Deck deck;
    private final Stack<Card> playedCards;
    private final int startingHandSize;

    public Game(Deck deck, int startingHandSize) {
        this.listeners = new ArrayList<>();
        this.players = new LinkedList<>();
        this.startingHandSize = startingHandSize;
        this.deck = deck;
        deck.shuffle();
        playedCards = new Stack<>();
    }

    public void init(Collection<Player> players) {
        this.players.addAll(players);
        setUpGame();
        gameLoop();
    }

    public String getCurrentPlayerID() {
        return currentPlayer.getName();
    }

    public Card getTopPlayedCard() {
        return playedCards.peek();
    }

    public String getTopPlayedCardString() {
        return getTopPlayedCard().toString();
    }

    private void gameLoop() {
        boolean noWinner = true;
        Iterator<Player> turnOrder = players.iterator();
        currentPlayer = turnOrder.next();

        while (noWinner) {
            // takeTurn(currentPlayer);

            // if (reverse()) {
            //     turnOrder = players.descendingIterator();
            // }
            // check winner noWinner = ...
            this.currentPlayer = turnOrder.next();
            notifyListeners();
        }
    }

    private void setUpGame() {
        givePLayersCards(startingHandSize);// give all players a starting hand
        playedCards.add(deck.drawCard());// add one card to start
    }

    private void givePLayersCards(int n) {
        for (int i = 0; i < n; i++) {
            givePlayersCard();
        }
    }

    private void givePlayersCard() {
       for (Player p : players) {
        p.drawCard(deck.drawCard());
       }
    }

    private void takeTurn(Player currentPlayer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTurn'");
    }

    private boolean reverse() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reverse'");
    }

    public void notifyListeners(){
        for (GameListener listener : listeners) {
            listener.update();
        }
    }
}
