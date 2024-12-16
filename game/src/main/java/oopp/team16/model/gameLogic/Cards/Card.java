package main.java.oopp.team16.model.gameLogic.Cards;

import main.java.oopp.team16.model.gameLogic.Cards.Colors.Color;

public interface Card {

    Color getColor();

    CardType getType();

    int getValue();  //not ideal, could be used to count score

}
