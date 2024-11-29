package oopp.team16.model.gameLogic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import oopp.team16.model.gameLogic.Cards.BaseCard;
import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.Value;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;

public class GameRulesTest {
    @Test
    void testAllowedPlay() {
        // cases,   1: both same colour and face, expected true
        //          2: same colour different face, expected true
        //          3: different colour same face, expected true
        //          4: both different colour and face, expected false
        Card c1 = new BaseCard(StdColors.blue(), Value.ZERO);
        assertTrue(GameRules.allowedPlay(c1, c1));
        Card c2 = new BaseCard(StdColors.blue(), Value.FIVE);
        assertTrue(GameRules.allowedPlay(c1, c2));
        Card c3 = new BaseCard(StdColors.green(), Value.FIVE);
        assertTrue(GameRules.allowedPlay(c2, c3));
        Card c4 = new BaseCard(StdColors.yellow(), Value.EIGHT);
        assertFalse(GameRules.allowedPlay(c3, c4));
    }

    @Test
    void testStackable() {
        
    }
}
