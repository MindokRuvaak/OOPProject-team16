package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class NumCard implements Card {
    //extend or have a basecard???
    // I say extend -Daniell
    //extend or have a basecard??? ask on TA meeting maybe
    int numValue;
    BaseCard bCard;


    public NumCard(Color color, Value value, int numValue) {
        this.bCard = new BaseCard(color, value);
        this.numValue = numValue;
    }

    @Override
    public Color getColor() {
        return bCard.getColor();
    }

    @Override
    public Value getValue() {
        return bCard.getValue();
        return bCard.getValue();
    }

    @Override
    public String toString() {
        return bCard.toString();
    }
}
