package oopp.team16.model.gameLogic.Cards;
import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class NumCard implements Card {
    private final int value;
    private final BaseCard bCard;

    public NumCard(Color color, int value) {
        this.bCard = new BaseCard(color, CardType.NUMBER, value);
        this.value = value;
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
        return value;
    }

    @Override
    public String toString() {
        return bCard.getColor().toString() + " " + getValue();
    }


}
