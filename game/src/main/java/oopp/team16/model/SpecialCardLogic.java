package oopp.team16.model;

/**
 *  This interface is used for abstracting game from components in the
 *  model that wants to interact with the game. It is used by the actions.
 */

public interface SpecialCardLogic {

    /**
     * Skips the next player turn
     */
    void skip();

    /**
     * The next player draws card
     * @param num Amount of cards the player draws
     */
    void nextPlayerDraws(int num);

    /**
     * Reverses the turn order
     */
    void reverseTurn();

    /**
     * The player chooses a color for the next player to play.
     */
    void chooseColor();

}
