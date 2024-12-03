package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class BaseCard implements Card {

    private final Color color;
    private final Value value;

    public BaseCard(Color color, Value value){
        this.color = color;
        this.value = value;
    }

    @Override
    public Color getColor(){
        return this.color;
    }

    @Override
    public Value getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return color.toString() + " " + value.toString();
    }

}
