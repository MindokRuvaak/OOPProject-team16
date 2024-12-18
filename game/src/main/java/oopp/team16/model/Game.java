package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.SpecialCard;
import oopp.team16.model.gameLogic.Cards.Colors.Color;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.GameLogic;
import oopp.team16.model.gameLogic.GameRules;
import oopp.team16.model.gameLogic.Player;

public class Game implements SpecialCardLogic {

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

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    Card getTopPlayedCard() {
        return playedCards.peek();
    }

    // Main game loop,
    private void gameLoop() {
        // TODO: add checking for empty deck and reset deck
        boolean noWinner = true;
        while (noWinner) {
            nextTurn(); // switch current player
            startTurn();
            while (this.currentPlayer.stillTakingTurn()) {
                takeTurn();
            }
            endTurn();

            noWinner = checkWinner();
        }
    }

    private boolean checkWinner() {
        boolean noWinner = true;
        if (!currentPlayer.hasCards()) {
            noWinner = false;
            setWinnerScore();
            announceWinner(currentPlayer.getName(), currentPlayer.getScore());
        }
        return noWinner;
    }

    private void setWinnerScore() {
        int score = 0;
        for (Player player : players) {
            score += player.getHandValue();
        }
        currentPlayer.setScore(score);
    }

    private void startTurn() {
        this.currentPlayer.startTurn();
        for (GameListener listener : listeners) {
            listener.startPlayerTurn(currentPlayer);
        }
    }

    private void endTurn() {
        this.currentPlayer.resetTurnInfo();
    }

    private void announceWinner(String name, int score) {
        for (GameListener listener : listeners) {
            listener.announceWinner(name, score);
        }
    }

    public void nextTurn() {
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

    public void AddListener(GameListener gameListener) {
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
        Card card = currentPlayer.playCard(index);
        playedCards.add(card);

        if (card instanceof SpecialCard) {
            SpecialCard SC = (SpecialCard) card;
            SC.getAction().executeAction(this);

        }
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

    public void reverseTurn() {
        Collections.reverse(players);
        turnOrder = players.iterator();
    }

    public void chooseColor() {
        requestColor();
    }

    private void requestColor() {
        for (GameListener listener : listeners) {
            listener.getColor();
        }
    }

    public void nextPlayerDraws(int num) {
        Player nextPlayer = turnOrder.next();

        for (int i = 0; i < num; i++) {
            nextPlayer.drawCard(deck.drawCard());
        }
    }

    void setWildColor(Color c) {
        Card card = getTopPlayedCard();
    }

}
