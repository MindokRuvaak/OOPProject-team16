package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public interface Card {

    Color getColor();

    CardType getType();

    int getValue();  //not ideal, could be used to count score


    void setWildColor(Color c);

}
