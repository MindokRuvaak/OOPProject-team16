package oopp.team16.model.gameLogic;

import java.util.Stack;

import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.BaseCard;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;
import oopp.team16.model.gameLogic.Cards.Value;
import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class CreateStdDeck implements DeckFactory {

    @Override
    public Deck createDeck() {
        Stack<Card> stack = new Stack<>();

        Color[] colors = {StdColors.red(),StdColors.blue(),StdColors.yellow(), StdColors.green()};

        for (Color color : colors) {
            for (int i = 0; i <= 9; i++) {
                stack.push(new BaseCard(color, Value.values()[i])); // Single 0 per color , use to getValue() instead?
                if (i != 0) { // Two copies for 1–9 cards
                    stack.push(new BaseCard(color, Value.values()[i]));
                }

            }
        }
        return new Deck(stack);
    }
}




