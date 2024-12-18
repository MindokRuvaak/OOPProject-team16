package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.GameLogic;
import oopp.team16.model.gameLogic.GameRules;
import oopp.team16.model.gameLogic.Player;

class Game {

    private final ArrayList<GameListener> listeners;
    private final LinkedList<Player> players;
    private Iterator<Player> turnOrder;
    private Player currentPlayer;
    private final Deck deck;
    private final Stack<Card> playedCards;
    private final int startingHandSize;
    // private GameLogic gamelogic; // can be final? Unnecessary?
    // alot of overlap between GameLogic and GameRules

    Game(Deck deck, int startingHandSize) {
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

    void startGame() {// TODO: rename to startGameLoop
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

    LinkedList<Player> getPlayers() {
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
            nextTurn(); // setup first player, after turn order iterator is initiaized, call next to
                        // setup first player in list as first current player
            startTurn();
            while (this.currentPlayer.stillTakingTurn()) {
                reUpDeck();
                takeTurn();
            }
            noWinner = !checkWinner();
        }
    }

    private void reUpDeck() {
        if (deck.isEmpty()) {
            Card top = playedCards.pop();
            deck.add(playedCards);
            playedCards.empty();
            playedCards.add(top);
        }
    }

    boolean checkWinner() {
        boolean haveWinner = false;
        if (!currentPlayer.hasCards()) {
            haveWinner = true;
            announceWinner(currentPlayer.getName());
        }
        return haveWinner;
    }

    void startTurn() {
        this.currentPlayer.startTurn();
        for (GameListener listener : listeners) {
            listener.startPlayerTurn();
        }
    }

    void endTurn() {
        this.currentPlayer.resetTurnInfo();
        this.currentPlayer.endTurn();
    }

    private void announceWinner(String name) {
        for (GameListener listener : listeners) {
            listener.announceWinner(name);
        }
    }

    void nextTurn() {
        if (!this.turnOrder.hasNext()) {// not hasNext => current is last player
            this.turnOrder = this.players.iterator(); // reset iterator
        }
        this.currentPlayer = this.turnOrder.next();// get next
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
            listener.takePlayerTurn();
        }
    }

    void AddListener(GameListener gameListener) {
        listeners.add(gameListener);
    }

    void tryPlay(int index) {
        if (!canEndTurn()) {
            tryPlayCard(index);
        } else {
            tryPlayMoreCards(index);
        }
    }

    private void tryPlayCard(int index) {
        if ((0 <= index && index < currentPlayer.getHandSize())
                && GameRules.allowedPlay(currentPlayer.getCard(index),
                        getTopPlayedCard())) {
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
        if (canEndTurn()) {
            endTurn();
        } else {
            announceMustPlayCard();
        }
    }

    boolean canEndTurn() {
        return currentPlayer.hasPlayedCard() || currentPlayer.drawnThree();
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
