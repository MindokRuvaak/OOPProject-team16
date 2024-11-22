package oopp.team16.model;

import java.util.ArrayList;
import java.util.List;

import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;

public class Model /* extends observable */ {
    private final List<ModelListener> listeners;
    private final Game game;
    private final DeckFactory df;

    public Model() {
        df = new CreateStdDeck();
        listeners = new ArrayList<>();

        game = new Game(df.createDeck());
    }

    public void initGame() {
        game.init();
        notifyListeners();
    }

    public String getCurrentPlayerID() {
        return game.getCurrentPlayerID();
    }

    public String getTopPlayedCardString() {
        return game.getTopPlayedCardString();
    }

    public void AddListener(ModelListener l) {
        listeners.add(l);
    }

    public void notifyListeners() {
        for (ModelListener l : listeners) {
            l.update();
        }
    }

}
