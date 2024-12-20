package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

/**
 * Represents a card in the game, that has a color, type and value.
 */

public interface Card {

    Color getColor();

    CardType getType();

    int getValue();

    void setWildColor(Color c);

}
