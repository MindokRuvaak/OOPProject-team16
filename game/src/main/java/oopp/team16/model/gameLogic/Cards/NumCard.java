package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class NumCard implements Card {
    //extend or have a basecard??? 
    int numValue;
    BaseCard bCard;

    @Override
    public Color getColor() {
        return bCard.getColor();
    }

    @Override
    public Value getValue() {
        return bCard.getValue();
    }

    @Override
    public String toString() {
        return bCard.toString();
    }
}
