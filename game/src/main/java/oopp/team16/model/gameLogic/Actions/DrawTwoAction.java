package oopp.team16.model.gameLogic.Actions;


import oopp.team16.model.SpecialCardLogic;
import oopp.team16.model.gameLogic.Cards.ActionStrategy;

/**
 * This action makes the next player in turn must draw two cards.
 */
public class DrawTwoAction implements ActionStrategy {
    @Override
    public void executeAction(SpecialCardLogic game) {
        game.nextPlayerDraws(2);
    }

}
