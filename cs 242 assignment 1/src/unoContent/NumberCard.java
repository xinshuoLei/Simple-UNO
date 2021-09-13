package unoContent;

/**
 * Class for number cards
 */
public class NumberCard extends Card {

		public NumberCard(String setSymbol, String setColor) {
			super(setSymbol, setColor);
		}
		
		/**
		 * function that print info about the card to stdout
		 */
		public void printCard() {
			System.out.println(getSymbol() + " " + getColor());
		}

}
