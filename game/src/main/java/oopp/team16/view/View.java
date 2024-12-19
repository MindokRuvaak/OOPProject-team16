package oopp.team16.view;

// import oopp.team16.model.GameListener;
// import oopp.team16.model.ModelListener;
public interface View /* extends GameListener , ModelListener */ {

    void requestPlayersMessage(int lower, int upper);

    void badInput();

    void requestPlayerName(int i);

}
