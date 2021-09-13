package unoCard;

/**
 * The abstract class of a card. includes things that all cards have in common
 */

public abstract class Card {
	
	/**
	 * constant for the symbol of a skip card
	 */
	public static String SKIP = "skip";
	
	/**
	 * constant for the symbol of a reverse card
	 */
	public static String REVERSE = "reverse";
	
	/**
	 * constant for the symbol of a draw two card
	 */
	public static String DRAW2 = "draw two";
	
	/**
	 * constant for the symbol of a wild card
	 */
	public static String WILD = "wild";
	
	/**
	 * constant for the symbol of a wild draw four card
	 */
	public static String WILD_DRAW4 = "wild draw four";
	
	/**
	 * constant for the color yellow
	 */
	public static String YELLOW = "yellow";
	
	/**
	 * constant for the color red
	 */
	public static String RED = "red";
	
	
	/**
	 * constant for the color blue
	 */
	public static String BLUE = "blue";
	
	
	/**
	 * constant for the color green
	 */
	public static String GREEN = "green";
	
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
	 * Function that print out card info. used for testing and debug
	 */
	public void printCard() {
		if (color != null) {
			System.out.println(getSymbol() + " " + getColor());
		} else {
			System.out.println(getSymbol());
		}
	}
	
	/**
	 * Function that checks if a card is a wild
	 * @return true if the card is wild
	 */
	public boolean isWild() {
		if (symbol.equals("wild") || symbol.equals("wild draw four")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Function that check if a card is a number card
	 * reference:
	 * https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	 * @return true if the card is a number card
	 */
	
	public boolean isNumber() {
		return symbol.chars().allMatch( Character::isDigit );
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getColor() {
		return color;
	}

}
