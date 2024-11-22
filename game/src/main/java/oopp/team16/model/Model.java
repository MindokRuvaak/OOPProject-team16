package oopp.team16.model;

import java.util.ArrayList;
import java.util.List;

import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;

public class Model implements GameListener {
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
        getPlayers();
        game.init(players);
        notifyListeners();
    }

    public String getCurrentPlayerID() {
        return game.getCurrentPlayerID();
    }

    public String getTopPlayedCardString() {
        return game.getTopPlayedCardString();
    }

    public void addPlayer(String name){
        players.add(new Player(name));
    }

    public void AddListener(ModelListener l) {
        listeners.add(l);
    }

    public void notifyListeners() {
        for (ModelListener listener : listeners) {
            listener.update();
        }
    }

    @Override
    public void update() {
        notifyListeners();
    }

    private void getPlayers() {
        for (ModelListener listener : listeners) {
            listener.requestPlayers();
        }
    }

}
