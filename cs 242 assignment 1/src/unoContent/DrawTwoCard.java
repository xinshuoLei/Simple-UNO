package unoContent;

/**
 * Class for draw two cards
 */
public class DrawTwoCard extends Card {

		public DrawTwoCard(String setSymbol, String setColor) {
			super(setSymbol, setColor);
		}
		
		/**
		 * function that print info about the card to stdout
		 */
		public void printCard() {
			System.out.println(getSymbol() + " " + getColor());
		}

}