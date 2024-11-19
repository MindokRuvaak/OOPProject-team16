package oopp.team16.model.gameLogic;

import java.util.Stack;

public class logic {

    //valid play, turn order

    // tar in två kort, ligger högst i played card och tar in kort som spelas, kollar om man får sepla

    boolean canPlay(Card card, Stack<Card> playedCard) {
        //stack, peek
        Card topCard = playedCard.peek();

        if(card.getColor().equals(topCard.getColor())) {
            return true;
        }else
            return card.getValue() == topCard.getValue(); // change when changing from enum

    }








}
