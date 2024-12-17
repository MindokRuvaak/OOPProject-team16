package oopp.team16.model;

import oopp.team16.model.gameLogic.Player;

public interface GameListener {

    // void update();

    void takePlayerTurn();

    void badMove();

    void announceWinner(String name);

    void startPlayerTurn();

    void announceMustPlayCard();

    // void requestTurnAction();


}
