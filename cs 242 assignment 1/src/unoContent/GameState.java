package unoContent;
import java.util.*;  

public class GameState {
	
	// list that store all available cards in a uno game
	public List<Card> allCards = new ArrayList<Card>();
	String[] allColors = {"yellow", "red", "green", "blue"};
	
	public GameState() {
		
		// initialize the list allCards
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j < 4; j++) {
				allCards.add(new NormalCard("normal", allColors[j], i));
				if (i != 0) {
					// two sets of 1-9
					allCards.add(new NormalCard("normal", allColors[j], i));
				}
			}
		}
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 2; y++) {
				allCards.add(new ReverseCard("reverse", allColors[x]));
				allCards.add(new SkipCard("skip", allColors[x]));
				allCards.add(new DrawTwoCard("draw two", allColors[x]));
			}
			allCards.add(new WildCard("wild"));
			allCards.add(new WildDrawFourCard("wild draw four"));
		}
		
		Collections.shuffle(allCards);  
		
	}
}
