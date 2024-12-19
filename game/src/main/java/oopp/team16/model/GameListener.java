package oopp.team16.model;

public interface GameListener {

    void takePlayerTurn();

    void badMove();

    void announceWinner(int id, int score);

    void startPlayerTurn();

    void announceMustPlayCard();

    void requestWildColor();

}
