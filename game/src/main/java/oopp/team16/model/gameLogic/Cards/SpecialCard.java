package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class SpecialCard implements Card{

    //ska ha en action

    private ActionStrategy action; //final ?
    private final BaseCard baseCard;

    public SpecialCard(Color color, CardType cardType){
        this.baseCard = new BaseCard(color, cardType);

    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public CardType getType() {
        return null;
    }
}
