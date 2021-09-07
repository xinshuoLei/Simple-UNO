package unoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.print.Printable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import unoContent.GameState;
import unoContent.NonWildCard;
import unoContent.Player;
import unoContent.Card;
import java.util.*;

class unoTest {
	
	void printCardPile(List<Card> pile) {
		for (Card oneCard : pile) {
			oneCard.printCard();
		}
	}
	
	
	@Test
	@DisplayName("initialize 108 cards")
	void testInitialPile() {
		GameState state = new GameState(null);
		assertEquals(108, state.getDrawPile().size(), "The size of the initial draw pile should be 108");
	}
	
	@Test
	@DisplayName("First card should be random after shuffle")
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
	@DisplayName("check invalid card played")
	void testInvalidCardPlayed() {
		GameState state = new GameState(null);
		// todo
	}
}
