package oopp.team16.model;

import java.util.Collection;
import java.util.Stack;

import oopp.team16.model.gameLogic.Deck;
import oopp.team16.model.gameLogic.Player;
import oopp.team16.model.gameLogic.Cards.Card;
import oopp.team16.model.gameLogic.Cards.SpecialCard;

/**
 * Manages the deck of cards in the game.
 */
public class DeckHandler {
    private final Deck deck;
    private final Stack<Card> playedCards;

    /**
     *  Constructor for the DeckHandler class.
     *  Shuffles and initializes the deck and provides a discard pile.
     *
     * @param deck The deck of card to be used in the game.
     */
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

    /**
     *  Gives cards to all players and puts the first card in play.
     *
     * @param players The collection of players in the game.
     * @param numCards The number of card each player starts with.
     */
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
