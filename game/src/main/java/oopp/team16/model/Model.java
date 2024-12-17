package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;

// TODO: model does not need to be GameListener, make relevant view and controllers GL instead
public class Model implements GameListener { // maybe change name ?ModelGameSetup?
    private List<ModelListener> listeners;
    private final Game game;
    private final DeckFactory df;
    private final List<Player> players;

    public Model() {
        listeners = new ArrayList<>();
        df = new CreateStdDeck();
        players = new ArrayList<>();
        game = new Game(df.createDeck(), 7);
        game.AddListener(this);
    }

    public void initGame() {
        getPlayers();
        game.init(players);
    }

    public void startGame() {
        game.startGame();
    }

    public void start() {
        game.start();
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
    }

    public void addListener(ModelListener l) {
        listeners.add(l);
    }

    public void addGameListener(GameListener gl) {
        game.AddListener(gl);
    }

    private void getPlayers() {
        for (ModelListener listener : listeners) {
            listener.requestPlayers();
        }
    }

    @Override
    public void takePlayerTurn() {
        for (ModelListener listener : listeners) {
            listener.takeTurn();
        }
    }

    private String[] ToStringArray(Card[] hand) {
        String[] handStrings = new String[hand.length];
        for (int i = 0; i < handStrings.length; i++) {
            handStrings[i] = hand[i].toString();
        }
        return handStrings;
    }

    public void playCard(int cardNumber) {
        // change from card number displayed to player to corresponding card index in
        // hand array
        game.tryPlay(cardNumber - 1);
    }

    @Override
    public void badMove() {
        for (ModelListener listener : listeners) {
            listener.announceBadMove();
        }
    }

    public void drawCard() {
        game.currentPlayerDrawCard();
    }

    @Override
    public void announceWinner(String name) {
        for (ModelListener listener : listeners) {
            listener.announceWinner(name);
        }
    }

    public boolean endTurn() {
        game.endCurrentPlayerTurn();
        boolean hasEnded = !game.getCurrentPlayer().stillTakingTurn();
        if (hasEnded) {
            // if player succesfully ended their turn
            game.nextTurn();
            //check if player won
            game.checkWinner();
        }
        return hasEnded;
    }

    @Override
    public void startPlayerTurn() {
        for (ModelListener listener : listeners) {
            listener.startNextPlayerTurn();
        }
    }

    @Override
    public void announceMustPlayCard() {
        for (ModelListener listener : listeners) {
            listener.announceMustPlayCard();
        }
    }

    public String getCurrentPlayerID() {
        return game.getCurrentPlayer().getName();
    }

    public String getTopPlayedCard() {
        return game.getTopPlayedCard().toString();
    }

    public String[] getCurrentPlayerHand() {
        return toStrings( game.getPlayerHand());
    }

    public String[] getListOfPlayers() {
        return toStrings(players.toArray(new Player[0]));
    }

    private <U> String[] toStrings(U[] objectArray) {
        String[] stringArray = new String[objectArray.length];
        for (int i = 0; i < objectArray.length; i++) {
            stringArray[i] = objectArray[i].toString();
        }
        return stringArray;
    }

    public void nextPlayerTurn() {
        game.startTurn();
    }
}
