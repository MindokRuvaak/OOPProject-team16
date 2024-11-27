package oopp.team16.controller;

import java.util.Arrays;

import oopp.team16.model.Model;
import oopp.team16.model.gameLogic.Player;


public class Controller {

    Model m;
    public Controller(Model m) {
        this.m = m;
    }

    private Player getCurrentPlayer(){
        return m.getCurrentPlayer();
    }

    public String getCurrentPlayerHand(){
        return Arrays.toString(getCurrentPlayer().getHand()); // usage dependency on <I>Card
    }

    public String getCurrentPlayerID() {
        return getCurrentPlayer().getName();
    }

    public String getTopPlayedCardString() {
        return m.getTopPlayedCard().toString(); // usage dependency on <I>Card
    }
    
    public void addPlayer(String id) {
        m.addPlayer(id);
    }

    public void playCard(int n) {
        m.playCard(n);
    }

}