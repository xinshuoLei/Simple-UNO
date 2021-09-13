package unoContent;

/**
 * The abstract class of a card. includes things that all cards have in common
 */

public abstract class Card {
	
	/**
	 * The symbol of the card, including 0-9, reverse, draw two, wild, and wild draw four
	 */
	private String symbol;
	
	/**
	 * The color of the card
	 * Color of wild cards is null when it is not played by a player
	 * If it is played by a player, then the color is the color chosen by the player
	 */
	private String color;
	
	
	/**
	 * The state the card:
	 * 0 - in draw stack
	 * 1 - in discard pile
	 * 2 - in player hand
	 * The default value is 0
	 */
	public int state = 0;
	
	/**
	 * Constructor of the card class
	 * @param setSymbol symbol of the card created
	 * @param setColor color of the card created
	 */
	public Card(String setSymbol, String setColor) {
		symbol = setSymbol;
		color = setColor;
	}
	
	
	/**
	 * function that print out card info. used for testing and debug
	 * card info format: symbol, number (if applicable)
	 */
	public abstract void printCard();
	
	/**
	 * function that checks if a card is a wild
	 * @param toCheck the card to check
	 * @return true if the card is wild
	 */
	public static boolean isWild(Card toCheck) {
		String symbol = toCheck.getSymbol();
		if (symbol.equals("wild") || symbol.equals("wild draw four")) {
			return true;
		}
		return false;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getColor() {
		return color;
	}

}
