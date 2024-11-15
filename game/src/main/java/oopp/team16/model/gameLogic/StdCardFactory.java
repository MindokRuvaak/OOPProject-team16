package oopp.team16.model.gameLogic;

import java.util.Stack;

public class StdCardFactory implements CardFactory {

    public Stack<Card> createDeck() {
        Stack<Card> deck = new Stack<>();

        for (Color color : Color.values()) {
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




