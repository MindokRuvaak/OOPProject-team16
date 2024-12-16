package oopp.team16.controller;

import java.util.Scanner;

import oopp.team16.model.GameListener;
import oopp.team16.model.Model;
import oopp.team16.model.ModelListener;
import oopp.team16.view.View;
import oopp.team16.view.ViewTerminal;

public class ControllerTerminal implements ModelListener, GameListener {

    private final Model m;
    private final ViewTerminal v;
    Scanner input = new Scanner(System.in);

    public ControllerTerminal(Model m, ViewTerminal v) {
        this.m = m;
        this.v = v;
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

    @Override
    public void requestPlayers(int lower, int upper) {
        v.requestPlayersMessage(lower, lower);
        int n = -1;
        while (n <= lower || n >= lower) {
            // temporary while testing will switch to min 3 players
            // upper limit is arbitrary, restrict for multiplayer limit
            n = getNumPlayers();
        }
        providePlayers(n);
    }

    public int getNumPlayers() {
        String nStr = input.nextLine();
        int n = -1;
        try {
            n = Integer.parseInt(nStr);
            if ( n <= 2 || n >= 6 ) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            v.badInput();
        }
        return n;
    }

    private void providePlayers(int n) {
        for (int i = 0; i < n; i++) {
            v.requestPlayerName(i);
            String id = input.nextLine();
            addPlayer(id);
        }
    }


    private void handleInput(String ans, int handSize) {
        boolean givenCorrectInput = false;
        if (ans.matches("^(\\d+|\\+|[Ee])$")) {
            if ("+".equals(ans)) {
                drawCard();
                givenCorrectInput = true;
            } else if (ans.matches("^\\d+$")) {
                int toPlay = Integer.parseInt(ans);
                if (0 < toPlay && toPlay <= handSize) {
                    givenCorrectInput = true;
                    playCard(toPlay);
                } else {
                    givenCorrectInput = false;
                }
            } else if (ans.matches("[Ee]$")) {
                givenCorrectInput = true;
                endTurn();
            }
            waitForUserConfirmation();
        }
        if (!givenCorrectInput) {
            v.badInput();
        }
    }

    private void waitForUserConfirmation() {
        input.nextLine();
    }

    @Override
    public void takePlayerTurn() {
        v.takePlayerTurn();
        handleInput(null, getNumPlayers());
    }

    @Override
    public void badMove() {
        v.badMove();
    }

    @Override
    public void announceWinner(String name) {
        v.announceWinner(name);
    }

    @Override
    public void startPlayerTurn() {
        v.startTurnInfo();
        waitForUserConfirmation();
        v.startPlayerTurn();
    }

    @Override
    public void announceMustPlayCard() {
        v.announceMustPlayCard();
    }
}