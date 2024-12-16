package oopp.team16.model.gameLogic;

import java.util.Stack;

import oopp.team16.model.gameLogic.Cards.*;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;
import oopp.team16.model.gameLogic.Cards.Colors.Color;

public class CreateStdDeck implements DeckFactory {

    CardFactory cardFactory = new CardFactory();


    @Override
    public Deck createDeck() {
        Stack<Card> stack = new Stack<>();

        Color[] colors = {StdColors.red(),StdColors.blue(),StdColors.yellow(), StdColors.green()};

        for (Color color : colors) {
            for (int i = 0; i <= 9; i++) {
                stack.push(cardFactory.createNumCard(color, i)); // Single 0 per color , use to getValue() instead?
                if (i != 0) { // Two copies for 1–9 cards
                    stack.push(cardFactory.createNumCard(color, i));
                }

            }

        }

        return new Deck(stack);
    }

}




