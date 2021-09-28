package unoGameLogic;

import java.util.*;


import unoCard.Card;

/**
 * the class that includes functions and varaibles related to a player of the uno game
 */

public abstract class Player {
	
	/**
	 * constant for player name prefix
	 */
	public static final String NAME_PREFIX = "Player";
	
	/**
	 * constant for baseline ai type
	 */
	public static final String BASELINE_AI = "baseline";
	
	/**
	 * constant for strategic AI type
	 */
	public static final String STRATEGIC_AI = "strategic";
	
	/**
	 * constant for no AI player
	 */
	public static final String NOT_AI = "N/A";
	
	/**
	 * Each player's index in the list allPlayer in GameState class
	 */
	private int index;
	
	/**
	 * Each player's name, format: "Player" + index
	 */
	private String name;
	
	/**
	 * Each player's ai type, for human players, this is set to NOT_AI
	 */
	private String aiType;
	
	/**
	 * Each player's seven card in hand
	 */
	private List<Card> stack = new ArrayList<Card>();
	
	/**
	 * Card to be played in player's turn, null if no card can be played
	 * exactly how this card is chosen should be implemented in later assignments
	 */
	private Card cardToPlay = null;
	
	/**
	 * Color selection for wild card
	 */
	private String colorToUse = null;
	
	/**
	 * Map that maps the color of a card to how many cards of that color is in validCards
	 * colors include red, blue, yellow, green, wild, and wild draw four  
	 */
	private Map<String, Integer> colorToNumber = new HashMap<>();
	
	
	/**
	 * Cards that are valid to play in the current turn
	 */
	private List<Card> validCards = new ArrayList<Card>();
	
	
	/**
	 * constructor of the player class
	 * @param setIndex index of the player in the list allPlayers in 
	 * GameState class
	 */
	public Player(int setIndex) {
		index = setIndex;
		name = NAME_PREFIX + String.valueOf(index);
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
		String color = toRemove.getColor();
		// for wild cards, color is set to their symbol
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
		// update stack
		Card cardDrawn = drawPile.get(0);
		for (int i = 0; i < numCards; i++) {
			cardDrawn = drawPile.get(i);
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
	 * Find all cards that are valid to play in the current turn 
	 * @param cardToMatch the card to match in the current turn
	 * @param cardBeforeSpecial the card before special for the custom rule
	 */
	public void updateValidCard(Card cardToMatch, Card cardBeforeSpecial) {
		for (Card oneCard: stack) {
			if (GameState.checkCardValidity(cardToMatch, cardBeforeSpecial
					, oneCard)) {
				// if card is valid, add to validCard
				validCards.add(oneCard);
			}
		}
	}
	
	/**
	 * Iterate through cards, update CardToNumber
	 * @param isValid if is valid is true, iterate through valid cards
	 * else, iterate through stack
	 */
	public void updateCardToNumber(boolean isValid) {
		// first set all values to 0
		colorToNumber.put(Card.BLUE, 0);
		colorToNumber.put(Card.YELLOW, 0);
		colorToNumber.put(Card.GREEN, 0);
		colorToNumber.put(Card.RED, 0);
		colorToNumber.put(Card.WILD, 0);
		colorToNumber.put(Card.WILD_DRAW4, 0);
		
		List<Card> toIterate = null;
		
		if (isValid) {
			toIterate = validCards;
		} else {
			toIterate = stack;
		}
		
		for (Card oneCard : toIterate) {
			String color = oneCard.getColor();
			// for wild cards, colors are set to their symbol
			if (color == null) {
				color = oneCard.getSymbol();
			}
			int currNumber = colorToNumber.get(color);
			colorToNumber.replace(color, currNumber + 1);
		}
	}
	
	/**
	 * The function that sets cardToPlay
	 */
	public abstract void pickCard();
	
	public abstract void pickColorForWild();
	
	
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

	public List<Card> getValidCards() {
		return validCards;
	}

	public Map<String, Integer> getColorToNumber() {
		return colorToNumber;
	}

	public String getColorToUse() {
		return colorToUse;
	}

	public void setColorToUse(String colorToUse) {
		this.colorToUse = colorToUse;
	}

	public String getAiType() {
		return aiType;
	}

	public void setAiType(String aiType) {
		this.aiType = aiType;
	}


}
