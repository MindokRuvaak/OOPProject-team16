package oopp.team16.view;

import java.util.Scanner;

import oopp.team16.controller.Controller;
// import oopp.team16.model.Model;

public class ViewTerminal implements View {

    // Model m;
    Controller c;
    Scanner input = new Scanner(System.in);

    public ViewTerminal(/* Model m,  */Controller c) {
        // this.m = m;
        this.c = c;
    }

    public void printGameState() {
        StringBuilder printMessage = new StringBuilder("");
        printMessage.append("Player: ");
        printMessage.append(c.getCurrentPlayerID());
        printMessage.append("'s turn.\n\n");

        printMessage.append("current card in play: ");
        printMessage.append(c.getTopPlayedCardString());

        System.out.print(printMessage.toString());
    }

    @Override
    public void update() {
        printGameState();
    }

    @Override
    public void requestPlayers() {
        boolean noAnswer = true;
        while (noAnswer) {
            System.out.print("How many players?\n> ");
            String nStr = input.nextLine();
            try {
                int n = Integer.parseInt(nStr);
                noAnswer = false;
                providePlayets(n);
            } catch (Exception e) {
                System.out.println("Pease enter a digit only.");
            }
        }
    }

    private void providePlayets(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("Please provide user ID / name of player " + (i + 1) + "\n> ");
            String id = input.nextLine();
            c.addPlayer(id);
        }
    }

}