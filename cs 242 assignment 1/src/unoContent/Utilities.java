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
	
	
	// helper function that check if two cards are equal
	public static boolean cardIsEqual(Card first, Card second) {
		// if both are wild cards
		if (Utilities.isWild(first) && Utilities.isWild(second)) {
			return first.getSymbol() == second.getSymbol();
		} else if (Utilities.isWild(first) || Utilities.isWild(second)) {
			// only one card is wild
			return false;
		} else {
			// both are non-wild cards, check color and symbol
			NonWildCard first_ = (NonWildCard) first;
			NonWildCard second_ = (NonWildCard) second;
			return (first_.getSymbol() == second_.getSymbol()) && (first_.getColor() == second_.getColor());
		}
	}
}
