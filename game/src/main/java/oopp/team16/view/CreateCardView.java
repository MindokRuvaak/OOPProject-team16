package oopp.team16.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oopp.team16.model.gameLogic.Cards.Card;

public class CreateCardView {

    private final double CARD_HEIGHT = 60;
    private final double CARD_WIDTH = 32;


    public ImageView createCard(String card) {

        String[] seperateCardStrings = card.split("\\s");

        String imagePath = "/ui/resources/unocards/" + seperateCardStrings[0] + "_" + seperateCardStrings[1] + ".png";
        return getCardImage(imagePath);
    }

    public ImageView getCardImage(String imagePath) {
        ImageView imageView = new ImageView(
            new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitHeight(CARD_HEIGHT); // Adjust these constants as needed
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        return imageView;
    }

    public ImageView createCardBack() {
        String imagePath = "/ui/resources/unocards/Uno.png";
        return getCardImage(imagePath);
    }


}
