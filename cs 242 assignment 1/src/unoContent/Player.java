package unoContent;

import java.util.*;

public class Player {
	
	private String name;
	
	// Each player's seven card in hand
	private List<Card> stack = new ArrayList<Card>();
	
	// card to be played in player's turn
	// null if no card can be played
	// exactly how this card is chosen should be implemented in later assignments
	private Card cardToPlay = null;
	
	public Player(String setName) {
		name = setName;
	}
	
	// attempt to play a card
	public Card playCard() {
		return cardToPlay;
	}
	
	// called in the main function after the game state finishing processing the card played
	// i.e. remove card from stack if card has been played and has taken effect
	public void removeCardFromStack(Card toRemove) {
		stack.remove(toRemove);
	}
	
	
	// draw numCards cards from the draw pile
	// @param canPlay: is true if the card drawn can be played (i.e. false in penalty and initializing)
	// @param cardToMatch: the card to match, null when can play is false
	// return the card drawn if it can be played immediately after drawn, otherwise return null
	public Card drawCard(List<Card> drawPile, int numCards, boolean canPlay, Card cardToMatch) {
		Card cardDrawn = drawPile.get(0);
		// update stack
		stack.add(cardDrawn);
		if (canPlay) {
			// if the card drawn matches cardToMatch, it must be played
			boolean mustPlay = Utilities.checkCardValidity(cardToMatch, cardToMatch);
			if (mustPlay) {
				return cardDrawn;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public List<Card> getStack() {
		return stack;
	}
	
	// used for unit testing
	public void setStack(List<Card> newStack) {
		stack = newStack;
	}

	// used for unit testing
	public void setCardToPlay(Card cardToPlay) {
		this.cardToPlay = cardToPlay;
	}

}
