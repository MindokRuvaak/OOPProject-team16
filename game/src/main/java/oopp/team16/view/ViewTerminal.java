package oopp.team16.view;

import oopp.team16.model.Model;

public class ViewTerminal implements View {

    Model m;

    public ViewTerminal(Model m) {
        this.m = m;
        System.out.println("\nTo play, enter the character inside the brackets < >\n");
    }

    private void printGameState() {
        StringBuilder printMessage = new StringBuilder("\n\n");
        printMessage.append("Player: ");
        printMessage.append(m.getCurrentPlayerName());
        printMessage.append("'s turn.\n");

        printMessage.append("Current card in play: ");
        printMessage.append(m.getTopPlayedCard()+"\n");
        if (m.canEndTurn()) {
            printMessage.append("can end turn.\n");
        }

        System.out.println(printMessage.toString());
    }

    @Override
    public void requestPlayersMessage(int lower, int upper) {
        System.out.print("How many players (" + lower + "-" + upper + ")?\n> ");
    }

    @Override
    public void requestPlayerName(int i) {
        System.out.print("Please provide user ID / name of player " + (i + 1) + "\n> ");
    }

    // @Override
    public void takePlayerTurn() {
        String[] hand = m.getCurrentPlayerHand();
        clearTerminal();
        printGameState();
        showHand(hand);
        turnActions();
    }

    private void waitForUserConfirmation() {
        System.out.println("Press enter to start your turn.");
    }

    private void turnActions() {
        System.out.print("> ");
    }

    public void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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

    // @Override
    public void badMove() {
        System.out.println("That selected card cannot be played!");
        waitForUserConfirmation();
        waitForUserConfirmation();
    }

    // @Override
    public void announceWinner(String name) {
        clearTerminal();
        System.out.println("The winner is " + name + "!!");
    }

    // @Override
    public void startTurnInfo() {
        clearTerminal();
        printGameState();
        waitForUserConfirmation();
    }

    public void startPlayerTurn() {
        clearTerminal();
        showHand(m.getCurrentPlayerHand());
        printGameState();
    }

    // @Override
    public void announceMustPlayCard() {
        System.out.println("You must play a card before ending your turn!");
        waitForUserConfirmation();
    }

    public void badInput() {
        System.out.println("Unable to parse input");
    }

    public void requestWildColor() {
        System.out.print("What color do you declare the wild?\n> ");
    }
}
