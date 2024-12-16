package main.java.oopp.team16.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.oopp.team16.model.gameLogic.Cards.Card;

public class CreateCardView {

    private final double CARD_HEIGHT = 60;
    private final double CARD_WIDTH = 32;


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


}
