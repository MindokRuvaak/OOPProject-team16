package oopp.team16.model;

public interface GameListener {

    // void update();

    void takePlayerTurn();

    void badMove();

    void announceWinner(String name);

    void startPlayerTurn();

    void announceMustPlayCard();

    // void requestTurnAction();


}
