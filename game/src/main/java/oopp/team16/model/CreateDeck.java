package oopp.team16.model;

import java.util.ArrayList;
import java.util.List;

public class CreateDeck {

    public static List<Card> createUnoDeck() {
        List<Card> deck = new ArrayList<>();

        for (Color color : Color.values()) {
            for (int i = 0; i <= 9; i++) {
                deck.add(new Card(color, Value.values()[i])); // Single 0 per color , use to getValue() instead?
                if (i != 0) { // Two copies for 1–9 cards
                    deck.add(new Card(color, Value.values()[i]));
                }

            }
        }
        return deck;
    }
}




