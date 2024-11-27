package oopp.team16.view;

import java.util.Scanner;

import oopp.team16.controller.Controller;
import oopp.team16.model.gameLogic.Cards.Card;

public class ViewTerminal implements View {

    Controller c;
    Scanner input = new Scanner(System.in);

    public ViewTerminal(Controller c) {
        this.c = c;
    }

    public void printGameState() {
        StringBuilder printMessage = new StringBuilder("\n\n");
        printMessage.append("Player: ");
        printMessage.append(c.getCurrentPlayerID());
        printMessage.append("'s turn.\n");

        printMessage.append("Current card in play: ");
        printMessage.append(c.getTopPlayedCardString());
        printMessage.append("\n");

        System.out.println(printMessage.toString());
    }

    @Override
    public void update() {
        printGameState();
    }

    @Override
    public void requestPlayers() {
        boolean noAnswer = true;
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
            System.out.println("Pease enter a digit only.");
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
        int answ = 0;
        while (answ <= 0) {
            answ = cardToPLay(hand.length);
        }
        c.playCard(answ);
    }

    private void showHand(String[] hand) {
        System.out.println("Player hand:");
        String[] message = new String[hand.length];
        for (int i = 0; i < hand.length; i++) {
            message[i] = "<" + (i + 1) + "> : " + hand[i];
        }
        System.out.println(String.join("\n", message));
    }

    public int cardToPLay(int handSize) {
        System.out.print("> ");
        String nStr = input.nextLine();
        int n = -1;
        try {
            int ans = Integer.parseInt(nStr);
            if (ans <= 0 || ans > handSize){
                throw new NumberFormatException();
            }
            else{
                n = ans;
            }
        } catch (NumberFormatException e) {
            System.out.println("Pease enter one of the numbers corresponding to a card in your hand.");
        }
        return n;
    }

}