package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.Game;
import oopp.team16.model.SpecialCardLogic;

/**
 * Represents the action a special card has in the game.
 *
 * The strategy pattern is used to encapsulate the actions, making them
 * interchangeable and enabling dynamic execution of different actions
 * during gameplay
 */

public interface ActionStrategy {
    /**
     * Executes the action
     *
     * @param game An abstraction of the game which the action is executed on.
     */
    void executeAction(SpecialCardLogic game);
}
