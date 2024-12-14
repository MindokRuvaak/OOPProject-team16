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
// import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

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

    private String[] players;

    public void initialize() {
        m.addListener(this);
        m.initGame();
        player1Hand = new HBox();
        player2Hand = new HBox();

        buttonStart.setOnAction(event -> {
            this.players = m.getListOfPlayers();
            buttonStart.setVisible(false);
            inputCard.setVisible(false);
            winningPane.setVisible(false);
            winningPane.setStyle("-fx-background-color: #2ecc71;");
            playersHand.put(players[1].split(":")[0], player2Hand);
            playersHand.put(players[0].split(":")[0], player1Hand);
            buttonDisplayHand.setVisible(false);
            m.start();
        });
    }

    public void uno() {
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
        buttonPlayDeck.setOnAction(event -> {
            m.drawCard();
        });
    }

    @Override
    public void startNextPlayerTurn() {
        setTurnLabel(m.getCurrentPlayerID());
    }

    private void setTurnLabel(String name) {
        labelTurn.setText(name + "'s turn");
    }

    public void endTurn() {
        endTurnButton.setOnAction(event -> {
            m.endTurn();
        });
    }

    public void cardView() {
        playCardButton.setOnAction(event -> {
            inputCard.setVisible(true);
            inputCard.toFront();
            inputCard.setOpacity(1.0);
            inputCard.setStyle("-fx-background-color: #2ecc71;");
        });
    }

    public void closeCardCiew() {
        playDoneButton.setOnAction(event -> {
            playingCard();
            inputCard.setVisible(false);
        });
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
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
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
        displayTopCard();
        displayHands();
    }

    // move to view
    public ImageView createCard(String card) {

        String[] seperateCardStrings = card.split("\\s");

        String imagePath = "/ui/resources/unocards/" + seperateCardStrings[0] + "_" + seperateCardStrings[1] + ".png";
        ImageView imageView = new ImageView(
            new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitHeight(CARD_HEIGHT); // Adjust these constants as needed
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        return imageView;
    }

    // should go to view
    public void displayHands() {
        // hardcode right now 2 hands.
        displayHand(playersHand.get(m.getCurrentPlayerID()));
        for (String p : m.getListOfPlayers()) {
            if (!(p.split(":")[0].equals(m.getCurrentPlayerID()))) {
                String[] nameHandSize = p.split(":");
                displayBackOfHand(playersHand.get(nameHandSize[0]),Integer.parseInt(nameHandSize[1]));
            }
        }
    }

    private void displayBackOfHand(HBox hBox, int n) {
        hBox.getChildren().clear(); // Clear existing cards in case of updates
        for (int i = 0; i < n; i++) {
            hBox.getChildren().add(createCardBack());
        }
        hBox.setVisible(true);
    }

    private Node createCardBack() {
        ImageView imageView = new ImageView(
            new Image(getClass().getResourceAsStream("/ui/resources/unocards/Uno.png")));
        imageView.setFitHeight(CARD_HEIGHT); // Adjust these constants as needed
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        return imageView;
    }

    // should go to view
    public void displayHand(HBox hbox) {
        hbox.getChildren().clear(); // Clear existing cards in case of updates
        for (String card : m.getCurrentPlayerHand()) {
            // Create an ImageView for each card
            hbox.getChildren().add(createCard(card)); // Add the card to the HBox
        }
        hbox.setVisible(true);
    }

    // move to view
    public void displayCard(Player player, HBox hbox, String card) {
        if (player == null) {
            System.out.println("error current player is null");
            return;
        }
        ImageView cardView = createCard(card);
        hbox.getChildren().add(cardView);
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
    }

    @Override
    public void announceMustPlayCard() {
        // TODO: implement
    }
}
