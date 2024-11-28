package oopp.team16.view;

import java.util.Scanner;

import oopp.team16.controller.Controller;

public class ViewTerminal implements View {

    Controller c;
    Scanner input = new Scanner(System.in);

    public ViewTerminal(Controller c) {
        this.c = c;
    }

    private void printGameState() {
        StringBuilder printMessage = new StringBuilder("\n\n");
        printMessage.append("Player: ");
        printMessage.append(c.getCurrentPlayerID());
        printMessage.append("'s turn.\n");

        printMessage.append("Current card in play: ");
        printMessage.append(c.getTopPlayedCardString());
        printMessage.append("\n");

        System.out.println(printMessage.toString());
    }

    // private

    @Override
    public void update() {
        printGameState();
    }

    @Override
    public void requestPlayers() {
        int n = 0;
        while (n <= 0 || n > 9) {
            n = getAnswer();
        }
        providePlayers(n);
    }

    private int getAnswer() {
        System.out.print("How many players?\n> ");
        String nStr = input.nextLine();
        int n = -1;
        try {
            n = Integer.parseInt(nStr);
        } catch (NumberFormatException e) {
            System.out.println("Pease enter a digit between 2 and 8.");
        }
        return n;
    }

    private void providePlayers(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("Please provide user ID / name of player " + (i + 1) + "\n> ");
            String id = input.nextLine();
            c.addPlayer(id);
        }
    }

    @Override
    public void takeTurn(String[] hand) {
        showHand(hand);
        boolean answered = false;
        while (!answered) {
            System.out.print("> ");
            String ans = input.nextLine();
            answered = handleInput(ans, hand.length);
        }
    }

    private boolean handleInput(String ans, int handSize) {
        boolean answered = false;
        if (ans.matches("^(\\d+|\\+)$")) {
            if ("+".equals(ans)) {
                c.drawCard();
                answered = true;
            } else if (ans.matches("^\\d+$")) {
                int toPlay = Integer.parseInt(ans);
                if (0 < toPlay || toPlay <= handSize) {
                    c.playCard(toPlay);
                    answered = true;
                } // these should be the only two options possible for the regex 
            }
        } 
        if (!answered) {
            System.out.println("Pease enter one of the numbers corresponding to a card in your hand.\n" + //
                    "Or enter + to draw a card.");
        }
        return answered;
    }

    private void showHand(String[] hand) {
        System.out.println("Player hand:");
        String[] message = new String[hand.length];
        for (int i = 0; i < hand.length; i++) {
            message[i] = "<" + (i + 1) + "> : " + hand[i];
        }
        System.out.println(String.join("\n", message));
    }

    @Override
    public void announceBadMove() {
        System.out.println("That selected card cannot be played!");
    }

    @Override
    public void announceWinner(String name) {
        System.out.println("The winner is " + name + "!!");
    }

}