package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.GameLogic;
import oopp.team16.model.gameLogic.Player;

public class Game {

    private final ArrayList<GameListener> listeners;
    private final LinkedList<Player> players;
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
        Iterator<Player> turnOrder = players.iterator();
        nextTurn(turnOrder);

        while (noWinner) {
            notifyListeners();
            takeTurn();

            // if (reverse()) {
            // turnOrder = players.descendingIterator();
            // }
            // check winner noWinner = ...
            nextTurn(turnOrder);
        }
    }

    private void nextTurn(Iterator<Player> turnOrder) {
        if (!turnOrder.hasNext()) {
            turnOrder = players.iterator(); // this feels bad? mutating input
        }
        this.currentPlayer = turnOrder.next();
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

    private boolean reverse() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reverse'");
    }

    public void notifyListeners() {
        for (GameListener listener : listeners) {
            listener.update();
        }
    }

    public void AddListener(GameListener gameListener) {
        listeners.add(gameListener);
    }

    public void playCard(int i) {
        if (GameRules.allowedPlay(currentPlayer.getHand()[i], getTopPlayedCard())) {
            playedCards.add(currentPlayer.playCard(i));
        } else {
            announceBadMove();
            notifyListeners();
            takeTurn();
        }

    }

    private void announceBadMove() {
        for (GameListener listener : listeners) {
            listener.badMove();
        }
    }

    public void currentPlayerDrawCard() {
        currentPlayer.drawCard(deck.drawCard());
    }
}
