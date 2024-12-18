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

        addNumberCards(stack, colors);
        addSpecialCards(stack,colors);
        addWildSpecialCards(stack);

        return new Deck(stack);
    }

    private void addNumberCards(Stack<Card> stack, Color[] colors) {
        for (Color color : colors) {
            for (int i = 0; i <= 9; i++) {
                stack.push(cardFactory.createNumCard(color, i)); // Single 0 per color

                if (i != 0) { // Two copies for 1–9 cards
                    stack.push(cardFactory.createNumCard(color, i));
                }

            }
        }
    }

    private void addSpecialCards(Stack<Card> stack, Color[] colors){
        for (Color color : colors) {
            for(int i = 0; i < 2; i++){
                stack.push(cardFactory.createSkipCard(color));
                stack.push(cardFactory.createReverseCard(color));
                stack.push(cardFactory.createDrawTwoCard(color));
            }

        }
    }

    private void addWildSpecialCards(Stack<Card> stack){
        for(int i = 0; i < 4; i++){
            stack.push(cardFactory.createWildCard());
            stack.push(cardFactory.createDrawFourCard());
        }
    }
}




