package oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oopp.team16.GameStartListener;
import oopp.team16.server.GameClientController;
import oopp.team16.view.CreateCardView;

import java.util.*;

public class GameViewController /* implements GameListener, ModelListener */ {

    // Model m = new Model();

    @FXML
    private Button buttonPlayDeck;

    @FXML
    private Button buttonStart;

    @FXML
    private HBox player1Hand;
    @FXML
    private HBox player2Hand;

    @FXML
    private VBox player3Hand;

    @FXML
    private VBox player4Hand;

    @FXML
    private Button buttonDisplayHand;
    @FXML
    private ImageView imageStartingCard;
    @FXML
    private Button endTurnButton;
    @FXML
    private Button buttonUno;
    @FXML
    private AnchorPane winningPane;
    @FXML
    private Label labelWinner;
    @FXML
    private Label labelTurn;
    @FXML
    private Button rulesButton;
    @FXML
    private AnchorPane rulesPane;
    @FXML
    private Button x;
    @FXML
    private TextArea rulesText;
    @FXML
    private Label labelUno;
    @FXML
    private Button buttonBlue;
    @FXML
    private Button buttonYellow;
    @FXML
    private Button buttonGreen;
    @FXML
    private Button buttonRed;
    @FXML
    private AnchorPane paneColour;
    @FXML
    private Label labelBadMove;

    private final Map<Integer, Pane> playersHand = new HashMap<>();
    private final List<Pane> paneList = new ArrayList<>();
    private int playerId;
    private String[] playerHand;
    private int currentPlayer;
    private List<String> players = new ArrayList<>();
    CreateCardView cc;
    private boolean gameStarted;
    // private GameStartListener gameStartListener;

    private GameClientController clientController; // WE DO A LITTLE BIT OF TESTING
    private int numConnectedPlayers;

    private String cardInPlay;

    public void initialize() {
        this.gameStarted = false;
        winningPane.setVisible(false);
        buttonDisplayHand.setVisible(false);
        rulesPane.setVisible(false);
        paneColour.setVisible(false);
        loadGameRules();
        cc = new CreateCardView();
        paneList.add(player1Hand);
        paneList.add(player2Hand);
        paneList.add(player3Hand);
        paneList.add(player4Hand);

        buttonStart.setOnAction(event -> {// this button will be redundant?
            if (gameStarted) {
                buttonStart.setVisible(false);
                updateDisplay();
            }
        });
        buttonDisplayHand.setOnAction(event -> { // unused
            updateDisplay();
            buttonDisplayHand.setVisible(false);
        });

        endTurnButton.setOnAction(event -> {
            endTurn();
        });

        buttonPlayDeck.setOnAction(event -> {
            drawCard();
        });

        x.setOnAction(event -> {
            closeRulesView();
        });
        rulesButton.setOnAction(event -> {
            openRulesView();
        });
        buttonUno.setOnAction(event -> {
            uno();
        });
        buttonBlue.setOnAction(event -> {
            provideBlue();
        });
        buttonGreen.setOnAction(event -> {
            provideGreen();
        });
        buttonYellow.setOnAction(event -> {
            provideYellow();
        });
        buttonRed.setOnAction(event -> {
            provideRed();
        });
    }

    public void provideBlue() {
        clientController.provideBlue();
        paneColour.setVisible(false);
    }

    public void provideGreen() {
        clientController.provideGreen();
        paneColour.setVisible(false);
    }

    public void provideYellow() {
        clientController.provideYellow();
        paneColour.setVisible(false);
    }

    public void provideRed() {
        clientController.provideRed();
        paneColour.setVisible(false);
    }

    public void setPlayers() {
        for (int i = 0; i < players.size(); i++) {
            int[] ps = idsOf(players.toArray(new String[0]));
            playersHand.put(ps[i], paneList.get(i));
        }
    }

    // player data contains name/id and number of cards in hand as
    // this returns array in same order, but only with player names
    private int[] idsOf(String[] players) {
        int[] ids = new int[players.length];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = idOf(players[i]);
        }
        return ids;
    }

    // ------------------------ [0] ------ [1] -- [2] [3] ------ [4]
    // player object toString: "Player " + id + " - Cards: " + handSize;
    private int idOf(String player) {
        return Integer.parseInt(player.split(" ")[1]);
    }

    private int handSizeOf(String player) {
        return Integer.parseInt(player.split(" ")[4]);
    }

    public void uno() {
        labelUno.setText(currentPlayer + "has UNO!");
    }

    public void openRulesView() {
        rulesPane.setVisible(true);
        rulesPane.toFront();
        rulesPane.setOpacity(1.0);
        rulesPane.setStyle("-fx-background-color: #2ecc71;");
    }

    public void closeRulesView() {
        rulesPane.setVisible(false);
    }

    public void loadGameRules() {
        String rules = """
                🎉 UNO Game Rules 🎉


                1⃣ **Setup**:
                   Each player starts with 7 cards. The rest of the deck is placed face-down as the draw pile.

                2 **Gameplay**:
                   - Match the top card on the discard pile by **color**, **number**, or **symbol**.
                   - You can play action cards (like Skip, Reverse, and Draw 2) to add strategy.
                   - You can stack **Draw 2** cards on top of each other, forcing the next player to draw multiple cards.
                   - You can also stack **Draw 4** Wild cards on top of each other, increasing the draw count.

                3 **Special Rules**:
                   - If you don't have a playable card, draw **up to 3 cards**.
                   - If you still can't play, your turn is over.
                   - If you forget to say "UNO!" when you have one card left, other players can call you out, and you must draw **2 penalty cards**.

                4 **Winning**:
                   - The first player to play all their cards wins the round.

                5 **Card Meanings**:
                   - **Skip: Skip the next players turn.
                   - **Reverse: Reverses the turn order.
                   - **Draw 2: The next player draws 2 cards (can be stacked!).
                   - **Wild: Change the color to anything you want.
                   - **Wild Draw: Change the color and force the next player to draw 4 cards (can be stacked!).
                """;
        rulesText.setText(rules);
    }

    // TODO: move to view
    public void announceWinner(int id, int score) {
        winningPane.setVisible(true);
        winningPane.toFront();
        winningPane.setOpacity(1.0);
        winningPane.setStyle("-fx-background-color: #2ecc71;");
        labelWinner.setText("Winner is player" + id);
    }

    public void drawCard() {
        if (gameStarted) {
            // TODO: send draw card message
            updateDisplay();
        }
    }

    public void startPlayerTurn() {
        if (gameStarted) {
            updateTurnLabel();
        }
    }

    private void updateTurnLabel() {
        labelTurn.setText("" + (currentPlayer + 1) + "'s turn");
    }

    public void endTurn() {
        // TODO: send end turn attempt message
        clientController.endTurn();

    }

    public void playCard(int cardIndex) {
        // TODO: play card message
        clientController.playCard(cardIndex);
    }

    // move to view
    public void displayTopCard() {
        if (cardInPlay != null) {
            ImageView cardView = cc.createCard(cardInPlay);
            imageStartingCard.setImage(cardView.getImage());
        }
    }

    // move to view
    public void updateDisplay() {
        if (gameStarted) {
            updateTurnLabel();
            displayTopCard();
            displayHands();
        }
    }

    // private void updateHands() {

    // }

    // should go to view
    public void displayHands() {
        for (String p : players) {
            int name = idOf(p);
            if (idOf(p) == this.playerId) {
                displayHand(playersHand.get(name));
            } else {
                displayBackOfHand(playersHand.get(name), handSizeOf(p));
            }
        }
    }

    // move to view
    public void displayHand(Pane hand) {
        hand.getChildren().clear(); // Clear existing cards
        for (String card : this.playerHand) {

            ImageView cardView = cc.createCard(card); // Create an ImageView for each card
            if (hand instanceof VBox) {
                cardView.setRotate(90); // Rotate the card to align vertically
                VBox.setMargin(cardView, new Insets(-30, 0, 0, 0));
            }
            cardView.setOnMouseClicked(event -> {
                int cardIndex = hand.getChildren().indexOf(cardView);
                playCard(cardIndex);
                System.out.println("Clicked card at index: " + cardIndex);
                System.out.println(cardView.toString());
            });
            hand.getChildren().add(cardView); // Add the card to the HBox
        }
    }

    private void displayBackOfHand(Pane hand, int handSize) {
        hand.getChildren().clear(); // Clear existing cards in case of updates
        for (int i = 0; i < handSize; i++) {
            ImageView cardBack = cc.createCardBack();
            if (hand instanceof VBox) {
                // cardView.setFitWidth(CARD_HEIGHT); // Flip width and height
                // cardView.setFitHeight(CARD_WIDTH);
                cardBack.setRotate(90); // Rotate the card to align vertically
                VBox.setMargin(cardBack, new Insets(-30, 0, 0, 0));

            }
            hand.getChildren().add(cardBack);
        }
    }

    public void takePlayerTurn() {
        updateDisplay();
    }

    // TODO: implement gui for these

    // @Override
    public void badMove() {
        labelBadMove.setText("cant play that card");
    }

    // @Override
    public void announceMustPlayCard() {
        // TODO: implement gui
        System.out.println("must play card");
    }

    // @Override
    public void requestWildColor() {
        // temporary termianl input
        // TODO: implement gui
        paneColour.setVisible(true);
        // need winidow popup with 4 buttons, one for each color
        // button "COLOR" calls setWildColor("COLOR") with "COLOR" to be the actual
        // color name as string eg. "red"
    }

    public void addClientController(GameClientController gcc) {
        this.clientController = gcc;
    }

    public void ping() {
        clientController.ping();
        printGameState();
    }

    private void printGameState() {
        System.out.println("\nthisPlayerID: " + playerId +
                "\nList of players: " + Arrays.toString(players.toArray(new String[0])) +
                "\nthis player cards: " + Arrays.toString(playerHand) +
                "\ncurrent card: " + cardInPlay +
                "\ncurrent player id: " + currentPlayer +
                "\nplayers connected: " + numConnectedPlayers);
    }

    public void setPlayerId(int id) {
        playerId = id;
        System.out.println("Setting id to: " + playerId);
    }

    public int getPlayerId() {
        return this.playerId;
    }

    // gameStateMessage expected to contain <key>:<value>
    // "currentPlayer":<Id of current player> -- singleton array value
    // "listOfPlayers":<[] Ids of all players>
    // "topCard":<card currently in play> -- singleton array value
    // --for each player in game--
    // "playerId":<[] hand of player>
    public void recieveServerData(Map<String, String[]> dataMap) {
        this.currentPlayer = Integer.parseInt(dataMap.get("currentPlayer")[0]);
        players = List.of(dataMap.get("listOfPlayers"));
        playerHand = dataMap.get(String.valueOf(this.playerId));
        cardInPlay = dataMap.get("topCard")[0];
        // printGameState();
    }

    public int numPlayersConnected() {
        return numConnectedPlayers;
    }

    public void setNumPlayersConnected(int n) {
        this.numConnectedPlayers = n;
    }

    public void startGame() {
        this.gameStarted = true;
        setPlayers();
        // updateDisplay();
    }

}
