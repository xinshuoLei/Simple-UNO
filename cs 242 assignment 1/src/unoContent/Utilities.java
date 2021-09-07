package unoContent;

public class Utilities {
	
	// check if a card is a wild card
		public static boolean isWild(Card toCheck) {
			String symbol = toCheck.getSymbol();
			if (symbol.equals("wild") || symbol.equals("wild draw four")) {
				return true;
			}
			return false;
		}
	
	
	// check if a card is valid in the current turn
	public static boolean checkCardValidity(Card cardToMatch, Card cardPlayed) {
		// wild cards are always valid
		if (isWild(cardPlayed)) {
			return true;
		}
		// cardPlayed need to have the same symbol or color
		NonWildCard cardPlayedCasted = (NonWildCard) cardPlayed;
		NonWildCard cardToMatchCasted = (NonWildCard) cardToMatch;
		boolean colorMatch = (cardPlayedCasted.getColor() == cardToMatchCasted.getColor());
		boolean symbolMatch = (cardPlayedCasted.getSymbol() == cardToMatchCasted.getSymbol());
		return colorMatch || symbolMatch;
	}
	
}
