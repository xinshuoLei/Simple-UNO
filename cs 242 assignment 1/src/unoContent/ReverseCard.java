package unoContent;

/**
 * Class for reverse cards
 */
public class ReverseCard extends Card {

		public ReverseCard(String setSymbol, String setColor) {
			super(setSymbol, setColor);
		}
		
		/**
		 * function that print info about the card to stdout
		 */
		public void printCard() {
			System.out.println(getSymbol() + " " + getColor());
		}

}
