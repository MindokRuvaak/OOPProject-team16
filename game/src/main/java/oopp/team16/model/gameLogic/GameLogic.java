package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;

public class GameLogic {

    public boolean canPlay(Card card, Card topCard) {
        return (card.getValue() == topCard.getValue()) || card.getColor().equals(topCard.getColor());
        // change when changing from enum
    }

    // byta håll från reverse
    void changeOrder() {

    }

    // byta spelare, måste wrap nånting
    void nextPlayer() { // fixed in Game.java should move here?

    }

}
