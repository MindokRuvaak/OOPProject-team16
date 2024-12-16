package main.java.oopp.team16.view;

import java.util.Scanner;

import main.java.oopp.team16.controller.ControllerTerminal;
import main.java.oopp.team16.model.Model;

public class ViewTerminal implements main.java.oopp.team16.view.View {

    Model m;
    ControllerTerminal c;
    Scanner input = new Scanner(System.in);

    public ViewTerminal(Model m, ControllerTerminal c) {
        this.m = m;
        this.c = c;

        System.out.println("\nTo play, enter the character inside the brackets < >\n");
    }

    private void printGameState() {
        StringBuilder printMessage = new StringBuilder("\n\n");
        printMessage.append("Player: ");
        printMessage.append(m.getCurrentPlayerID());
        printMessage.append("'s turn.\n");

        printMessage.append("Current card in play: ");
        printMessage.append(m.getTopPlayedCardString());
        printMessage.append("\n");

        System.out.println(printMessage.toString());
    }

    // TODO: move input to controller
    @Override
    public void requestPlayers() {
        int n = -1;
        while (n <= 0 || n >= 9) {
            // temporary while testing will switch to min 2 players
            // upper limit is arbitrary, restrict for multiplayer limit
            n = getNumPlayers();
        }
        providePlayers(n);
    }

    // TODO: move input to controller
    private int getNumPlayers() {
        System.out.print("How many players?\n> ");
        String nStr = input.nextLine();
        int n = -1;
        try {
            n = Integer.parseInt(nStr);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a digit between 2 and 9.");
            // these numbers are not enforced yet!
        }
        return n;
    }

    // TODO: move input to controller
    private void providePlayers(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print("Please provide user ID / name of player " + (i + 1) + "\n> ");
            String id = input.nextLine();
            c.addPlayer(id);
        }
    }

    // TODO: change signature, remove inputs, instead create getter in model
    @Override
    public void takeTurn(String[] hand, boolean hasPlayedCard) {
        clearTerminal();
        if (hasPlayedCard) {
            System.out.println("Play more cards?");
        }
        printGameState();
        showHand(hand);
        turnActions(hand, hasPlayedCard);
    }

    // TODO: move input to controller
    private void waitForUserConfirmation() {
        System.out.println("Press enter to start your turn.");
        input.nextLine();
    }

    // TODO: move input to controller
    private void turnActions(String[] hand, boolean hasPlayedCard) {
        System.out.print("> ");
        String ans = input.nextLine();
        handleInput(ans, hand.length, hasPlayedCard);
    }

    public final static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // TODO: move to controller
    private void handleInput(String ans, int handSize, boolean hasPlayedCard) {
        boolean givenCorrectInput = false;
        if (ans.matches("^(\\d+|\\+|[Ee])$")) {
            if ("+".equals(ans)) {
                c.drawCard();
                givenCorrectInput = true;
            } else if (ans.matches("^\\d+$")) {
                int toPlay = Integer.parseInt(ans);
                if (0 < toPlay && toPlay <= handSize) {
                    givenCorrectInput = true;
                    c.playCard(toPlay);
                } else {
                    givenCorrectInput = false;
                }
            } else if (ans.matches("[Ee]$")) {
                givenCorrectInput = true;
                c.endTurn();
            } // these should be the only options possible for the regex
        }
        if (!givenCorrectInput) {
            System.out.println("Please enter one of the numbers corresponding to a card in your hand.\n" + //
                    "Or enter + to draw a card, E to end your turn.");
            waitForUserConfirmation();
        }
    }

    // TODO: change signature, remove inputs, instead create getter in model
    private void showHand(String[] hand) {
        System.out.println("Player hand:");
        String[] message = new String[hand.length];
        for (int i = 0; i < hand.length; i++) {
            message[i] = "<" + (i + 1) + "> : " + hand[i];
        }
        System.out.println(String.join("\n", message));
        System.out.println("\n<+> : Draw a card");
        System.out.println("<E> : End turn \n");
    }

    @Override
    public void announceBadMove() {
        System.out.println("That selected card cannot be played!");
        waitForUserConfirmation();
    }

    // TODO: change signature, remove inputs, instead create getter in model
    @Override
    public void announceWinner(String name) {
        clearTerminal();
        System.out.println("The winner is " + name + "!!");
    }

    // TODO: change signature, remove inputs, instead create getter in model
    @Override
    public void startNextPlayerTurn(String name) {
        clearTerminal();
        printGameState();
        waitForUserConfirmation();
        clearTerminal();
    }

    @Override
    public void announceMustPlayCard() {
        System.out.println("You must play a card before ending your turn!");
        waitForUserConfirmation();
    }

}
