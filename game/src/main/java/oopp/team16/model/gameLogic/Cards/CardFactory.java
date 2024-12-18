package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Actions.*;
import oopp.team16.model.gameLogic.Cards.Colors.Color;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;

public class CardFactory {

    private final int actionValue =  20;
    private final int wildValue = 50;
    public Card createNumCard(Color color, int value) {
        return new NumCard(color, value);
    }
    public Card createSkipCard(Color color){
        return new SpecialCard(color, CardType.SKIP, actionValue, new SkipAction());
    }

    public Card createReverseCard(Color color){
        return new SpecialCard(color, CardType.REVERSE, actionValue, new ReverseAction());
    }

    public Card createDrawTwoCard(Color color){
        return new SpecialCard(color, CardType.DRAW_TWO, actionValue, new DrawTwoAction());
    }

    public Card createDrawFourCard(){
        return new SpecialCard(StdColors.wild(), CardType.DRAW_FOUR, wildValue, new DrawFourAction());
    }

    public Card createWildCard(){
        return new SpecialCard(StdColors.wild(), CardType.WILDCARD, wildValue, new WildCardAction());
    }


}
