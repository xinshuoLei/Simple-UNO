package unoTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import unoContent.GameState;
import unoContent.NormalCard;

class unoTest {

	@Test
	@DisplayName("initialize 108 cards")
	void testInitialPile() {
		GameState state = new GameState();
		assertEquals(108, state.allCards.size(), "The size of the initial pile should be 108");
	}
	
	@Test
	void testShuffleCard() {
		GameState state = new GameState();
		System.out.println(state.allCards.get(0).type);
		if (state.allCards.get(0).type.equals("normal")) {
			NormalCard firstCard = (NormalCard) state.allCards.get(0);
			System.out.println(firstCard.number);
		}
	}

}
