package oopp.team16.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import oopp.team16.model.Model;
import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.GameRules;
import oopp.team16.model.gameLogic.Player;

import java.util.HashMap;
import java.util.Map;


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
    private final Map<Player, HBox> playersHand = new HashMap<>();

    public void initialize() {
        buttonInfo.setOnAction(event -> {
            hboxInfo.setVisible(false);
        });

        buttonStart.setOnAction(event -> {
                //   labelLogo.setText("Game Started!");
                buttonStart.setVisible(false);
                inputCard.setVisible(false);
                System.out.println("Game Start");
                m.initGame();
            System.out.println("initalize game");
                System.out.println("size of players    " + m.getListOfPlayers().size());
                System.out.println(m.getCurrentPlayer().getName());
                playersHand.put(m.getListOfPlayers().get(0), player1Hand);
                playersHand.put(m.getListOfPlayers().get(1), player2Hand);
            });
        buttonDisplayHand.setOnAction(event -> {
            System.out.println(m.getCurrentPlayer().getName());
           displayHands();
            displayTopCard();
           buttonDisplayHand.setVisible(false);
        });
    }

    public void cardView(){
        playCardButton.setOnAction(event -> {
            inputCard.setVisible(true);
            inputCard.toFront();
            inputCard.setOpacity(1.0);
            inputCard.setStyle("-fx-background-color: #2ecc71;");
        });
    }
    public void closeCardCiew(){
        playDoneButton.setOnAction(event -> {
            plauingCard();
            inputCard.setVisible(false);
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

    public void plauingCard() {
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
        HBox hbox = playersHand.get(m.getCurrentPlayer());

        if (hbox == null) {
            System.err.println("No HBox found for player: " + m.getCurrentPlayer().getName());
            return;
        }
        if (cardIndex < 0 || cardIndex > hbox.getChildren().size()) {
            System.err.println("Invalid card index!");
        }


        if(m.game.getPlayed()) {
            m.playCard(cardIndex);

            hbox.getChildren().remove(cardIndex - 1);
            System.out.println("kommer jag hit?");
            displayTopCard();
            System.out.println("kommer jag hit???+ visa kort");
        }
        else System.out.println("you cant play that card");
    }

    public void displayTopCard(){
        Card startingCard = m.getTopPlayedCard();
        if (startingCard != null) {
            ImageView cardView = createCard(startingCard);
            imageStartingCard.setImage(cardView.getImage());
        }
        else System.err.println("imageStartingCard is not initialized.");
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
        imageView.setFitHeight(CARD_HEIGHT);  // Adjust these constants as needed
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        return imageView;
    }

    public void displayHands() {
     /*   for (Player p : m.getListOfPlayers()) {
            System.out.println("printing cards for " + p.getName());
            displayHand(p,);
        }

      */
        //hardcode right now 2 hands.
        for (Player p :m.getListOfPlayers()) {
            displayHand(p, playersHand.get(p));
            System.out.println("printing cards for" + p.getName());
        }
        /*
        System.out.println("printing player 1 cards");
        displayHand(m.getListOfPlayers().get(0), player1Hand);
        System.out.println("all done printing player 1 cards");
        System.out.println("printing player 2 cards");
        displayHand(m.getListOfPlayers().get(1),player2Hand);
        System.out.println("all done printing player 2 cards");

         */
    }
    @FXML
    public void displayHand(Player player,HBox hbox) {
        if (player == null) {
            System.err.println("Error: current player is null");
            return;
        }
        hbox.getChildren().clear(); // Clear existing cards in case of updates

        for (Card card : player.getHand()) {
            System.out.println("printing soon");
            ImageView cardView = createCard(card); // Create an ImageView for each card
            hbox.getChildren().add(cardView); // Add the card to the HBox
            System.out.println("printing done");
        }
    }
}
