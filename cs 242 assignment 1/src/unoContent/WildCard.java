package unoContent;

// class for wild card and wild draw four card
public class WildCard extends Card {
	public WildCard(String setType) {
		super(setType);
	}
	
	public void printCard() {
		System.out.println(getSymbol());
	}
}
