package oopp.team16.model.gameLogic.Actions;


import oopp.team16.model.SpecialCardLogic;
import oopp.team16.model.gameLogic.Cards.ActionStrategy;

public class ReverseAction implements ActionStrategy {

    @Override
    public void executeAction(SpecialCardLogic game) {
        game.reverseTurn();
    }
}
