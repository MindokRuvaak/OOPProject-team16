package oopp.team16.model;

import oopp.team16.model.gameLogic.Cards.Card;

public class GameRules {

    static boolean allowedPlay(Card playerCard, Card playedCard) {
        return sameColour(playerCard, playedCard) || sameFace(playerCard, playedCard);
    }

    private static boolean sameFace(Card playerCard, Card playedCard) {
        return playerCard.getValue() == playedCard.getValue();
    }

    private static boolean sameColour(Card playerCard, Card playedCard) {
        return playerCard.getColor() == playedCard.getColor();
    }

}
