package main.java.oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Point2D;
import main.java.oopp.team16.model.Model;
import main.java.oopp.team16.model.ModelListener;
import main.java.oopp.team16.model.gameLogic.Cards.Card;
import main.java.oopp.team16.model.gameLogic.Player;
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
    private final Map<Player, HBox> playersHand = new HashMap<>();

    public void initialize() {
        m.addListener(this);
        m.initGame();

        buttonStart.setOnAction(event -> {
            m.startGame();
            buttonStart.setVisible(false);
            inputCard.setVisible(false);
            winningPane.setVisible(false);
            winningPane.setStyle("-fx-background-color: #2ecc71;");
            playersHand.put(m.getListOfPlayers().get(0), player1Hand);
            playersHand.put(m.getListOfPlayers().get(1), player2Hand);
            labelTurn.setText(m.getCurrentPlayerID() + "'s turn");
        });

        buttonDisplayHand.setOnAction(event -> {
            updateDisplay();
            buttonDisplayHand.setVisible(false);
        });
    }

    public void uno() {
    }

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
            Card[] hand = m.getCurrentPlayerHand();
            displayCard(m.getCurrentPlayer(), playersHand.get(m.getCurrentPlayer()), hand[hand.length - 1]);
        });
    }

    @Override
    public void startNextPlayerTurn(String name) {
        labelTurn.setText(name + "'s' turn");
    }

    public void endTurn() {
        endTurnButton.setOnAction(event -> {
            m.endTurn();
            m.getCurrentPlayer().resetTurnInfo();
            System.out.println(m.getCurrentPlayer().getName() + "ended turn");
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
        try {
            HBox hbox = playersHand.get(m.getCurrentPlayer());
            if (hbox == null) {
                System.err.println("No HBox found for player: " + m.getCurrentPlayer().getName());
                throw new IllegalStateException("No HBox found for player: " + m.getCurrentPlayer().getName());
            }
            if (cardIndex < 0 || cardIndex > hbox.getChildren().size()) {
                System.err.println("Invalid card index!");
                throw new IllegalArgumentException("You cannot play this card");
            }
            if (!m.getCurrentPlayer().hasPlayedCard()) {
                m.playCard(cardIndex);

            }

            else {
                System.out.println("have alredy played a card");
                m.playCard(cardIndex);
            }

        } catch (IllegalStateException | IndexOutOfBoundsException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        updateDisplay();
    }

    // should also go to view
    public void displayTopCard() {
        Card startingCard = m.getTopPlayedCard();
        if (startingCard != null) {
            ImageView cardView = createCard(startingCard);
            imageStartingCard.setImage(cardView.getImage());
        }

    }

    // move to view
    public void updateDisplay() {
        displayTopCard();
        displayHands();
        labelTurn.setText(m.getCurrentPlayerID() + "'s' turn");
    }

    // move to view
    public ImageView createCard(Card card) {
        String imagePath = "/ui/resources/unocards/" + card.getColor() + "_" + card.getValue() + ".png";
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            image = new Image(getClass().getResourceAsStream("/ui/resources/unocards/card_back.png"));
            System.err.println("Could not load image: " + imagePath);
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(CARD_HEIGHT); // Adjust these constants as needed
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        return imageView;
    }

    // should go to view
    public void displayHands() {
        // hardcode right now 2 hands.
        for (Player p : m.getListOfPlayers()) {
            displayHand(p, playersHand.get(p));
        }
    }

    // should go to view
    public void displayHand(Player player, HBox hbox) {
        if (player == null) {
            System.err.println("Error: current player is null");
            return;
        }
        hbox.getChildren().clear(); // Clear existing cards in case of updates

        for (Card card : player.getHand()) {
            ImageView cardView = createCard(card); // Create an ImageView for each card
            hbox.getChildren().add(cardView); // Add the card to the HBox
        }
    }

    // move to view
    public void displayCard(Player player, HBox hbox, Card card) {
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
    public void takeTurn(String[] hand, boolean playedCardYet) { // TODO: fix signature
        // TODO: implement
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
