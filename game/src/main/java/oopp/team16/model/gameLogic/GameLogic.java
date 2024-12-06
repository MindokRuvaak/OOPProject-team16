package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;

public class GameLogic {

    public boolean canPlay(Card card, Card topCard) {
        return (card.getType().equals(topCard.getType()) || card.getColor().equals(topCard.getColor()));
    }

    // byta håll från reverse
    void changeOrder() {

    }

    // byta spelare, måste wrap nånting
    void nextPlayer() { // fixed in Game.java should move here?

    }

}
