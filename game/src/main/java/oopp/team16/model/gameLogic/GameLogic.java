package oopp.team16.model.gameLogic;

import oopp.team16.model.gameLogic.Cards.Card;

import java.util.Stack;

public class GameLogic {
   public boolean canPlay(Card card, Stack<Card> playedCard) {
        //stack, peek
        Card topCard = playedCard.peek();

        if(card.getColor().equals(topCard.getColor())) {
            return true;
        }else
            return card.getValue() == topCard.getValue(); // change when changing from enum

    }

    //byta håll från reverse
    void changeOrder(){

    }

    //byta spelare, måste wrap nånting
    void nextPlayer(){

    }










}
