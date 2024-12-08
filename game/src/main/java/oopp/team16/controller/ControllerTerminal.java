package oopp.team16.controller;

import java.util.Arrays;

import oopp.team16.model.Model;


public class ControllerTerminal {

    Model m;
    public ControllerTerminal(Model m) {
        this.m = m;
    }


    public void addPlayer(String id) {
        m.addPlayer(id);
    }

    public void playCard(int n) {
        m.playCard(n);
    }

    public void drawCard() {
        m.drawCard();
    }

    public void endTurn() {
        m.endTurn();
    }

}