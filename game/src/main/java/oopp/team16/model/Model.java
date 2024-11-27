package oopp.team16.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;

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
        game.AddListener(this);
    }

    public void initGame() {
        getPlayers();
        game.init(players);
        notifyListeners();
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public Card getTopPlayedCard() {
        return game.getTopPlayedCard();
    }

    public void addPlayer(String name) {
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

    @Override
    public void takePlayerTurn(Player currentPlayer) {
        for (ModelListener listener : listeners) {
            listener.takeTurn(ToStringArray((currentPlayer.getHand())));
        }
    }

    // @Override
    // public void requestTurnAction() {
    // for (ModelListener listener : listeners) {
    // listener.requestAction();
    // }
    // }

    private String[] ToStringArray(Card[] hand) {
        String[] handStrings = new String[hand.length];
        for (int i = 0; i < handStrings.length; i++) {
            handStrings[i] = hand[i].toString();
        }
        return handStrings;
    }

    public void playCard(int cardNumber) {
        // change from card number displayed to player to corresponding card index in hand array
        game.playCard(cardNumber - 1); 
    }

}
