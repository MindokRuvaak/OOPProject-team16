package oopp.team16.model;

import java.util.*;

import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;

// TODO: model does not need to be GameListener, make relevant view and controllers GL instead
public class Model implements GameListener { // maybe change name ?ModelGameSetup?
    private List<ModelListener> listeners;
    public final Game game;
    private final DeckFactory df;
    private final List<Player> players;

    public Model() {
        listeners = new ArrayList<>();
        df = new CreateStdDeck();
        players = new ArrayList<>();
        game = new Game(df.createDeck(), 7);
        game.AddListener(this);
    }

    public List<Player> getListOfPlayers() {
        return players;
    }

    public void initGame() {
        getPlayers();
        game.init(players);
    }

    public void startGame() {
        game.startGame();
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
    public void takePlayerTurn(Player currentPlayer) {
        for (ModelListener listener : listeners) {
            listener.takeTurn(ToStringArray((currentPlayer.getHand())), currentPlayer.hasPlayedCard());
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

    public void endTurn() {
        game.endCurrentPlayerTurn();
    }

    @Override
    public void startPlayerTurn(Player currentPlayer) {
        for (ModelListener listener : listeners) {
            listener.startNextPlayerTurn(currentPlayer.getName());
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

    public String getTopPlayedCardString() {
        return game.getTopPlayedCard().toString();
    }

    // TODO: temporary methods, can change GameViewController's methods to not
    // require the card or player classes and reduce dependencies / lower coupling
    public Card[] getCurrentPlayerHand() {
        return game.getCurrentPlayer().getHand();
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public Card getTopPlayedCard() {
        return game.getTopPlayedCard();
    }
}
