package oopp.team16.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oopp.team16.model.gameLogic.Card;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;

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
        notifyListeners();
    }

    public String getCurrentPlayerID() {
        return "player";
    }

    public Card getTopPlayedCard() {
        Card c = playedCards.drawOne();
        playedCards.add(c);
        return c;
    }

    public String getTopPlayedCardString() {
        Card c = playedCards.drawOne();
        playedCards.add(c);
        return c.toString();
    }
}