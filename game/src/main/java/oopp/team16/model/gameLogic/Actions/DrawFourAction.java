package oopp.team16.model.gameLogic.Actions;

import oopp.team16.model.SpecialCardLogic;
import oopp.team16.model.gameLogic.Cards.ActionStrategy;

/**
 * This action represents the draw four cards.
 *
 *  The next player in line has to draw four cards.
 *  The player playing the card chooses a color.
 */

public class DrawFourAction implements ActionStrategy {
    @Override
    public void executeAction(SpecialCardLogic game) {
            game.chooseColor();
            game.nextPlayerDraws(4);
        }
}
