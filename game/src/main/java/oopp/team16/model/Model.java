package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;

// TODO: model does not need to be GameListener, make relevant view and controllers GL instead
public class Model /* implements GameListener */ { // maybe change name ?ModelGameSetup?
    private List<ModelListener> listeners;
    private final Game game;
    private final DeckFactory df;
    private final List<Player> players;

    public Model() {
        listeners = new ArrayList<>();
        df = new CreateStdDeck();
        players = new ArrayList<>();
        game = new Game(df.createDeck(), 7);
        // game.AddListener(this);
    }

    public void initGame() {
        getPlayers(3,5);
        game.init(players);
    }

    public void startGame() {
        game.startGame();
    }

    public void start() {
        game.start();
    }

    public void addPlayer(String name) {
        players.add(new Player(name, players.size()));
    }

    public void addListener(ModelListener l) {
        listeners.add(l);
    }

    public void addGameListener(GameListener gl) {
        game.AddListener(gl);
    }

    private void getPlayers(int lower, int upper) {
        for (ModelListener listener : listeners) {
            listener.requestPlayers(lower, upper);
        }
    }

    public void playCard(int cardNumber) {
        // change from card number displayed to player to corresponding card index in
        // hand array
        game.tryPlay(cardNumber - 1);
    }

    public void drawCard() {
        game.currentPlayerDrawCard();
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

    public String getCurrentPlayerName() {
        return game.getCurrentPlayer().getName();
    }

    public int getCurrentPlayerID() {
        return game.getCurrentPlayer().getid();
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
