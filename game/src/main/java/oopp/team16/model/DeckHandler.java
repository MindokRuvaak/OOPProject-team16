package oopp.team16.model;

import java.util.Collection;
import java.util.Stack;

import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.SpecialCard;

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
        putNumberCardInPlay();
    }

    private void putNumberCardInPlay() {
        playedCards.push(drawCard());// add one card to start
        while (topCard() instanceof SpecialCard) {
            playedCards.push(drawCard()); // if starting card is special add more
        }
        if (playedCards.size() > 1) { // if there are more than on card to start, keep top card and shuffle the rest
                                      // back into deck
            shufflePlayedCardsExceptTop();
        }
    }

    private void reUpDeck() {
        if (deck.isEmpty()) {
            shufflePlayedCardsExceptTop();
        }
    }

    private void shufflePlayedCardsExceptTop() {
        Card top = playedCards.pop();
        deck.add(playedCards);
        playedCards.clear();
        playedCards.add(top);
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
