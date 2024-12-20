package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;

/**
 * Represents a base card in the game with a specific color, type, and value.
 */

public class BaseCard implements Card {
    private Color color;
    private final CardType type;
    private final int value;

    public BaseCard(Color color, CardType type, int value){
        this.color = color;
        this.type = type;
        this.value= value;
    }

    @Override
    public Color getColor(){
        return this.color;
    }

    @Override
    public CardType getType(){
        return this.type;
    }

    @Override
    public int getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return color.toString() + " " + type.toString();
    }

    @Override
    public void setWildColor(Color c){
        if (color == StdColors.wild()){
            this.color = c;
        }
    }

}
