package unoContent;

public class DrawTwoCard extends Card {
	// The color of the card, including red, yellow, green, and blue. 
	// For wild cards, the color is NULL.
	public String color;	
	
	public DrawTwoCard(String setType, String setColor) {
		super(setType);
		color = setColor;
	}
}
