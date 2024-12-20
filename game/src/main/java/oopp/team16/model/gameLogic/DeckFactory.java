package oopp.team16.model.gameLogic;

/**
 * Factory interface for creating a deck of cards.
 * Implementing classes will define how the deck is constructed.
 */
public interface DeckFactory {

   Deck createDeck();
}
