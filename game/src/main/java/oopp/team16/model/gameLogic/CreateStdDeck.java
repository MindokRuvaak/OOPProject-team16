package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Colors.*;

import java.util.Stack;

public class CreateStdDeck implements DeckFactory {

    public Stack<Card> createDeck() {
        Stack<Card> deck = new Stack<>();

        Color[] colors = {Green.getColor(), Red.getColor(), Blue.getColor(), Yellow.getColor()};

        for (Color color : colors) {
            for (int i = 0; i <= 9; i++) {
                deck.push(new StdCard(color, Value.values()[i])); // Single 0 per color , use to getValue() instead?
                if (i != 0) { // Two copies for 1–9 cards
                    deck.push(new StdCard(color, Value.values()[i]));
                }

            }
        }
        return deck;
    }
}




