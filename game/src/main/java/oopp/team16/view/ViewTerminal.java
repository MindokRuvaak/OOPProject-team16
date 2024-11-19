package oopp.team16.view;

import oopp.team16.controller.Controller;
import oopp.team16.model.Model;

public class ViewTerminal implements View {
    
    Model m;
    Controller c;


    public ViewTerminal(Model m, Controller c) {
        this.m = m;
        this.c = c;
    }

    public void printGameState(){
        StringBuilder printMessage = new StringBuilder("");
        printMessage.append("Player: "); 
        printMessage.append(m.getCurrentPlayerID());
        printMessage.append("'s turn.\n\n" );

        printMessage.append("current card in play: ");
        printMessage.append(m.getTopPlayedCardString() ); // adds usage dependency on cards, want to avoid?
        // better to get card component strings?
        System.out.print(printMessage.toString());
    }

    @Override
    public void update() {
        printGameState();
    }
}