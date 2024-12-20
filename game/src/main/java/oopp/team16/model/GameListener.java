package oopp.team16.model;

/**
 * The GameListener interface contains methods that must be implemented
 * by any class that listens for events and actions occurring during the game.
 * These methods correspond to specific actions and events during gameplay.
 */
public interface GameListener {

    /**
     * Called when it is the current player's turn to play.
     */
    void takePlayerTurn();

    /**
     * Called when a player makes an invalid move.
     */
    void badMove();

    /**
     * Announces the winner of the game
     *
     * @param id The ID of the winning player
     * @param score The score of the winning player
     */
    void announceWinner(int id, int score);

    void startPlayerTurn();

    void announceMustPlayCard();

    /**
     *  Called when the game requests the player to choose a color for a wild card.
     */
    void requestWildColor();

}
