package oopp.team16;

import oopp.team16.model.gameLogic.Cards.BaseCard;
import oopp.team16.model.gameLogic.Cards.Colors.Color;
import oopp.team16.model.gameLogic.Cards.Colors.StdColors;
import oopp.team16.model.gameLogic.Cards.NumCard;
import oopp.team16.model.gameLogic.Cards.Value;

public class testing {
    public static void main(String[] args) {
        BaseCard card = new BaseCard(StdColors.red(), Value.ZERO);
        System.out.println("/ui/resources/unocards/" + card.getColor() + "_" + card.getValue() + ".png");
    }
}
