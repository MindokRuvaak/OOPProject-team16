package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;

/**
 * The Model class serves as the core game logic manager.
 * It handles game setup, player management, and coordinates actions between the
 * game and listeners.
 * It manages the creation of the deck, player actions, and communicates with
 * listeners for updates.
 */
public class Model { // maybe change name ?ModelGameSetup?
    private List<ModelListener> listeners;
    private final Game game;
    private final DeckFactory df;
    private final List<Player> players;

    public Model() {
        listeners = new ArrayList<>();
        df = new CreateStdDeck();
        players = new ArrayList<>();
        game = new Game(df.createDeck(), 7);
    }

    public void initGame() {
        // this will change?, when possible to create loby with set amount of players
        getPlayers(2, 6);
        game.init(players);
    }

    public void startGameLoop() {
        game.startGameLoop();
    }

    public void start() {
        game.start();
    }

    public void addPlayer(int playerId) {
        players.add(new Player(playerId));
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

    // 
    public void playCard(int cardNumber) {
        game.tryPlay(cardNumber - 1);
    }
    
    // if int already converted to index
    public void playCardAtInd(int cardNumber) {
        game.tryPlay(cardNumber);
    }

    public void drawCard() {
        game.currentPlayerDrawCard();
    }

    public void endTurn() {
        game.endCurrentPlayerTurn();
    }

    public int getCurrentPlayerID() {
        return game.getCurrentPlayer().getId();
    }

    public String getTopPlayedCard() {
        return game.getTopPlayedCard().toString();
    }

    public String[] getCurrentPlayerHand() {
        return toStrings(game.getPlayerHand());
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
        game.nextTurn();
        game.startTurn();
    }

    public boolean haveWinner() {
        return game.checkWinner();
    }

    public boolean canEndTurn() {
        return game.canEndTurn();
    }

    public void setWildColor(String colorString) {
        parseColor(colorString);
    }

    private void parseColor(String colorString) {
        // switch case for now, may change later if needed
        switch (colorString) {
            case "red":
                game.setWildColor(StdColors.red());
                break;
            case "yellow":
                game.setWildColor(StdColors.yellow());
                break;
            case "green":
                game.setWildColor(StdColors.green());
                break;
            case "blue":
                game.setWildColor(StdColors.blue());
                break;
            default:
                game.chooseColor();// if input color does not match, try again
                break;
        }
    }

    public String[] getPlayerHandById(int playerId) {
        return toStrings(game.getPlayerHandById(playerId));
    }

    public void printState() {
        System.out.println("\ncurrentPlayerID: " + getCurrentPlayerID() +
                "\nList of players: " + Arrays.toString(getListOfPlayers())); // +
        for (Player p : players) {
             System.out.println("\nplayer "+ p.getId() + " cards: ");
             System.out.println(Arrays.toString(getPlayerHandById(p.getId())));
        }
        System.out.println("\ncurrent card: " + game.getTopPlayedCard() +
                "\ncurrent player: " + game.getCurrentPlayer().toString() +
                "\nplayers connected: " + players.size());
    }
}
