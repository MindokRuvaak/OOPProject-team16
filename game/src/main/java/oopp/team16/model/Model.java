package oopp.team16.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import oopp.team16.ModelUpdateListener;
import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;

public class Model /* extends observable */ {
    List<ModelUpdateListener> listeners = new ArrayList<>();
    List<Player> players = new LinkedList<>();
    Deck deck = DeckFactory.initDeck();
    Deck playedCards = DeckFactory.emptyDeck();



    public void addPlayer(Player player){
        players.add(player);
    }

    public void AddListener(ModelUpdateListener l){
        listeners.add(l);
    }
}