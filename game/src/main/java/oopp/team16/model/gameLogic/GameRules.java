package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;

public class GameRules {

    private GameRules(){}

    public static boolean allowedPlay(Card playerCard, Card playedCard) {
        return sameColour(playerCard, playedCard) || sameFace(playerCard, playedCard);
    }

    private static boolean sameFace(Card playerCard, Card playedCard) {
        return playerCard.getValue() == playedCard.getValue();
    }

    private static boolean sameColour(Card playerCard, Card playedCard) {
        return playerCard.getColor() == playedCard.getColor();
    }

    public static boolean stackable(Card[] toPlay) {
        for (Card card : toPlay) {
            if (!sameFace(toPlay[0], card)) {
                // checks if all cards in to be played have same face
                // will check first card with itself 
                return false;
            }
        }
        return true;
    }

}
