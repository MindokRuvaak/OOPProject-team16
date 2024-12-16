package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

//not for mvp
public class SpecialCard implements Card{

    //ska ha en action, ta in i konstruktorn?

    private ActionStrategy action; //final ?
    private final BaseCard baseCard;

    //mayebe not have value in here
    public SpecialCard(Color color, CardType cardType, int value, ActionStrategy action){ //behöver ha en cardtype ?
        this.baseCard = new BaseCard(color, cardType, value);
        this.action = action;

    }

    @Override
    public Color getColor() {
        return baseCard.getColor();
    }

    @Override
    public CardType getType() {
        return baseCard.getType();
    }


    @Override
    public int getValue() {
        return baseCard.getValue();
    }
}
