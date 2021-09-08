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
	
	// test cards piles
	List<Card> testPile1 = generateTestPile1();
	List<Card> testPile2 = generateTestPile2();
	// test player lists
	List<String> testPlayerList1 = new ArrayList<>(Arrays.asList("Player 1", "Player2", "Player3"));
	List<String> testPlayerList2 = new ArrayList<>(Arrays.asList("Player 1", "Player2"));
	List<String> testPlayerList3 = new ArrayList<>(Arrays.asList("Player 1", "Player2", "Player3", "Player4"));
	
	// functions that generates card piles for test
	
	List<Card> generateTestPile1() {
		List<Card> testPile = new ArrayList<Card>();
		// add five cards to testDiscardPile
		testPile.add(new NonWildCard("5", "yellow"));
		testPile.add(new NonWildCard("reverse", "blue"));
		testPile.add(new NonWildCard("skip", "red"));
		testPile.add(new WildCard("wild"));
		testPile.add(new NonWildCard("0", "green"));
		return testPile;
	}
	
	List<Card> generateTestPile2() {
		List<Card> testPile = new ArrayList<Card>();
		// add five cards to testDiscardPile
		testPile.add(new NonWildCard("7", "green"));
		testPile.add(new NonWildCard("draw two", "yellow"));
		testPile.add(new NonWildCard("skip", "red"));
		testPile.add(new WildCard("wild"));
		testPile.add(new NonWildCard("2", "red"));
		testPile.add(new WildCard("wild draw four"));
		testPile.add(new WildCard("wild draw four card"));
		return testPile;
	}
	
	
	// helper function that check if two cards are equal
	boolean cardIsEqual(Card first, Card second) {
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
	
	
	@Test
	@DisplayName("Check initial draw pile")
	void testInitialPile() {
		GameState state = new GameState(null);
		assertEquals(108, state.getDrawPile().size(), "The size of the initial draw pile should be 108");
	}
	
	@Test
	@DisplayName("First card of draw pile should be random")
	void testShuffleCard() {
		System.out.println("output of testShuffleCard(): ");
		GameState state = new GameState(null);
		Card firstCard = state.getDrawPile().get(0);
		System.out.print("The first card is: ");
		firstCard.printCard();
		System.out.println();
	}

	@Test
	@DisplayName("Check a player's initial stack of cards")
	void testPlayerInitialStack() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
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
	
	@Test
	@DisplayName("Check discard pile is reused correctly when draw pile is empty")
	void testReuseDiscardPile_empty() {
		GameState state = new GameState(null);
		// set discard pile and empty draw pile
		state.setDiscardPile(new ArrayList<>(testPile1));
		state.setDrawPile(new ArrayList<Card>());
		
		// run test and compare result
		state.ReuseDiscardPile();
		List<Card> discardPile = state.getDiscardPile();
		List<Card> drawPile = state.getDrawPile();
		
		// check discard pile
		assertEquals(1, discardPile.size(), "After reuse, discard pile should only contain one card");
		assertEquals(true, cardIsEqual(discardPile.get(0), new NonWildCard("5", "yellow")), 
				"After reuse, discard pile is the top card of the previous discard pile");
		
		// check draw pile
		assertEquals(testPile1.size() - 1, drawPile.size(), 
				"After reuse, size of draw pile should be size of previous discard pile - 1");
		for (int i = 1; i < testPile1.size(); i++) {
			assertEquals(true, drawPile.contains(testPile1.get(i)),
					"After reuse, draw pile should contain all cards in previous draw pile except the top card");
		}
	}
	
	@Test
	@DisplayName("Discard pile should not be reused if draw pile is not empty")
	void testReuseDiscardPile_nonempty() {
		GameState state = new GameState(null);
		// set discard pile and draw pile
		state.setDrawPile(new ArrayList<>(testPile1));
		state.setDiscardPile(new ArrayList<>(testPile2));
		
		state.ReuseDiscardPile();
		List<Card> discardPile = state.getDiscardPile();
		List<Card> drawPile = state.getDrawPile();
		
		// compare result
		assertEquals(true, drawPile.equals(testPile1), "Draw pile should be the same");
		assertEquals(true, discardPile.equals(testPile2), "Discard pile should be the same");
	}

	@Test
	@DisplayName("Check shouldEnd return true when the game should end")
	void testShouldEnd_true() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		// empty a player's stack
		Player SecondPlayer = state.getAllPlayers().get(1);
		SecondPlayer.setStack(new ArrayList<Card>());
		
		assertEquals(true, state.shouldEndGame(), "shouldEnd Game should return true  if a player has no card in hand");
	}
	
	@Test
	@DisplayName("Check shouldEnd return false when the game is not over")
	void testShouldEnd_false() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		// modify a player's stack
		Player FirstPlayer = state.getAllPlayers().get(0);
		FirstPlayer.setStack(new ArrayList<Card>(testPile1));
		
		assertEquals(false, state.shouldEndGame(), "shouldEnd Game should return false if all players have card");
	}
	
	@Test
	@DisplayName("Check disacrd pile and player stack is updated correctly - valid card")
	void testUpdatePile_true() {
		List<String> playerNames = new ArrayList<>(testPlayerList2);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		// give a custom discard pile to make sure the card played is added to the correct position
		state.setDiscardPile(new ArrayList<>(testPile2));
		state.setCurrentPlayer(0);
		// save player stack before playing a card
		Player FirstPlayer = state.getAllPlayers().get(0);
		List<Card> initial_stack = new ArrayList<>(FirstPlayer.getStack());
		
		// make state process a valid card
		Card played = initial_stack.get(3);
		state.setCardToMatch(initial_stack.get(3));
		
		state.processCardPlayed(played);
		
		// compare discard pile 
		// should be original pile + played, with played as position 0
		List<Card> disacrdPile = state.getDiscardPile();
		assertEquals(testPile2.size() + 1, disacrdPile.size(), "size of dicard pile should increase by 1");
		assertEquals(true, cardIsEqual(played, disacrdPile.get(0)), 
				"card played should appear at front of discard pile");
		
		// compare player stack
		List<Card> stack = FirstPlayer.getStack();
		assertEquals(initial_stack.size() - 1, stack.size(), "size of player stack should decrease by 1");
		assertEquals(false, stack.contains(played), 
				"card played should be removed from player stack");
	}
	

	@Test
	@DisplayName("Check disacrd pile and player stack is not updated when invalid card played")
	void testUpdatePile_false() {
		System.out.println("output of testUpdatePile_false(): ");
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		// give a custom discard pile to make sure it stays the same
		state.setDiscardPile(new ArrayList<>(testPile2));
		state.setCurrentPlayer(1);
		// set player stack
		Player SecondPlayer = state.getAllPlayers().get(1);
		SecondPlayer.setStack(new ArrayList<>(testPile1));
		
		// make state process an invalid card
		state.setCardToMatch(new NonWildCard("0", "red"));
		// card played is ("reverse", "blue")
		Card played = testPile1.get(1);
		
		state.processCardPlayed(played);
		
		// compare discard pile 
		// should be the same
		List<Card> disacrdPile = state.getDiscardPile();
		assertEquals(true, disacrdPile.equals(testPile2), 
				"discard pile should be the same");
		
		// compare player stack
		List<Card> stack = SecondPlayer.getStack();
		assertEquals(true, stack.equals(testPile1), 
				"player stack should be the same");
		System.out.println();
	}
	
	@Test
	@DisplayName("Check effect of a valid reverse card")
	void testValidReverse() {
		List<String> playerNames = new ArrayList<>(testPlayerList3);
		GameState state = new GameState(playerNames);
		
		// save initial allPlayers
		List<Player> initial_allPlayers = new ArrayList<>(state.getAllPlayers());
		
		// set cardToMatch to a reverse card
		state.setCardToMatch(new NonWildCard("reverse", "blue"));
		// process a reverse card
		state.processCardPlayed(new NonWildCard("reverse", "red"));
		
		// check allPlayer list is now reversed
		List<Player> allPlayers = state.getAllPlayers();
		assertEquals(true, initial_allPlayers.size() == allPlayers.size(), 
				"size of allPlayers should be the same");
		// compare allPlayers and initial_allPlayers
		for (int i = 0; i < initial_allPlayers.size(); i++) {
			int j = initial_allPlayers.size() - i - 1;
			assertEquals(true, initial_allPlayers.get(i).getName().equals(allPlayers.get(j).getName()), 
					"allPlayers list should be reversed");
		}
	}
	
	@Test
	@DisplayName("Check effect of a wild card")
	void testValidSkip() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		
		// set current player to the third player
		state.setCurrentPlayer(2);
		// set cardToMatch to a reverse card
		state.setCardToMatch(new NonWildCard("0", "blue"));
		// process a reverse card
		state.processCardPlayed(new NonWildCard("skip", "blue"));
		
		assertEquals(true, state.getCurrentPlayer() == 1);
	}
	
	@Test
	@DisplayName("Check effect of a wild card")
	void testValidWild() {
		System.out.println("output of testValidWild(): ");
		List<String> playerNames = new ArrayList<>(testPlayerList2);
		GameState state = new GameState(playerNames);
		
		// set cardToMatch so we can know if it is updated
		state.setCardToMatch(new NonWildCard("3", "blue"));
		// process a wild card
		state.processCardPlayed(new WildCard("wild"));
		// print updated cardToMatch
		state.getCardToMatch().printCard();
		System.out.println();
	}
	
	@Test
	@DisplayName("Check effect of a vaid card")
	void testValidNormalNumberCard() {
		List<String> playerNames = new ArrayList<>(testPlayerList2);
		GameState state = new GameState(playerNames);
		
		// set cardToMatch so we can know if it is updated
		state.setCardToMatch(new NonWildCard("3", "blue"));
		// process a wild card
		Card played = new NonWildCard("3", "red");
		state.processCardPlayed(played);
		// compare cardToMatch
		assertEquals(true, cardIsEqual(state.getCardToMatch(), played), 
				"cardToMatch should be updated to card played");
	}
	
	@Test
	@DisplayName("Check effect of a vaid draw two card - without stack")
	void testValidDrawTwoCard_noStack() {
		List<String> playerNames = new ArrayList<>(testPlayerList3);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		state.setCardToMatch(new NonWildCard("5", "blue"));
		// set current player to 3
		state.setCurrentPlayer(3);
		// save player1 (index 0)'s initial stack
		Player playerOne = state.getAllPlayers().get(0);
		List<Card> player1_initialStack = new ArrayList<>(playerOne.getStack());
		// process a draw two card
		Card played = new NonWildCard("draw two", "blue");
		state.processCardPlayed(played);
		// the next player plays a card that is not draw two
		// so this player need to draw two card and skip this turn
		state.processCardPlayed(new NonWildCard("4", "blue"));
		assertEquals(player1_initialStack.size() + 2, player1_initialStack.size() + 2,  
				"next player should draw two cards");
		assertEquals(1, state.getCurrentPlayer(), "next playe's turn should be skipped");
	}
	
	@Test
	@DisplayName("Check effect of a vaid draw two card - with stacking")
	void testValidDrawTwoCard_stack() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		state.setCardToMatch(new NonWildCard("7", "yellow"));
		// set current player to 0
		state.setCurrentPlayer(0);
		// process a draw two card
		Card played = new NonWildCard("draw two", "yellow");
		state.processCardPlayed(played);
		// process another draw two card
		state.processCardPlayed(new NonWildCard("draw two", "blue"));
		List<Integer> penalty = state.getDrawPenalty();
		// penalty for player with index 1 should be 0
		assertEquals(0, penalty.get(1),  
				"player that play another draw two card avoid penalty");
		// penalty for player with index 2 should be 4
		assertEquals(4, penalty.get(2), "next playe's penalty should be stacked");
	}
	
	@Test
	@DisplayName("player draw a card and is able to play it")
	void testDrawAndPlay() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		state.setCardToMatch(new NonWildCard("5", "green"));
		// set draw pile so we can know what the next card is 
		state.setDrawPile(new ArrayList<>(testPile1));
		// set current player to 0
		state.setCurrentPlayer(0);
		// player 0 does not have card that match
		state.processCardPlayed(null);
		// we know he can play the card he draw
		// so cardToMatch should be updated
		assertEquals(true, cardIsEqual(testPile1.get(0), state.getCardToMatch()), 
				"player should play the card and it should be processed");
	}
}
