package oopp.team16.model.gameLogic.Cards;
import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class BaseCard implements Card {
    private final Color color;
    private final CardType type;

    public BaseCard(Color color, CardType type){
        this.color = color;
        this.type = type;
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
    public String toString(){
        return color.toString() + " " + type.toString();
    }

}
