package oopp.team16.model.gameLogic.Cards;
import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class NumCard implements Card {
    //extend or have a basecard??? ask on TA meeting maybe
    int numValue;
    BaseCard bCard;


    public NumCard(Color color, int numValue) {
        this.bCard = new BaseCard(color, CardType.NUMBER);
        this.numValue = numValue;
    }

    @Override
    public Color getColor() {
        return bCard.getColor();
    }

    @Override
    public CardType getType() {
        return bCard.getType();
    }

    public int getValue() {
        return numValue;
    }

    @Override
    public String toString() {
        return bCard.toString();
    }


}
