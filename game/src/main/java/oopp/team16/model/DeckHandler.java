package oopp.team16.model;

import java.util.Collection;
import java.util.Stack;

import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;

public class DeckHandler {
    private final Deck deck;
    private final Stack<Card> playedCards;

    public DeckHandler(Deck deck) {
        this.deck = deck;
        this.deck.shuffle();
        playedCards = new Stack<>();
    }

    Card drawCard() {
        Card drawn = this.deck.drawCard();
        reUpDeck();
        return drawn;
    }

    void init(Collection<Player> players, int numCards) {
        for (int i = 0; i < numCards; i++) {
            givePlayersCard(players); // give all players a starting hand
        }
        playedCards.add(drawCard());// add one card to start
    }

    private void reUpDeck() {
        if (deck.isEmpty()) {
            Card top = playedCards.pop();
            deck.add(playedCards);
            playedCards.empty();
            playedCards.add(top);
        }
    }
    
    private void givePlayersCard(Collection<Player> players) {
        for (Player p : players) {
            p.drawCard(drawCard());
        }
    }

    public Card topCard() {
        return playedCards.peek();
    }

    public void play(Card card) {
        this.playedCards.push(card);
    }

    public Card[] drawCards(int num) {
        Card[] res = new Card[num];
        for (int i = 0; i < num; i++) {
            res[i] = this.deck.drawCard();
        }
        return res;
    }

}
