package oopp.team16.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import oopp.team16.model.gameLogic.Card;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;

public class Game {

    private final LinkedList<Player> players;
    // private int currentPlayer; // might not be necessary?
    private final Deck deck;
    private final Stack<Card> playedCards;

    public Game(Deck deck) {
        players = new LinkedList<>();
        this.deck = deck;
        deck.shuffle();
        playedCards = new Stack<>();
    }

    public void createPlayer(String id) {
        players.add(new Player(id));
    }

    public void init() {
        /// give all players 7 cards each
        playedCards.add(deck.drawCard());
        // gameLoop();
    }

    public String getCurrentPlayerID() {
        return "player";
    }

    public Card getTopPlayedCard() {
        Card c = playedCards.peek();
        playedCards.add(c);
        return c;
    }

    public String getTopPlayedCardString() {
        return getTopPlayedCard().toString();
    }

    private void gameLoop() {
        boolean noWinner = true;
        Player currentPlayer = players.getFirst();
        Iterator<Player> turnOrder = players.iterator();
        while (noWinner) {
            takeTurn(currentPlayer);

            if (reverse()) {
                turnOrder = players.descendingIterator();
            }
            currentPlayer = turnOrder.next();
            //check winner
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

}
