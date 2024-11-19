package oopp.team16.model.gameLogic;

import java.util.Stack;

import oopp.team16.model.gameLogic.Colors.Blue;
import oopp.team16.model.gameLogic.Colors.Color;
import oopp.team16.model.gameLogic.Colors.Green;
import oopp.team16.model.gameLogic.Colors.Red;
import oopp.team16.model.gameLogic.Colors.Yellow;

public class CreateStdDeck implements DeckFactory {

    @Override
    public Deck createDeck() {
        Stack<Card> stack = new Stack<>();

        Color[] colors = {Green.getColor(), Red.getColor(), Blue.getColor(), Yellow.getColor()};

        for (Color color : colors) {
            for (int i = 0; i <= 9; i++) {
                stack.push(new StdCard(color, Value.values()[i])); // Single 0 per color , use to getValue() instead?
                if (i != 0) { // Two copies for 1–9 cards
                    stack.push(new StdCard(color, Value.values()[i]));
                }

            }
        }
        return new Deck(stack);
    }
}




