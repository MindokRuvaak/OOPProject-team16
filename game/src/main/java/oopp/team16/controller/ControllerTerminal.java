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
        v.requestPlayersMessage(lower, upper);
        int n = -1;
        while (n < lower || n > upper) {
            n = getNumPlayers(lower, upper);
        }
        providePlayers(n);
    }

    public int getNumPlayers(int lower, int upper) {
        String nStr = input.nextLine();
        int n = -1;
        try {
            n = Integer.parseInt(nStr);
            if ( n < lower || n > upper ) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            badInput();
        }
        return n;
    }

    private void badInput() {
        v.badInput();
        waitForUserConfirmation();
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
        }
        if (!givenCorrectInput) {
            badInput();
        }
    }

    private void waitForUserConfirmation() {
        input.nextLine();
    }

    @Override
    public void takePlayerTurn() {
        v.takePlayerTurn();
        String ans = input.nextLine(); 
        int handsize = m.getCurrentPlayerHand().length;
        handleInput(ans, handsize);
    }

    @Override
    public void badMove() {
        v.badMove();
        waitForUserConfirmation();
    }

    @Override
    public void announceWinner(String name, int score) {
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
        waitForUserConfirmation();
    }

    @Override
    public void requestWildColor() {
        v.requestWildColor();
        String ans = input.nextLine(); 
        m.setWildColor(ans);
    }

}