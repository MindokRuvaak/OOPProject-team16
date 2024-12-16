package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.Game;
import oopp.team16.model.SpecialCardLogic;

public interface ActionStrategy {
    void executeAction(SpecialCardLogic game);
}
