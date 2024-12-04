package oopp.team16.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Point2D;
import oopp.team16.model.Model;
import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Player;


public class GameViewController {

    Model m = new Model();

    @FXML
    private Label labelLogo;

    @FXML
    private Label labelCurrentPlayer;

    @FXML
    private HBox hboxInfo;

    @FXML
    private Label labelInfo;

    @FXML
    private Button buttonInfo;

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
    private HBox playerHand; // Add this to your FXML file
    @FXML
    private Button buttonDisplayHand;

    private final double CARD_HEIGHT = 90.0;
    private final double CARD_WIDTH = 57.0;

    private final double CARD_SPACING_LARGE = 14.0;
    private final double CARD_SPACING_MEDIUM = 0.0;
    private final double CARD_SPACING_SMALL = - 25.0;
    private final double CARD_SPACING_ULTRA_SMALL = - 35.0;

    private Point2D PLAYER_STARTING_POINT;
    private final Point2D AI_1_STARTING_POINT = new Point2D(100.0, 75.0);
    private Point2D AI_2_STARTING_POINT;
    private Point2D AI_3_STARTING_POINT;


    public void initialize() {
        buttonInfo.setOnAction(event -> {
            hboxInfo.setVisible(false);
        });

        buttonStart.setOnAction(event -> {
                //   labelLogo.setText("Game Started!");
                buttonStart.setVisible(false);
                System.out.println("Game Start");
                m.initGame();
                System.out.println("size of playets" + m.players.size());
                System.out.println(m.getCurrentPlayer().getName());

                System.out.println("who is" + m.getCurrentPlayer());
            });
        buttonDisplayHand.setOnAction(event -> {

           displayHand(m.getCurrentPlayer());
           buttonDisplayHand.setVisible(false);
        });
    }
    /*
    @FXML
    public void addPlayer(){
        buttonAddPlayer.setOnAction(event -> {
            String playerName = PlayerName.getText();
            if (!playerName.isEmpty()) {
                m.addPlayer(playerName); // Add player to the game
                System.out.println("Player added: " + playerName);
            }
        });
    }

     */
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
        imageView.setFitHeight(CARD_HEIGHT);  // Adjust these constants as needed
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        return imageView;
    }

    @FXML
    public void displayHand(Player player) {
        if (player == null) {
            System.err.println("Error: current player is null");
            return;
        }
        playerHand.getChildren().clear(); // Clear existing cards in case of updates

        for (Card card : player.getHand()) {
            System.out.println("printing soon");
            ImageView cardView = createCard(card); // Create an ImageView for each card
            playerHand.getChildren().add(cardView); // Add the card to the HBox
            System.out.println("printing done");
        }
    }
    public void updateHand() {
        displayHand(m.getCurrentPlayer()); // Refresh the current player's hand
    }
    // further event handling methods

}
