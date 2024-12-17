package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class SpecialCard implements Card{

    private ActionStrategy action; //final ?
    private final BaseCard baseCard;

    //maybe not have value in here
    public SpecialCard(Color color, CardType cardType, int value, ActionStrategy action){
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
    public ActionStrategy getAction(){
        return this.action;
    }

    @Override
    public void setWildColor(Color c) {
        baseCard.setWildColor(c);
    }

    public String toString(){
            return baseCard.getColor().toString() + " " + baseCard.getType();

    }


}
