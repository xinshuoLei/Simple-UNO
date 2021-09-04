package unoContent;

public class ReverseCard extends Card{
	// The color of the card, including red, yellow, green, and blue. 
	// For wild cards, the color is NULL.
	public String color;
	
	public ReverseCard(String setType, String setColor) {
		super(setType);
		color = setColor;
	}
}
