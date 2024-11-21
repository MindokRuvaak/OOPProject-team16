package oopp.team16.model;

import java.util.Collection;
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
    private final int startingHandSize;

    public Game(Deck deck, int startingHandSize, Collection<Player> players) {
        this.players = new LinkedList<>();
        this.players.addAll(players);
        this.startingHandSize = startingHandSize;
        this.deck = deck;
        deck.shuffle();
        playedCards = new Stack<>();
    }

    public void init() {
        setUpGame();
        gameLoop();
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
            // check winner
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

}
