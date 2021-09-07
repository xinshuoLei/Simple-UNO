package unoTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import unoContent.GameState;
import unoContent.NonWildCard;
import unoContent.Player;
import unoContent.Utilities;
import unoContent.WildCard;
import unoContent.Card;
import java.util.*;

class unoTest {
	
	void printCardPile(List<Card> pile) {
		for (Card oneCard : pile) {
			oneCard.printCard();
		}
	}
	
	
	@Test
	@DisplayName("Check initial draw pile")
	void testInitialPile() {
		GameState state = new GameState(null);
		assertEquals(108, state.getDrawPile().size(), "The size of the initial draw pile should be 108");
	}
	
	@Test
	@DisplayName("First card of draw pile should be random")
	void testShuffleCard() {
		GameState state = new GameState(null);
		Card firstCard = state.getDrawPile().get(0);
		System.out.print("The first card is: ");
		firstCard.printCard();
	}

	@Test
	@DisplayName("Check a player's initial stack of cards")
	void testPlayerInitialStack() {
		List<String> playerNames = new ArrayList<>(Arrays.asList("Player 1", "Player2", "Player3"));
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		for (Player onePlayer : state.getAllPlayers()) {
			assertEquals(7, onePlayer.getStack().size(), "Player's initial stack should have seven cards");
		}
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify wild card - part1")
	void testIsWild_Wild() {
		boolean return_val_wild = Utilities.isWild(new WildCard("wild"));
		assertEquals(true, return_val_wild, "isWild should return true for wild cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify wild card - part2")
	void testIsWild_WildDrawFour() {
		boolean return_val = Utilities.isWild(new WildCard("wild draw four"));
		assertEquals(true, return_val, "isWild should return true for wild draw four cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part1")
	void testIsWildFalse_Number() {
		boolean return_val= Utilities.isWild(new NonWildCard("0", "yellow"));
		assertEquals(false, return_val, "isWild should return false for normal number cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part2")
	void testIsWildFalse_Reverse() {
		boolean return_val = Utilities.isWild(new NonWildCard("reverse", "red"));
		assertEquals(false, return_val, "isWild should return false for reverse cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part3")
	void testIsWildFalse_DrawTwo() {
		boolean return_val_wild = Utilities.isWild(new NonWildCard("draw two", "green"));
		assertEquals(false, return_val_wild, "isWild should return false for draw two cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part4")
	void testIsWildFalse_Skip() {
		boolean return_val_wild = Utilities.isWild(new NonWildCard("skip", "blue"));
		assertEquals(false, return_val_wild, "isWild should return false for skip cards");
	}
	
	@Test
	@DisplayName("Check validity of wild cards")
	void testCheckValidity_Wild() {
		Card cardToMatch = new NonWildCard("1", "red");
		Card toCheck = new WildCard("wild");
		boolean validity = Utilities.checkCardValidity(cardToMatch, toCheck);
		assertEquals(true, validity, "wild cards are always valid");
	}
	
	@Test
	@DisplayName("Check validity of cards that don't match")
	void testCheckValidity_Invalid() {
		Card cardToMatch = new NonWildCard("1", "red");
		Card toCheck = new NonWildCard("reverse", "blue");
		boolean validity = Utilities.checkCardValidity(cardToMatch, toCheck);
		assertEquals(false, validity, "checkValidity should return false if neither symbol nor color match");
	}
	
	@Test
	@DisplayName("Check validity of cards that only match in color")
	void testCheckValidity_ColorMatch() {
		Card cardToMatch = new NonWildCard("skip", "red");
		Card toCheck = new NonWildCard("draw two", "red");
		boolean validity = Utilities.checkCardValidity(cardToMatch, toCheck);
		assertEquals(true, validity, "checkValidity should return true if color match");
	}
	
	@Test
	@DisplayName("Check validity of cards that only match in symbol")
	void testCheckValidity_SymbolMatch() {
		Card cardToMatch = new NonWildCard("2", "blue");
		Card toCheck = new NonWildCard("2", "red");
		boolean validity = Utilities.checkCardValidity(cardToMatch, toCheck);
		assertEquals(true, validity, "checkValidity should return true if symbol match");
	}
	
	@Test
	@DisplayName("Check validity of cards that match in color and symbol")
	void testCheckValidity_ColorAndSymbolMatch() {
		Card cardToMatch = new NonWildCard("3", "green");
		Card toCheck = new NonWildCard("3", "green");
		boolean validity = Utilities.checkCardValidity(cardToMatch, toCheck);
		assertEquals(true, validity, "checkValidity should return true if color and symbol match");
	}
	
	
}
