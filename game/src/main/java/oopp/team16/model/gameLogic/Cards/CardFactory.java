package oopp.team16.model.gameLogic.Cards;

import oopp.team16.model.gameLogic.Actions.*;
import oopp.team16.model.gameLogic.Cards.Colors.Color;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;

public class CardFactory {
    public Card createNumCard(Color color, int value) {
        return new NumCard(color, value);
    }

    //en för alla eller dela upp?
    public Card createSkipCard(Color color, int value){
        return new SpecialCard(color, CardType.SKIP, value, new SkipAction());
    }

    public Card createReverseCard(Color color, int value){
        return new SpecialCard(color, CardType.REVERSE, value, new ReverseAction());
    }

    public Card createDrawTwoCard(Color color, int value){
        return new SpecialCard(color, CardType.DRAW_TWO, value, new DrawTwoAction());
    }

    public Card createDrawFourCard(int value){
        return new SpecialCard(StdColors.black(), CardType.DRAW_FOUR, value, new DrawFourAction());
    }


    public Card createWildCard(int value){
        return new SpecialCard(StdColors.black(), CardType.WILDCARD, value, new WildCardAction());
    }






}
