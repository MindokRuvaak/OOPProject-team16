package oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import oopp.team16.server.GameClientController;
import oopp.team16.server.GameMessage;

import java.util.HashMap;
import java.util.Map;

public class GameViewController {

    @FXML
    private Label labelTurn;

    @FXML
    private Button buttonPlayDeck;

    @FXML
    private Button playCardButton;

    @FXML
    private TextField intInput;

    @FXML
    private Button playDoneButton;

    @FXML
    private AnchorPane inputCard;

    @FXML
    private ImageView imageStartingCard;

    @FXML
    private HBox player1Hand;

    @FXML
    private HBox player2Hand;

    @FXML
    private HBox player3Hand;

    @FXML
    private HBox player4Hand;

    @FXML
    private Button endTurnButton;

    @FXML
    private AnchorPane winningPane;

    @FXML
    private Label labelWinner;

    private final Map<String, HBox> playersHand = new HashMap<>();
    private GameClientController clientController;

    private final double CARD_HEIGHT = 60;
    private final double CARD_WIDTH = 32;

    public void setClientController(GameClientController controller) {
        this.clientController = controller;
    }

    public void initialize() {
        inputCard.setVisible(false);
        winningPane.setVisible(false);

        playCardButton.setOnAction(event -> cardView());
        playDoneButton.setOnAction(event -> closeCardView());
        endTurnButton.setOnAction(event -> sendEndTurn());
        buttonPlayDeck.setOnAction(event -> drawCard());
    }

    // ---------------------- SERVER ACTIONS ----------------------

    private void sendEndTurn() {
        GameMessage message = new GameMessage("endTurn", "Player1");
        clientController.sendMessage(message);
    }

    private void drawCard() {
        GameMessage message = new GameMessage("drawCard", "Player1");
        clientController.sendMessage(message);
    }

    private void sendPlayCard(int cardIndex) {
        GameMessage message = new GameMessage("playerMove", "Player1");
        message.addData("cardPlayed", cardIndex);
        clientController.sendMessage(message);
    }

    // ---------------------- USER INPUT ----------------------

    public void cardView() {
        inputCard.setVisible(true);
        inputCard.toFront();
        inputCard.setOpacity(1.0);
        inputCard.setStyle("-fx-background-color: #2ecc71;");
    }

    public void closeCardView() {
        String input = intInput.getText(); // Get input index
        try {
            int cardIndex = Integer.parseInt(input);
            sendPlayCard(cardIndex);
            inputCard.setVisible(false);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    // ---------------------- SERVER RESPONSE HANDLING ----------------------

    public void updateGameState(String topCard, String currentPlayer, Map<String, Integer> hands) {
        // Update the turn label
        labelTurn.setText(currentPlayer + "'s turn");

        // Update the top card display
        displayTopCard(topCard);

        // Update hands display
        updatePlayerHands(hands);
    }

    private void displayTopCard(String topCard) {
        if (topCard != null) {
            ImageView cardView = createCard(topCard);
            imageStartingCard.setImage(cardView.getImage());
        }
    }

    private void updatePlayerHands(Map<String, Integer> hands) {
        // Clear and update hands for each player
        for (Map.Entry<String, Integer> entry : hands.entrySet()) {
            String playerName = entry.getKey();
            int handSize = entry.getValue();

            HBox handBox = playersHand.get(playerName);
            if (handBox != null) {
                handBox.getChildren().clear();
                for (int i = 0; i < handSize; i++) {
                    handBox.getChildren().add(createCardBack());
                }
            }
        }
    }

    // ---------------------- UI UTILITIES ----------------------

    private ImageView createCard(String card) {
        String[] splitCard = card.split("\\s");
        String imagePath = "/ui/resources/unocards/" + splitCard[0] + "_" + splitCard[1] + ".png";
        return getCardImage(imagePath);
    }

    private ImageView getCardImage(String imagePath) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitHeight(CARD_HEIGHT);
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);
        return imageView;
    }

    private ImageView createCardBack() {
        String imagePath = "/ui/resources/unocards/Uno.png";
        return getCardImage(imagePath);
    }

    public void showWinner(String winnerName) {
        winningPane.setVisible(true);
        winningPane.toFront();
        labelWinner.setText("Winner: " + winnerName);
    }
}
