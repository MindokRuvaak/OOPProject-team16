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
    // private/* final */ Deck deck;
    // private /* final */Stack<Card> playedCards;
    private final int startingHandSize;
    private int toSkip;
    private final DeckHandler dh;
    private final PlayerHandler ph;

    Game(Deck deck, int startingHandSize) {
        this.listeners = new ArrayList<>();
        this.startingHandSize = startingHandSize;
        this.toSkip = 0;
        this.dh = new DeckHandler(deck);
        this.ph = new PlayerHandler();
    }

    void init(Collection<Player> players) {
        ph.init(players);
        dh.init(ph.getPlayers(), startingHandSize);
    }

    void startGameLoop() {
        gameLoop();
    }

    void start() {
        if (ph.getCurrentPlayer() == null) {
            nextTurn();
            startTurn();
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
        return dh.topCard();
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
                takeTurn();
            }
            noWinner = !checkWinner();
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
            // announce skipping player?
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

    private void takeTurn() {
        for (GameListener listener : listeners) {
            listener.takePlayerTurn();
        }
    }

    void AddListener(GameListener gameListener) {
        listeners.add(gameListener);
    }

    void tryPlay(int index) {
        if ((0 <= index && index < ph.playerHandSize())) {
            if (ph.firstPlayedCard()) {
                tryPlayCard(index);
            } else {
                tryPlayMoreCards(index);
            }
        }
    }

    private void tryPlayCard(int index) {
        if (GameRules.allowedPlay(ph.getPlayerCard(index),
                dh.topCard())) {
            playCard(index);
        } else {
            announceBadMove();
        }
    }

    private void playCard(int index) {
        Card card = ph.playCard(index);
        dh.play(card);

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
        ph.playerDrawCard(dh.drawCard());
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
        if (GameRules.stackable(ph.getPlayerCard(index), dh.topCard())) {
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
        ph.nextPlayerDraws(dh.drawCards(num));
        if (toSkip >= 1) {
            skip();
        }
    }

    public void skip() {
        toSkip += 1;
    }

    void setWildColor(Color c) {
        getTopPlayedCard().setWildColor(c);
    }

}
