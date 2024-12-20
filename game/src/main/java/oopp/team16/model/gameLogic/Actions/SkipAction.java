package oopp.team16.model.gameLogic.Actions;


import oopp.team16.model.SpecialCardLogic;
import oopp.team16.model.gameLogic.Cards.ActionStrategy;

/**
 * This action skips over the next player in the order.
 */
public class SkipAction implements ActionStrategy {
    @Override
    public void executeAction(SpecialCardLogic game) {
        game.skip();
    }
}
