package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.CardType;
import oopp.team16.model.gameLogic.Cards.NumCard;

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

    public static boolean stackable(Card playerCard, Card playedCard) {
        return sameFace(playerCard, playedCard);
    }

}
