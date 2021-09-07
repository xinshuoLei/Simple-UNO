package unoContent;

public abstract class Card {
	
	// The symbol of the card, including 0-9, reverse, draw two, wild, and wild draw four
	private String symbol;
	
	// The state the card:
	// 0 - in draw stack
	// 1 - in discard pile
	// 2 - in player hand
	// The default value is 0
	public int state = 0;
	
	// constructor for the Card class
	public Card(String setSymbol) {
		symbol = setSymbol;
	}
	
	// function that print out card info. used for testing and debug
	// card info format: type, color (if applicable), number (if applicable)
	public abstract void printCard();
	
	public String getSymbol() {
		return symbol;
	}
}
