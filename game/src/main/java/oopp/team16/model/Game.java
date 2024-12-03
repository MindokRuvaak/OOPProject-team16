package oopp.team16.model;

import java.util.*;

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
    private GameLogic gamelogic;

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
        this.turnOrder = players.iterator();
        setUpGame();
        gameLoop();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Card getTopPlayedCard() {
        return playedCards.peek();
    }

    public void initializeGame() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            for (int j = 0; j < 7; j++) {
                if (!deck.isEmpty()) {
                    Card card = deck.drawCard();
                    player.drawCard(card);
                }
            }
        }
    }

    private void gameLoop() {
        boolean noWinner = true;
        while (noWinner) {
            nextTurn();
            startTurn();
            while (this.currentPlayer.stillTakingTurn()) {
                takeTurn();
            }
            endTurn();

            if (!currentPlayer.hasCards()) {
                noWinner = false;
                announceWinner(currentPlayer.getName());
                break;
            }
        }
    }

    private void startTurn() {
        this.currentPlayer.startTurn();
        for (GameListener listener : listeners) {
            listener.startNextPlayerTurn(currentPlayer);
        }
    }

    private void endTurn() {
        this.currentPlayer.resetTurnInfo();
        this.currentPlayer.endTurn();
    }

    private void announceWinner(String name) {
        for (GameListener listener : listeners) {
            listener.announceWinner(name);
        }
    }

    private void nextTurn() {
        if (!this.turnOrder.hasNext()) {
            this.turnOrder = this.players.iterator();
        }
        this.currentPlayer = this.turnOrder.next();
        this.currentPlayer.startTurn();
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

    public List<Card> playableCards(Player player, Stack<Card> cardPile) {
        List<Card> playableHand = new ArrayList<>();
        for (Card card : player.getHand()) {
            if (gamelogic.canPlay(card, cardPile.peek())) {
                playableHand.add(card);
            }
        }
        return playableHand;
    }

    private void takeTurn() {
        for (GameListener listener : listeners) {
            listener.takePlayerTurn(currentPlayer);
        }

    }

    public void notifyListeners() {
        for (GameListener listener : listeners) {
            listener.update();
        }
    }

    public void AddListener(GameListener gameListener) {
        listeners.add(gameListener);
    }

    public void tryPlayCard(int index) {
        if (GameRules.allowedPlay(currentPlayer.getCard(index), getTopPlayedCard())) {
            playCard(index);
        } else {
            badMoveGoAgain();
        }
    }

    private void badMoveGoAgain() {
        announceBadMove();
    }

    private void playCard(int index) {
        playedCards.add(currentPlayer.playCard(index));
    }

    private void announceBadMove() {
        for (GameListener listener : listeners) {
            listener.badMove();
        }
    }

    public void currentPlayerDrawCard() {
        currentPlayer.drawCard(deck.drawCard());
    }

    public void endCurrentPlayerTurn() {
        if (currentPlayer.hasPlayedCard()) {
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

    public void tryPlayMoreCards(int index) {
        if (GameRules.stackable(currentPlayer.getCard(index), getTopPlayedCard())) {
            playCard(index);
        } else {
            badMoveGoAgain();
        }
    }
}
