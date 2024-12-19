package oopp.team16.model;

public interface ModelListener {


    void requestPlayers(int lower, int upper);

    void announceWinner(String name, int score);

    void requestWildColor();


}
