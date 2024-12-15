package oopp.team16.model.gameLogic.Actions;

import oopp.team16.model.Game;
import oopp.team16.model.gameLogic.Cards.ActionStrategy;

public class DrawFourAction implements ActionStrategy {

    @Override
    public void executeAction(Game game) {
        for(int i = 0; i < 4; i++){
            game.currentPlayerDrawCard();
        }
    }//color thing
}
