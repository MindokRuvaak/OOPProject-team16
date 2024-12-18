package oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import oopp.team16.model.GameListener;
import oopp.team16.model.Model;
import oopp.team16.model.ModelListener;

import java.util.*;

public class GameViewController implements GameListener, ModelListener{

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
    private VBox player3Hand; // Add this to your FXML file

    @FXML
    private VBox player4Hand; // Add this to your FXML file

    @FXML
    private Button buttonDisplayHand;
    // TODO: remove this button? maybe
    @FXML
    private ImageView imageStartingCard;
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
    @FXML
    private Button rules;
    @FXML
    private AnchorPane rulesPane;
    @FXML
    private Button x;
    @FXML
    private TextArea rulesText;
    private final double CARD_HEIGHT = 60;
    private final double CARD_WIDTH = 32;

    private final double CARD_SPACING_LARGE = 14.0;
    private final double CARD_SPACING_MEDIUM = 0.0;
    private final double CARD_SPACING_SMALL = -25.0;
    private final double CARD_SPACING_ULTRA_SMALL = -35.0;

    // private Point2D PLAYER_STARTING_POINT;
    // private final Point2D AI_1_STARTING_POINT = new Point2D(100.0, 75.0);
    // private Point2D AI_2_STARTING_POINT;
    // private Point2D AI_3_STARTING_POINT;
    private final Map<String, Pane> playersHand = new HashMap<>();
    private final List<Pane> paneList = new ArrayList<>();

    public void initialize() {
        m.addListener(this);
        m.initGame();
        winningPane.setVisible(false);
        buttonDisplayHand.setVisible(false);
        rulesPane.setVisible(false);
        loadGameRules();
        paneList.add(player1Hand);
        paneList.add(player2Hand);
        paneList.add(player3Hand);
        paneList.add(player4Hand);

        buttonStart.setOnAction(event -> {
          //  String[] players = namesOf(m.getListOfPlayers());
            buttonStart.setVisible(false);
            setPlayers();
           /* playersHand.put(players[3],this.player4Hand);
            playersHand.put(players[2], this.player3Hand);
            playersHand.put(players[1], this.player2Hand);
            playersHand.put(players[0], this.player1Hand);

            */
            buttonDisplayHand.setVisible(true);
            m.start();
        });
        buttonDisplayHand.setOnAction(event -> {
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
        rules.setOnAction(event -> {
            openRulesView();
        });

    }
     public void setPlayers(){
        int n = 0;
         for (int i = 0; i < m.getListOfPlayers().length; i++) {
             String[] players = namesOf(m.getListOfPlayers());
             playersHand.put(players[i], paneList.get(n));
                 n++;
         }
     }
    // player data contains name/id and number of cards in hand as name:num,
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
        System.out.println(m.getCurrentPlayerName() + " has decclared UNO!");
    }

    public void openRulesView(){
        rulesPane.setVisible(true);
        rulesPane.toFront();
        rulesPane.setOpacity(1.0);
        rulesPane.setStyle("-fx-background-color: #2ecc71;");
    }

    public void closeRulesView(){
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
    public void startPlayerTurn() {
        updateTurnLabel();
    }

    private void updateTurnLabel() {
        labelTurn.setText(m.getCurrentPlayerName() + "'s turn");
    }

    public void endTurn() {
        if (m.canEndTurn()) {
            m.endTurn();
            if (m.haveWinner())
            m.nextPlayerTurn();
            updateHide();
            buttonDisplayHand.setVisible(true);
        }
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
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    public void playCard(int cardIndex) {
        m.playCard(cardIndex);
        updateDisplay();
    }

    // move to view
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

    // // move to view
    public void updateHide() {
        updateTurnLabel();
        displayTopCard();
        hideHands();
    }

    // should go to view
    public void displayHands() {
        for (String p : m.getListOfPlayers()) {
            String name = nameOf(p);
            if (nameOf(p).equals(m.getCurrentPlayerName())) {
                displayHand(playersHand.get(name));
            } else {
                displayBackOfHand(playersHand.get(name), handSizeOf(p));
            }
        }
    }

    // move to view
    public void displayHand(Pane hand) {
        hand.getChildren().clear(); // Clear existing cards
        for (String card : m.getCurrentPlayerHand()) {
            ImageView cardView = createCard(card); // Create an ImageView for each card
            if (hand instanceof VBox) {
                cardView.setRotate(90); // Rotate the card to align vertically
                VBox.setMargin(cardView, new Insets(-30, 0, 0, 0));
            }
            cardView.setOnMouseClicked(event -> {

                int cardIndex = hand.getChildren().indexOf(cardView);
                playCard(cardIndex+1);
                System.out.println("Clicked card at index: " + cardIndex);
            });
            hand.getChildren().add(cardView); // Add the card to the HBox
        }
    }

    // move to view
    private void hideHands() {
        for (String p : m.getListOfPlayers()) {
            displayBackOfHand(playersHand.get(nameOf(p)), handSizeOf(p));
        }
    }

    private void displayBackOfHand(Pane hand, int handSize) {
        hand.getChildren().clear(); // Clear existing cards in case of updates
        for (int i = 0; i < handSize; i++) {
            ImageView cardBack = createCardBack();
            if (hand instanceof VBox) {
                // cardView.setFitWidth(CARD_HEIGHT); // Flip width and height
                //cardView.setFitHeight(CARD_WIDTH);
                cardBack.setRotate(90); // Rotate the card to align vertically
                VBox.setMargin(cardBack, new Insets(-30, 0, 0, 0));


            }
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
    public void requestPlayers(int lower, int upper) {
        m.addPlayer("Player 1");
        m.addPlayer("Player 2");
    }

    @Override
    public void takePlayerTurn() {
        updateHide();
    }

    // TODO: implement observer pattern methods

    @Override
    public void badMove() {
        // TODO: implement
        System.out.println("cant play that card");
    }

    @Override
    public void announceMustPlayCard() {
        // TODO: implement
        System.out.println("must play card");
    }
}
