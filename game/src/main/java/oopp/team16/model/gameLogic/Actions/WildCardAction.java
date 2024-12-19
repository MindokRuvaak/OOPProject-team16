package oopp.team16.model.gameLogic.Actions;

import oopp.team16.model.SpecialCardLogic;
import oopp.team16.model.gameLogic.Cards.ActionStrategy;

/**
 * This action allows the player who plays the card to choose the color
 * that must be followed by the next player in turn.
 */

public class WildCardAction implements ActionStrategy {
    @Override
    public void executeAction(SpecialCardLogic game) {
        game.chooseColor();
    }

}
