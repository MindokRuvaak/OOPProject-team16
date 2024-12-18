package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.SpecialCard;
import oopp.team16.model.gameLogic.Cards.Colors.Color;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.GameRules;
import oopp.team16.model.gameLogic.Player;

public class Game implements SpecialCardLogic {

    private final ArrayList<GameListener> listeners;
    private final Deck deck;
    private final Stack<Card> playedCards;
    private final int startingHandSize;
    private int toSkip;
    private final DeckHandler dh;
    private final PlayerHandler ph;

    Game(Deck deck, int startingHandSize) {
        this.listeners = new ArrayList<>();
        this.startingHandSize = startingHandSize;
        this.deck = deck;
        this.deck.shuffle();
        this.toSkip = 0;
        playedCards = new Stack<>();

        this.dh = new DeckHandler(deck, playedCards);
        this.ph = new PlayerHandler();
    }

    void init(Collection<Player> players) {
        ph.init(players);
        setUpGame();
    }

    void startGameLoop() {
        gameLoop();
    }

    void start() {
        if (ph.getCurrentPlayer() == null) {
            nextTurn();
            startTurn();
            reUpDeck();
            takeTurn();
        }
    }

    LinkedList<Player> getPlayers() {
        return ph.getPlayers();
    }

    Player getCurrentPlayer() {
        return ph.getCurrentPlayer();
    }

    Card getTopPlayedCard() {
        return playedCards.peek();
    }

    Card[] getPlayerHand() {
        return ph.currentPlayerHand();
    }

    // Main game loop,
    private void gameLoop() {
        boolean noWinner = true;
        while (noWinner) {
            nextTurn(); // setup first player, after turn order iterator is initiaized, call next to
                        // setup first player in list as first current player
            startTurn();
            while (ph.playerStillTakingTurn()) {
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
        if (!ph.playerHandEmpty()) {
            ph.calculateWinningScore();
            announceWinner(ph.playerName(), ph.playerScore());
            haveWinner = true;
        }
        return haveWinner;
    }


    void startTurn() {
        for (int i = 0; i < toSkip; i++) {
            nextTurn();
        }
        toSkip = 0;
        ph.startPlayerTurn();
        for (GameListener listener : listeners) {
            listener.startPlayerTurn();
        }
    }

    void endTurn() {
        ph.endPlayerTurn();
    }

    private void announceWinner(String name, int score) {
        for (GameListener listener : listeners) {
            listener.announceWinner(name, score);
        }
    }

    public void nextTurn() {
        ph.nextTurn();
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
        ph.givePlayersCard(this.deck);
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
        if (ph.firstPlayedCard()) {
            tryPlayCard(index);
        } else {
            tryPlayMoreCards(index);
        }
    }

    private void tryPlayCard(int index) {
        if ((0 <= index && index < ph.playerHandSize())
                && GameRules.allowedPlay(ph.getPlayerCard(index),
                        getTopPlayedCard())) {
            playCard(index);
        } else {
            announceBadMove();
        }
    }

    private void playCard(int index) {
        Card card = ph.playCard(index);
        playedCards.add(card);

        if (card instanceof SpecialCard) {
            ((SpecialCard) card).getAction().executeAction(this);
        }
    }

    private void announceBadMove() {
        for (GameListener listener : listeners) {
            listener.badMove();
        }
    }

    void currentPlayerDrawCard() {
        ph.playerDrawCard(deck.drawCard());
    }

    void endCurrentPlayerTurn() {
        if (canEndTurn()) {
            endTurn();
        } else {
            announceMustPlayCard();
        }
    }

    boolean canEndTurn() {
        return ph.canEndTurn();
    }

    private void announceMustPlayCard() {
        for (GameListener listener : listeners) {
            listener.announceMustPlayCard();
        }
    }

    void tryPlayMoreCards(int index) {
        if (GameRules.stackable(ph.getPlayerCard(index), getTopPlayedCard())) {
            playCard(index);
        } else {
            announceBadMove();
        }
    }

    public void reverseTurn() {
        ph.reverseTurn();
    }

    public void chooseColor() {
        requestColor();
    }

    private void requestColor() {
        for (GameListener listener : listeners) {
            listener.requestWildColor();
        }
    }

    public void nextPlayerDraws(int num) {
        ph.nextPlayerDraws(num, deck);
        skip();
    }

    public void skip() {
        toSkip += 1;
    }

    void setWildColor(Color c) {
        getTopPlayedCard().setWildColor(c);
    }

}
