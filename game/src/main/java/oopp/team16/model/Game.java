package oopp.team16.model;

import java.util.LinkedList;
import java.util.List;

import oopp.team16.model.gameLogic.Card;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;

public class Game {

    private final LinkedList<Player> players;
    // private int currentPlayer; // might not be necessary?
    private final Deck deck;
    private final Deck playedCards;

    public Game(){
        players = new LinkedList<>();
        deck = new Deck(false);
        deck.shuffle();
        playedCards = new Deck(true);
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
        Card c = playedCards.drawCard();
        playedCards.add(c);
        return c;
    }

    public String getTopPlayedCardString() {
        Card c = playedCards.drawCard();
        playedCards.add(c);
        return c.toString();
    }

    public void initializeGame(){
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            for (int j = 0; j < 7; j++) {
            }
        }

    }

    private void gameLoop() {
        Player currentPlayer = players.getFirst();
        while (true) {
            // players.push(currentPlayer);

        }
    }

}
