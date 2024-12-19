package oopp.team16.model.gameLogic.Cards;
import oopp.team16.model.gameLogic.Cards.Colors.Color;

/**
 * A number card, with a numeric value and no action.
 */
public class NumCard implements Card {
    private final int value;
    private final BaseCard baseCard;

    /**
     *  The constructor for number cards has card type NUMBER
     *  nåt mer
     * @param color Color of the card
     * @param value Numeric value of the card
     */
    public NumCard(Color color, int value) {
        this.baseCard = new BaseCard(color, CardType.NUMBER, value);
        this.value = value;
    }
    @Override
    public Color getColor() {
        return baseCard.getColor();
    }
    @Override
    public CardType getType() {
        return baseCard.getType();
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return baseCard.getColor().toString() + " " + getValue();
    }

    @Override
    public void setWildColor(Color c) {
        baseCard.setWildColor(c); // won't do anything
    }

}
