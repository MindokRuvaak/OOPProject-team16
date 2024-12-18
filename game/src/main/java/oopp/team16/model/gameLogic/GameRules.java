package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;

public class GameRules {

    private GameRules() {
    }

    public static boolean allowedPlay(Card playerCard, Card playedCard) {
        return sameColour(playerCard, playedCard) || sameFace(playerCard, playedCard) || playingWild(playerCard);
    }

    private static boolean playingWild(Card playerCard) {
        return playerCard.getColor() == StdColors.black();
    }

    private static boolean sameFace(Card playerCard, Card playedCard) {
        return playerCard.getValue() == playedCard.getValue();
    }

    private static boolean sameColour(Card playerCard, Card playedCard) {
        return playerCard.getColor() == playedCard.getColor();
    }

    public static boolean stackable(Card playerCard, Card playedCard) {
        return sameFace(playerCard, playedCard);
    }

}
