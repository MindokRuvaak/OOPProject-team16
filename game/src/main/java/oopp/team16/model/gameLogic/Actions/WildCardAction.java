package oopp.team16.model.gameLogic.Actions;

import oopp.team16.model.Game;
import oopp.team16.model.gameLogic.Cards.ActionStrategy;

public class WildCardAction implements ActionStrategy {

    @Override
    public void executeAction(Game game) {
        game.chooseColor();
    }
}
