package unoContent;

public abstract class Card {
	
	// The type of the card, including normal, reverse, draw two, wild, and wild draw four
	public String type;
	
	// The state the card:
	// 0 - in draw stack
	// 1 - in discard pile
	// 2 - in player hand
	// The default value is 0
	public int state = 0;
	
	// constructor for the Card class
	public Card(String setType) {
		type = setType;
	}
}
