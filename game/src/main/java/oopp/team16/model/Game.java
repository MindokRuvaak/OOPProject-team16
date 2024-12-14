package oopp.team16.model;

import java.util.*;

import javafx.application.Platform;
import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.GameLogic;
import oopp.team16.model.gameLogic.GameRules;
import oopp.team16.model.gameLogic.Player;

public class Game {

    private final ArrayList<GameListener> listeners;
    private final LinkedList<Player> players;
    private Iterator<Player> turnOrder;
    private Player currentPlayer;
    private final Deck deck;
    private final Stack<Card> playedCards;
    private final int startingHandSize;
    private GameLogic gamelogic; // can be final? Unnecessary?
    // alot of overlap between GameLogic and GameRules

    public Game(Deck deck, int startingHandSize) {
        this.listeners = new ArrayList<>();
        this.players = new LinkedList<>();
        this.startingHandSize = startingHandSize;
        this.deck = deck;
        this.deck.shuffle();
        playedCards = new Stack<>();
    }

    void init(Collection<Player> players) {
        this.players.addAll(players);
        this.turnOrder = players.iterator();
        setUpGame();
    }

    void startGame() {
        gameLoop();
    }

    void start() {
        if (currentPlayer == null) {
            nextTurn();
            startTurn();
            reUpDeck();
            takeTurn();
        }
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    Card getTopPlayedCard() {
        return playedCards.peek();
    }

    Card[] getPlayerHand() {
        return this.currentPlayer.getHand();
    }

    // Main game loop,
    private void gameLoop() {
        boolean noWinner = true;
        while (noWinner) {
            nextTurn(); // switch current player
            startTurn();
            while (this.currentPlayer.stillTakingTurn()) {
                reUpDeck();
                takeTurn();
            }
            endTurn();
            noWinner = checkWinner();
        }
    }

    private void reUpDeck() {
        if(deck.isEmpty()){
            Card  top = playedCards.pop();
            deck.add(playedCards);
            playedCards.empty();
            playedCards.add(top);
        }
    }

    private boolean checkWinner() {
        boolean noWinner = true;
        if (!currentPlayer.hasCards()) {
            noWinner = false;
            announceWinner(currentPlayer.getName());
        }
        return noWinner;
    }

    private void startTurn() {
        this.currentPlayer.startTurn();
        for (GameListener listener : listeners) {
            listener.startPlayerTurn();
        }
    }

    private void endTurn() {
        this.currentPlayer.resetTurnInfo();
    }

    private void announceWinner(String name) {
        for (GameListener listener : listeners) {
            listener.announceWinner(name);
        }
    }

    private void nextTurn() {
        if (!this.turnOrder.hasNext()) {// not hasNext => current is last player
            this.turnOrder = this.players.iterator(); // reset iterator
        }
        this.currentPlayer = this.turnOrder.next();
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

    private void takeTurn() {
        for (GameListener listener : listeners) {
            listener.takePlayerTurn(currentPlayer);
        }

    }

    void AddListener(GameListener gameListener) {
        listeners.add(gameListener);
    }

    void tryPlay(int index) {
        if (!currentPlayer.hasPlayedCard()) {
            tryPlayCard(index);
        } else {
            tryPlayMoreCards(index);
        }
    }

    private void tryPlayCard(int index) {
        if (GameRules.allowedPlay(currentPlayer.getCard(index), getTopPlayedCard())) {
            playCard(index);
        } else {
            announceBadMove();
        }
    }

    private void playCard(int index) {
        playedCards.add(currentPlayer.playCard(index));
    }

    private void announceBadMove() {
        for (GameListener listener : listeners) {
            listener.badMove();
        }
    }

    void currentPlayerDrawCard() {
        currentPlayer.drawCard(deck.drawCard());

    }

    void endCurrentPlayerTurn() {
        if (currentPlayer.hasPlayedCard()) {// TODO: can end turn if drawn 3 cards
            currentPlayer.endTurn();
        } else {
            announceMustPlayCard();
        }
    }

    private void announceMustPlayCard() {
        for (GameListener listener : listeners) {
            listener.announceMustPlayCard();
        }
    }

    void tryPlayMoreCards(int index) {
        if (GameRules.stackable(currentPlayer.getCard(index), getTopPlayedCard())) {
            playCard(index);
        } else {
            announceBadMove();
        }
    }
}
