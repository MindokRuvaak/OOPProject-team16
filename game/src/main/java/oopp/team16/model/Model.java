package oopp.team16.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oopp.team16.model.gameLogic.*;

public class Model /* extends observable */ {
    private List<ModelListener> listeners;
    private List<Player> players;
    private int currentPlayer;
    private Deck deck;
    private Deck playedCards;

    public Model(){
        listeners = new ArrayList<>();
        players = new LinkedList<>();
        deck = new Deck(false);
        deck.shuffle();
        playedCards = new Deck(true);
    }


    public void createPlayer(String id){
        players.addLast(new Player(id));
    }

    public void AddListener(ModelListener l){
        listeners.add(l);
    }

    public void notifyListeners(){
        for (ModelListener l : listeners) {
            l.update();
        }
    }

    public void init() {
        playedCards.add(deck.drawOne());
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getCurrentPlayerID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Card getTopPlayedCard() {
        Card c = playedCards.drawOne();
        playedCards.add(c);
        return c;
    }
}