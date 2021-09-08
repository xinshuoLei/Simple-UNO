package unoContent;

// class for non wild cards, including normal number, skip, draw two, reverse
public class NonWildCard extends Card {
		// The color of the card, including red, yellow, green, and blue. 
		// For wild cards, the color is NULL.
		private String color;	
		
		public NonWildCard(String setSymbol, String setColor) {
			super(setSymbol);
			color = setColor;
		}
		
		public void printCard() {
			System.out.println(getSymbol() + " " + color);
		}
		
		public String getColor() {
			return color;
		}
}
