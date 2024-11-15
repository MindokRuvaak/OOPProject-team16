package oopp.team16.model.gameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CreateDeck {

    public static Stack<StdCard> createUnoDeck() {
        Stack<StdCard> deck = new Stack<>();

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




