package oopp.team16.model;

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

    public Game(Deck deck){
        players = new LinkedList<>();
        this.deck = deck;
        deck.shuffle();
        playedCards = new Stack<>();
    }


    public void createPlayer(String id){
        players.add(new Player(id));
    }

    public void init() {
        ///give all players 7 cards each
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
        Player currentPlayer = players.getFirst();
        while (true) {
            // players.push(currentPlayer);

            
        }
    }

}
