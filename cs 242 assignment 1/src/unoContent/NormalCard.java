package unoContent;

public class NormalCard extends Card {
		// The color of the card, including red, yellow, green, and blue. 
		// For wild cards, the color is NULL.
		public String color;
		
		// The number of the card, including numbers 1-9
		// For non-normal cards, the number is -1
		public int number;
		
		public NormalCard(String setType, String setColor, int setNumber) {
			super(setType);
			color = setColor;
			number = setNumber;
		}
}
