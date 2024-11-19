package oopp.team16.model;

import java.util.ArrayList;
import java.util.List;

public class Model /* extends observable */ {
    private final List<ModelListener> listeners;
    private final Game game;

    public Model() {
        listeners = new ArrayList<>();
        game = new Game();
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
