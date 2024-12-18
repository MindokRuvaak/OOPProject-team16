package oopp.team16.model;

import oopp.team16.model.gameLogic.Player;

public interface GameListener {

    // void update();

    void takePlayerTurn(Player currentPlayer);

    void badMove();

    void announceWinner(String name);

    void startPlayerTurn(Player currentPlayer);

    void announceMustPlayCard();

    void getColor();

    // void requestTurnAction();


}
