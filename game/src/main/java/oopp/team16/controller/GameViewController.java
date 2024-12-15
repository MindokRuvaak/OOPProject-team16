package oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Point2D;
import oopp.team16.model.Model;
import oopp.team16.model.ModelListener;

import java.util.HashMap;
import java.util.Map;

public class GameViewController implements ModelListener {

    Model m = new Model();

    @FXML
    private Label labelLogo;

    @FXML
    private Label labelCurrentPlayer;

    @FXML
    private Button buttonPlayDeck;

    @FXML
    private Button buttonLastCard;

    @FXML
    private Label labelPlayer2Name;

    @FXML
    private Label labelPlayer3Name;

    @FXML
    private Label labelPlayer1Name;

    @FXML
    private Button buttonStart;

    @FXML
    private Button buttonNewGame;

    @FXML
    private HBox player1Hand; // Add this to your FXML file
    @FXML
    private HBox player2Hand; // Add this to your FXML file

    @FXML
    private HBox player3Hand; // Add this to your FXML file

    @FXML
    private HBox player4Hand; // Add this to your FXML file

    @FXML
    private Button buttonDisplayHand;
    // TODO: remove this button? maybe
    @FXML
    private ImageView imageStartingCard;
    @FXML
    private Button playCardButton;
    @FXML
    private TextField intInput;
    @FXML
    private Button playDoneButton;
    @FXML
    private AnchorPane inputCard;
    @FXML
    private Label errorCard;
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
    private final double CARD_HEIGHT = 60;
    private final double CARD_WIDTH = 32;

    private final double CARD_SPACING_LARGE = 14.0;
    private final double CARD_SPACING_MEDIUM = 0.0;
    private final double CARD_SPACING_SMALL = -25.0;
    private final double CARD_SPACING_ULTRA_SMALL = -35.0;

    private Point2D PLAYER_STARTING_POINT;
    private final Point2D AI_1_STARTING_POINT = new Point2D(100.0, 75.0);
    private Point2D AI_2_STARTING_POINT;
    private Point2D AI_3_STARTING_POINT;
    private final Map<String, HBox> playersHand = new HashMap<>();

    // private String[] players;

    public void initialize() {
        m.addListener(this);
        m.initGame();

        buttonStart.setOnAction(event -> {
            String[] players = namesOf(m.getListOfPlayers());
            buttonStart.setVisible(false);
            inputCard.setVisible(false);
            winningPane.setVisible(false);
            playersHand.put(players[1], this.player2Hand);
            playersHand.put(players[0], this.player1Hand);
            m.start();
        });

        buttonDisplayHand.setOnAction(event -> {
            // updateDisplay();
            buttonDisplayHand.setVisible(false);
        });

        endTurnButton.setOnAction(event -> {
            endTurn();
        });

        
        playDoneButton.setOnAction(event -> {
            closeCardCiew();
        });
        
        buttonPlayDeck.setOnAction(event -> {
            drawCard();
        });

        playCardButton.setOnAction(event -> {
            cardView();
        });
    }

    //player data contains name/id and number of cards in hand as name:num, 
    // this returns array in same order, but only with player names
    private String[] namesOf(String[] players) {
        String[] ns = new String[players.length];
        for (int i = 0; i < ns.length; i++) {
            ns[i] = nameOf(players[i]);
        }
        return ns;
    }
    private String nameOf(String player) {
        return player.split(":")[0];
    }

    private int handSizeOf(String player) {
        return Integer.parseInt(player.split(":")[1]);
    }

    public void uno() {
        System.out.println(m.getCurrentPlayerID() + " has decclared UNO!");
    }

    // TODO: move to view
    @Override
    public void announceWinner(String name) {
        winningPane.setVisible(true);
        winningPane.toFront();
        winningPane.setOpacity(1.0);
        winningPane.setStyle("-fx-background-color: #2ecc71;");
        labelWinner.setText("Winner is " + name);
    }

    public void drawCard() {
        m.drawCard();
        updateDisplay();
    }

    @Override
    public void startNextPlayerTurn() {
        updateTurnLabel();
    }

    private void updateTurnLabel() {
        labelTurn.setText(m.getCurrentPlayerID() + "'s turn");
    }

    public void endTurn() {
        m.endTurn();
        m.nextPlayerTurn();
        updateDisplay();
    }

    public void cardView() {
        inputCard.setVisible(true);
        inputCard.toFront();
        inputCard.setOpacity(1.0);
        inputCard.setStyle("-fx-background-color: #2ecc71;");
    }

    public void closeCardCiew() {
        playingCard();
        inputCard.setVisible(false);
    }

    public void playingCard() {
        String input = intInput.getText(); // Get the index input as a string
        try {
            int index = Integer.parseInt(input); // Convert input to integer
            playCard(index); // Call the method to play the card
            System.out.println(index);

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    public void playCard(int cardIndex) {
        String currentPlayer = m.getCurrentPlayerID();
        try {
            HBox hbox = playersHand.get(currentPlayer);
            if (hbox == null) {
                System.err.println("No HBox found for player: " + currentPlayer);
                throw new IllegalStateException("No HBox found for player: " + currentPlayer);
            }
            if (cardIndex < 0 || cardIndex > hbox.getChildren().size()) {
                System.err.println("Invalid card index!");
                throw new IllegalArgumentException("You cannot play this card");
            }
            m.playCard(cardIndex);

        } catch (IllegalStateException | IndexOutOfBoundsException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } 
        updateDisplay();
    }

    // should also go to view
    public void displayTopCard() {
        String startingCard = m.getTopPlayedCard();
        if (startingCard != null) {
            ImageView cardView = createCard(startingCard);
            imageStartingCard.setImage(cardView.getImage());
        }
    }

    // move to view
    public void updateDisplay() {
        updateTurnLabel();
        displayTopCard();
        displayHands();
    }

    // should go to view
    public void displayHands() {
        for (String p : m.getListOfPlayers()) {
            String name = nameOf(p);
            if (nameOf(p).equals(m.getCurrentPlayerID())) {
                displayHand(playersHand.get(name));
            } else {
                displayBackOfHand(playersHand.get(name), handSizeOf(p));
            }
        }
    }

    // should go to view
    public void displayHand(HBox hand) {
        hand.getChildren().clear(); // Clear existing cards
        for (String card : m.getCurrentPlayerHand()) {
            ImageView cardView = createCard(card); // Create an ImageView for each card
            hand.getChildren().add(cardView); // Add the card to the HBox
        }
    }

    private void displayBackOfHand(HBox hand, int handSize) {
        hand.getChildren().clear(); // Clear existing cards in case of updates
        for (int i = 0; i < handSize; i++) {
            ImageView cardBack = createCardBack();
            hand.getChildren().add(cardBack);
        }
    }

    // move to view
    public ImageView createCard(String card) {

        String[] seperateCardStrings = card.split("\\s");

        String imagePath = "/ui/resources/unocards/" + seperateCardStrings[0] + "_" + seperateCardStrings[1] + ".png";
        return getCardImage(imagePath);
    }

    private ImageView getCardImage(String imagePath) {
        ImageView imageView = new ImageView(
                new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitHeight(CARD_HEIGHT); // Adjust these constants as needed
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        return imageView;
    }

    private ImageView createCardBack() {
        String imagePath = "/ui/resources/unocards/Uno.png";
        return getCardImage(imagePath);
    }

    @Override
    public void requestPlayers() {
        m.addPlayer("Player 1");
        m.addPlayer("Player 2");
    }

    // TODO: implement observer pattern methods

    @Override
    public void takeTurn(String[] hand) { // TODO: fix signature
        updateDisplay();
    }

    @Override
    public void announceBadMove() {
        // TODO: implement
        System.out.println("cant play card");
    }

    @Override
    public void announceMustPlayCard() {
        // TODO: implement
        System.out.println("must play card");
    }
}
