package unoContent;

/**
 * Class for skip cards
 */
public class SkipCard extends Card {

		public SkipCard(String setSymbol, String setColor) {
			super(setSymbol, setColor);
		}
		
		/**
		 * function that print info about the card to stdout
		 */
		public void printCard() {
			System.out.println(getSymbol() + " " + getColor());
		}

}