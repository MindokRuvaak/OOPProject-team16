package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class SpecialCard implements Card{

    //ska ha en action, ta in i konstruktorn?

    private ActionStrategy action; //final ?
    private final BaseCard baseCard;

    public SpecialCard(Color color, CardType cardType){ //behöver ha en cardtype ? kan ha en action istället
        this.baseCard = new BaseCard(color, cardType);

    }

    @Override
    public Color getColor() {
        return baseCard.getColor();
    }

    @Override
    public CardType getType() {
        return baseCard.getType();
    }
}
