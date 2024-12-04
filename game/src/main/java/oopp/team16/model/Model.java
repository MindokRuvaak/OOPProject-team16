package oopp.team16.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Platform;
import oopp.team16.model.gameLogic.CreateStdDeck;
import oopp.team16.model.gameLogic.DeckFactory;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;

public class Model implements GameListener {
    private List<ModelListener> listeners;
    public final Game game;
    private final DeckFactory df;
    public final List<Player> players;

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
        startGameLoop();
        System.out.println("we are in hte game looooop");
    }

    private void startGameLoop() {
        // Run the game loop on a new thread to prevent blocking the UI thread
        new Thread(() -> {
            // Ensure that game loop runs in a background thread
            game.gameLoop();  // Run the game logic
            System.out.println("we are in the gameLOOp");

            // After each turn, you may need to update the UI (use Platform.runLater)
            Platform.runLater(() -> {
                // Update UI: display the hand of the current player
                notifyListeners();  // Notify the listeners to update the UI
            });

            // Handle the end of the game when it finishes
            Platform.runLater(() -> {
                System.out.println("Game Over! The winner is: " + game.getCurrentPlayer().getName());
            });
        }).start();
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
      /*  for (ModelListener listener : listeners) {
            listener.requestPlayers();
        }

       */
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        // Clear the existing players collection and add the hard-coded players
        players.clear();
        players.add(player1);
        System.out.println("added players" + player1.getName());
        players.add(player2);
        System.out.println("added players" + player2.getName());

    }

    @Override
    public void takePlayerTurn(Player currentPlayer) {
        // should maybe not allow multiple listeners?
        // only one listener (view)?
        for (ModelListener listener : listeners) {
            listener.takeTurn(ToStringArray((currentPlayer.getHand())),currentPlayer.hasPlayedCard());
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
        // change from card number displayed to player to corresponding card index in hand array
        game.tryPlayCard(cardNumber - 1);
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

    public void playMoreCards(int toPlay) {
        game.tryPlayMoreCards(toPlay - 1); //again change to index
    }

    @Override
    public void startNextPlayerTurn(Player currentPlayer) {
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
}
