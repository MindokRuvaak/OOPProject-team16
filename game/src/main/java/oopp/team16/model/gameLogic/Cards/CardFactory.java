package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class CardFactory {
    public Card createNumCard(Color color, int value) {
        return new NumCard(color, value);
    }



}
