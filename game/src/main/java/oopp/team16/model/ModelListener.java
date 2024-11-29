package oopp.team16.model;

// import oopp.team16.model.gameLogic.Cards.Card;

public interface ModelListener {

    void update();

    void requestPlayers();

    void takeTurn(String[] hand);

    void announceBadMove();

    void announceWinner(String name);


}
