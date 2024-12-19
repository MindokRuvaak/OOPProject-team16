package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.CardType;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;

public class GameRules {

    private GameRules() {
    }

    public static boolean allowedPlay(Card playerCard, Card playedCard) {
        return sameColour(playerCard, playedCard) || sameFace(playerCard, playedCard) || playingWild(playerCard);
    }

    private static boolean playingWild(Card playerCard) {
        return playerCard.getColor() == StdColors.wild();
    }

    private static boolean sameFace(Card playerCard, Card playedCard) {
        return (sameNumeric(playerCard, playedCard)) || (sameSpecialType(playerCard, playedCard));
    }

    private static boolean sameSpecialType(Card playerCard, Card playedCard) {
        return ((playerCard.getType() != CardType.NUMBER) && sameType(playerCard, playedCard));
    }

    private static boolean sameValue(Card playerCard, Card playedCard) {
        return playerCard.getValue() == playedCard.getValue();
    }

    private static boolean sameNumeric(Card playerCard, Card playedCard) {
        return ((playerCard.getType() == CardType.NUMBER) && sameType(playerCard, playedCard))
                && sameValue(playerCard, playedCard);
    }

    private static boolean sameType(Card playerCard, Card playedCard) {
        return playerCard.getType() == playedCard.getType();
    }

    private static boolean sameColour(Card playerCard, Card playedCard) {
        return playerCard.getColor() == playedCard.getColor();
    }

    public static boolean stackable(Card playerCard, Card playedCard) {
        return sameFace(playerCard, playedCard);
    }

}
