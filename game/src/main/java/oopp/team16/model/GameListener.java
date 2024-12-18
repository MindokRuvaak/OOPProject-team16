package oopp.team16.model;

public interface GameListener {

    void takePlayerTurn();

    void badMove();

    void announceWinner(String name, int score);

    void startPlayerTurn();

    void announceMustPlayCard();

    void requestWildColor();

}
