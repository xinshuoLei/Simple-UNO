package unoContent;

import java.util.*;

/**
 * the class that includes functions and varaibles related to a player of the uno game
 */

public class Player {
	
	private String name;
	
	/**
	 * Each player's seven card in hand
	 */
	private List<Card> stack = new ArrayList<Card>();
	
	/**
	 * Card to be played in player's turn, null if no card can be played
	 * exactly how this card is chosen should be implemented in later assignments
	 */
	private Card cardToPlay = null;
	
	public Player(String setName) {
		name = setName;
	}
	
	/**
	 * Function that attempt to play a card, called in main game loop
	 * @return the card played
	 */
	public Card attemptPlayCard() {
		return cardToPlay;
	}
	
	/**
	 * Function that is called in the main function after the game state 
	 * finishing processing the card played
	 * @param toRemove the card to remove from player's stack
	 */
	public void removeCardFromStack(Card toRemove) {
		stack.remove(toRemove);
	}
	
	/**
	 * Draw cards from the draw pile
	 * @param drawPile copy of draw pile in game state class
	 * @param numCards number of cards to draw
	 * @param canPlay true if the card drawn can be played after 
	 * @param cardToMatch used to check if the card is valid if it can be played
	 * @param cardBeforeSpecial  to check if the card is valid if it can be played
	 * @return the card drawn if it can be played immediately after drawn, otherwise return null
	 */
	public Card drawCard(List<Card> drawPile, int numCards, boolean canPlay, 
			Card cardToMatch, Card cardBeforeSpecial) {
		Card cardDrawn = drawPile.get(0);
		// update stack
		for (int i = 0; i < numCards; i++) {
			stack.add(cardDrawn);
		}
		if (canPlay) {
			// if the card drawn matches cardToMatch, it must be played
			boolean mustPlay = GameState.checkCardValidity(cardToMatch, 
													cardBeforeSpecial, cardDrawn);
			if (mustPlay) {
				return cardDrawn;
			}
		}
		return null;
	}

	/**
	 * functions below are getters and setters
	 */
	
	
	public String getName() {
		return name;
	}

	public List<Card> getStack() {
		return stack;
	}
	
	public void setStack(List<Card> newStack) {
		stack = newStack;
	}
	
	public void setCardToPlay(Card cardToPlay) {
		this.cardToPlay = cardToPlay;
	}

}
