package unoContent;

public class WildCard extends Card {
	public WildCard(String setType) {
		super(setType);
	}
	
	public void printCard() {
		System.out.println(getSymbol());
	}
}
