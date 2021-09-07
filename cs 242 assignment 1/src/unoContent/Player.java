package unoContent;

import java.util.*;

public class Player {
	
	private String name;
	
	// Each player's seven card in hand
	private List<Card> stack = new ArrayList<Card>();
	
	public Player(String setName) {
		name = setName;
	}
	
	// attempt to play a card
	public Card playCard() {
		// TODO
		return null;
	}
	
	// called in the main function after the game state finishing processing the card played
	// i.e. remove card from stack if card has been played and has taken effect
	public void removeCardFromStack(Card toRemove) {
		stack.remove(toRemove);
	}
	
	
	// draw numCards cards from the draw pile
	public void drawCard(List<Card> drawPile, int numCards) {
		for (int i = 0; i < numCards; i++) {
			// update stack to reflect draw
			stack.add(drawPile.get(0));
		}
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

}
