package unoTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import unoCard.Card;
import unoCard.DrawTwoCard;
import unoCard.NumberCard;
import unoCard.ReverseCard;
import unoCard.SkipCard;
import unoCard.WildCard;
import unoGameLogic.GameState;
import unoGameLogic.Player;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;


/**
 * Unit tests for uno game logic 
 * set user input reference:
 * https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input 
 */
class unoTest {
	
	// test cards piles
	List<Card> testPile1 = generateTestPile1();
	List<Card> testPile2 = generateTestPile2();
	// test player lists
	List<String> testPlayerList1 = new ArrayList<>(Arrays.asList("Player 1", 
			"Player2", "Player3"));
	List<String> testPlayerList2 = new ArrayList<>(Arrays.asList("Player 1",
			"Player2"));
	List<String> testPlayerList3 = new ArrayList<>(Arrays.asList("Player 1",
			"Player2", "Player3", "Player4"));
	
	// functions that generates card piles for test
	
	List<Card> generateTestPile1() {
		List<Card> testPile = new ArrayList<Card>();
		// add five cards to testDiscardPile
		testPile.add(new NumberCard("5", Card.YELLOW));
		testPile.add(new ReverseCard(Card.REVERSE, Card.BLUE));
		testPile.add(new SkipCard(Card.SKIP, Card.RED));
		testPile.add(new WildCard(Card.WILD, null));
		testPile.add(new NumberCard("0", Card.GREEN));
		return testPile;
	}
	
	List<Card> generateTestPile2() {
		List<Card> testPile = new ArrayList<Card>();
		// add five cards to testDiscardPile
		testPile.add(new NumberCard("7", Card.GREEN));
		testPile.add(new DrawTwoCard(Card.DRAW2, Card.YELLOW));
		testPile.add(new SkipCard(Card.SKIP, Card.RED));
		testPile.add(new WildCard(Card.WILD, null));
		testPile.add(new NumberCard("2", Card.RED));
		testPile.add(new WildCard(Card.WILD_DRAW4, null));
		testPile.add(new WildCard(Card.WILD_DRAW4, null));
		return testPile;
	}
	
	
	// helper function that check if two cards are equal
	boolean cardIsEqual(Card first, Card second) {
		// if both are wild cards
		if (first.isWild() && second.isWild()) {
			return first.getSymbol() == second.getSymbol();
		} else if (first.isWild() || second.isWild()) {
			// only one card is wild
			return false;
		} else {
			// both are non-wild cards, check color and symbol
			return (first.getSymbol() == second.getSymbol()) 
					&& (first.getColor() == second.getColor());
		}
	}
	
	
	@Test
	@DisplayName("Check initial draw pile")
	void testInitialPile() {
		GameState state = new GameState(null);
		assertEquals(108, state.getDrawPile().size(), 
				"The size of the initial draw pile should be 108");
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
			assertEquals(7, onePlayer.getStack().size(), 
					"Player's initial stack should have seven cards");
		}
	}
	
	
	@Test
	@DisplayName("Utilites.isWild can identify wild card - part1")
	void testIsWild1() {
		boolean returnValWild = (new WildCard(Card.WILD, null)).isWild();
		assertEquals(true, returnValWild, "isWild should return true for wild cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify wild card - part2")
	void testIsWild2() {
		boolean returnVal = (new WildCard(Card.WILD_DRAW4, null)).isWild();
		assertEquals(true, returnVal, 
				"isWild should return true for wild draw four cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part1")
	void testIsWildFalse1() {
		boolean returnVal= (new NumberCard("0", Card.YELLOW)).isWild();
		assertEquals(false, returnVal, 
				"isWild should return false for normal number cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part2")
	void testIsWildFalse2() {
		boolean returnVal = (new ReverseCard(Card.REVERSE, Card.RED).isWild());
		assertEquals(false, returnVal, 
				"isWild should return false for reverse cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part3")
	void testIsWildFalse3() {
		boolean returnValWild = (new DrawTwoCard(Card.DRAW2, Card.GREEN)).isWild();
		assertEquals(false, returnValWild, 
				"isWild should return false for draw two cards");
	}
	
	@Test
	@DisplayName("Utilites.isWild can identify non wild card - part4")
	void testIsWildFalse4() {
		boolean returnValWild = (new SkipCard(Card.SKIP, Card.BLUE)).isWild();
		assertEquals(false, returnValWild, 
				"isWild should return false for skip cards");
	}
	
	@Test
	@DisplayName("Check validity of wild cards")
	void testCheckValidityWild() {
		Card cardToMatch = new NumberCard("1", Card.RED);
		Card toCheck = new WildCard(Card.WILD, null);
		boolean validity = GameState.checkCardValidity(cardToMatch, null, toCheck);
		
		assertEquals(true, validity, "wild cards are always valid");
	}
	
	@Test
	@DisplayName("Check validity of cards that don't match")
	void testCheckValidityFalse() {
		Card cardToMatch = new NumberCard("1", Card.RED);
		Card toCheck = new ReverseCard(Card.REVERSE, Card.BLUE);
		boolean validity = GameState.checkCardValidity(cardToMatch, null, toCheck);
		
		assertEquals(false, validity, 
				"checkValidity should return false if neither symbol nor color match");
	}
	
	@Test
	@DisplayName("Check validity of cards that only match in color")
	void testCheckValidity1() {
		Card cardToMatch = new SkipCard(Card.SKIP, Card.RED);
		Card toCheck = new DrawTwoCard(Card.DRAW2, Card.RED);
		boolean validity = GameState.checkCardValidity(cardToMatch, null, toCheck);
		assertEquals(true, validity,
				"checkValidity should return true if color match");
	}
	
	@Test
	@DisplayName("Check validity of cards that only match in symbol")
	void testCheckValidity2() {
		Card cardToMatch = new NumberCard("2", Card.BLUE);
		Card toCheck = new NumberCard("2", Card.RED);
		boolean validity = GameState.checkCardValidity(cardToMatch, null, toCheck);
		assertEquals(true, validity, 
				"checkValidity should return true if symbol match");
	}
	
	@Test
	@DisplayName("Check validity of cards that match in color and symbol")
	void testCheckValidity3() {
		Card cardToMatch = new NumberCard("3", Card.GREEN);
		Card toCheck = new NumberCard("3", Card.GREEN);
		boolean validity = GameState.checkCardValidity(cardToMatch, null, toCheck);
		assertEquals(true, validity, 
				"checkValidity should return true if color and symbol match");
	}
	
	@Test
	@DisplayName("Check discard pile is reused correctly when draw pile is empty")
	void testReuseDiscardPile1() {
		GameState state = new GameState(null);
		// set discard pile and empty draw pile
		state.setDiscardPile(new ArrayList<>(testPile1));
		state.setDrawPile(new ArrayList<Card>());
		
		// run test and compare result
		state.ReuseDiscardPile();
		List<Card> discardPile = state.getDiscardPile();
		List<Card> drawPile = state.getDrawPile();
		
		// check discard pile
		assertEquals(1, discardPile.size(), 
				"After reuse, discard pile should only contain one card");
		assertEquals(true, cardIsEqual(discardPile.get(0), new NumberCard("5", Card.YELLOW)), 
				"After reuse, discard pile is the top card of the previous discard pile");
		
		// check draw pile
		assertEquals(testPile1.size() - 1, drawPile.size(), 
				"After reuse, size of draw pile should be size of previous discard pile - 1");
		for (int i = 1; i < testPile1.size(); i++) {
			assertEquals(true, drawPile.contains(testPile1.get(i)),
					"After reuse, draw pile should contain all cards"
					+ " in previous draw pile except the top card");
		}
	}
	
	@Test
	@DisplayName("Discard pile should not be reused if draw pile is not empty")
	void testReuseDiscardPile2() {
		GameState state = new GameState(null);
		// set discard pile and draw pile
		state.setDrawPile(new ArrayList<>(testPile1));
		state.setDiscardPile(new ArrayList<>(testPile2));
		
		state.ReuseDiscardPile();
		List<Card> discardPile = state.getDiscardPile();
		List<Card> drawPile = state.getDrawPile();
		
		// compare result
		assertEquals(true, drawPile.equals(testPile1), 
				"Draw pile should be the same");
		assertEquals(true, discardPile.equals(testPile2), 
				"Discard pile should be the same");
	}

	@Test
	@DisplayName("Check shouldEnd return true when the game should end")
	void testShouldEndTrue() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		// empty a player's stack
		Player SecondPlayer = state.getAllPlayers().get(1);
		SecondPlayer.setStack(new ArrayList<Card>());
		
		assertNotEquals(-1, state.checkWinner(), 
				"shouldEnd Game should return true if a player has no card in hand");
	}
	
	@Test
	@DisplayName("Check shouldEnd return false when the game is not over")
	void testShouldEndFalse() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		// modify a player's stack
		Player FirstPlayer = state.getAllPlayers().get(0);
		FirstPlayer.setStack(new ArrayList<Card>(testPile1));
		
		assertEquals(-1, state.checkWinner(), 
				"shouldEnd Game should return false if all players have card");
	}
	
	@Test
	@DisplayName("Check disacrd pile and player stack is updated correctly - valid card")
	void testUpdatePileTrue() {
		List<String> playerNames = new ArrayList<>(testPlayerList2);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		// give a custom discard pile to make sure the card played 
		// is added to the correct position
		state.setDiscardPile(new ArrayList<>(testPile2));
		state.setCurrentPlayer(0);
		// save player stack before playing a card
		Player FirstPlayer = state.getAllPlayers().get(0);
		List<Card> initialStack = new ArrayList<>(FirstPlayer.getStack());
		
		// make state process a valid card
		Card played = initialStack.get(3);
		state.setCardToMatch(initialStack.get(3));
		
		state.processCardPlayed(played);
		
		// compare discard pile 
		// should be original pile + played, with played as position 0
		List<Card> disacrdPile = state.getDiscardPile();
		assertEquals(testPile2.size() + 1, disacrdPile.size(), 
				"size of dicard pile should increase by 1");
		assertEquals(true, cardIsEqual(played, disacrdPile.get(0)), 
				"card played should appear at front of discard pile");
		
		// compare player stack
		List<Card> stack = FirstPlayer.getStack();
		assertEquals(initialStack.size() - 1, stack.size(), 
				"size of player stack should decrease by 1");
		assertEquals(false, stack.contains(played), 
				"card played should be removed from player stack");
	}
	

	@Test
	@DisplayName("Check disacrd pile and player stack is not updated - invalid card")
	void testUpdatePileFalse() {
		System.out.println("output of testUpdatePileFalse(): ");
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
		state.setCardToMatch(new NumberCard("0", Card.RED));
		// card played is (Card.REVERSE, Card.BLUE)
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
		List<Player> initialAllPlayers = new ArrayList<>(state.getAllPlayers());
		
		// set cardToMatch to a reverse card
		state.setCardToMatch(new ReverseCard(Card.REVERSE, Card.BLUE));
		// process a reverse card
		state.processCardPlayed(new ReverseCard(Card.REVERSE, Card.RED));
		
		// check allPlayer list is now reversed
		List<Player> allPlayers = state.getAllPlayers();
		assertEquals(true, initialAllPlayers.size() == allPlayers.size(), 
				"size of allPlayers should be the same");
		// compare allPlayers and initialAllPlayers
		for (int i = 0; i < initialAllPlayers.size(); i++) {
			int j = initialAllPlayers.size() - i - 1;
			String nameI = initialAllPlayers.get(i).getName();
			String nameJ = allPlayers.get(j).getName();
			assertEquals(true, nameI.equals(nameJ), 
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
		state.setCardToMatch(new NumberCard("0", Card.BLUE));
		// process a reverse card
		state.processCardPlayed(new SkipCard(Card.SKIP, Card.BLUE));
		
		assertEquals(true, state.getCurrentPlayer() == 1);
	}
	
	@Test
	@DisplayName("Check effect of a wild card")
	void testValidWild() {
		System.out.println("output of testValidWild(): ");
		List<String> playerNames = new ArrayList<>(testPlayerList2);
		GameState state = new GameState(playerNames);
		
		// set cardToMatch so we can know if it is updated
		state.setCardToMatch(new NumberCard("3", Card.BLUE));
		// process a wild card
		state.processCardPlayed(new WildCard(Card.WILD, null));
		// print updated cardToMatch
		System.out.print("cardToMatch is: ");
		state.getCardToMatch().printCard();
		System.out.println();
	}
	
	@Test
	@DisplayName("Check effect of a vaid card")
	/*
	 * included custom rule split draw
	 */
	void testValidNormalNumberCard() {
		List<String> playerNames = new ArrayList<>(testPlayerList2);
		GameState state = new GameState(playerNames);
		
		// set cardToMatch so we can know if it is updated
		state.setCardToMatch(new NumberCard("3", Card.BLUE));
		// process a wild card
		Card played = new NumberCard("3", Card.RED);
		state.processCardPlayed(played);
		// compare cardToMatch
		assertEquals(true, cardIsEqual(state.getCardToMatch(), played), 
				"cardToMatch should be updated to card played");
	}
	
	@Test
	@DisplayName("Check effect of a vaid draw two card - without stack")
	/*
	 * included custom rule split draw
	 */
	void testValidDrawTwoCardNoStack() {
		List<String> playerNames = new ArrayList<>(testPlayerList3);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		state.setCardToMatch(new NumberCard("5", Card.BLUE));
		
		// set current player to 3
		state.setCurrentPlayer(3);
		
		// save player1 (index 0)'s initial stack
		Player playerOne = state.getAllPlayers().get(0);
		List<Card> player1InitialStack = new ArrayList<>(playerOne.getStack());
		
		// process a draw two card
		Card played = new DrawTwoCard(Card.DRAW2, Card.BLUE);
		state.processCardPlayed(played);
		
		// the next player plays a card that is not draw two
		// so this player need to draw two card and skip this turn
		state.processCardPlayed(new NumberCard("4", Card.BLUE));
		assertEquals(player1InitialStack.size() + 1, player1InitialStack.size() + 1,  
				"next player should draw one cards because of draw split");
		assertEquals(1, state.getCurrentPlayer(), 
				"next playe's turn should be skipped");
	}
	
	@Test
	@DisplayName("Check effect of a vaid draw two card - with stacking")
	void testValidDrawTwoCardStack() {
		List<String> playerNames = new ArrayList<>(testPlayerList3);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		state.setCardToMatch(new NumberCard("7", Card.YELLOW));
		// set current player to 0
		state.setCurrentPlayer(0);
		
		// player1 plays draw two, so penalty for player2 and 3 should be 1 
		Card played = new DrawTwoCard(Card.DRAW2, Card.YELLOW);
		state.processCardPlayed(played);
		
		// player2 plays another draw two
		// penalty for player3 should be 1+1+1 = 3
		// penalty for player4 should be 0+1 = 1
		state.processCardPlayed(new DrawTwoCard(Card.DRAW2, Card.BLUE));
		List<Integer> penalty = state.getDrawPenalty();
		
		// penalty for player with index 1 should be 0
		assertEquals(0, penalty.get(1),  
				"player that play another draw two card avoid penalty");
		// penalty for player with index 2 should be 2
		assertEquals(3, penalty.get(2), "next 2 players' penalty should be stacked");
		// penalty for player with index 3 should be 2
		assertEquals(1, penalty.get(3), "next 2 players' penalty should be stacked");
	}
	
	@Test
	@DisplayName("Check effect of a valid wild draw four card - no stacking")
	void testValidWildDrawFour() {
		System.out.println("output of testValidWildDrawFour(): ");
		List<String> playerNames = new ArrayList<>(testPlayerList3);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
	
		// set current player to 1
		state.setCurrentPlayer(1);
		
		// set input to 0, meaning the user chose yellow as the next color
		// backup System.in to restore it later
		InputStream sysInBackup = System.in; 
		ByteArrayInputStream in = new ByteArrayInputStream("\n0".getBytes());
		System.setIn(in);
		
		// process a wild draw four card
		Card played = new WildCard(Card.WILD_DRAW4, null);
		state.processCardPlayed(played);
		
		// next player should have a penalty of four
		assertEquals(4, state.getDrawPenalty().get(2), "next player should get a draw "
				+ "penalty of 4");
		
		// cardToMatch should be set to wild draw four, yellow
		Card expectedCardToMatch = new WildCard(Card.WILD_DRAW4, Card.YELLOW);
		assertEquals(true, cardIsEqual(state.getCardToMatch(), expectedCardToMatch));
		System.setIn(sysInBackup);
		System.out.println("");
	}
	
	
	@Test
	@DisplayName("player draw a card and is able to play it")
	void testDrawAndPlay() {
		List<String> playerNames = new ArrayList<>(testPlayerList1);
		GameState state = new GameState(playerNames);
		state.initializePlayerStack();
		
		state.setCardToMatch(new NumberCard("5", Card.GREEN));
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
	
	@Test
	@DisplayName("Check special rule: cardBefore Special") 
	void testCardBeforeSpecial() {
		List<String> playerNames = new ArrayList<>(testPlayerList3);
		GameState state = new GameState(playerNames);
		
		// set cardToMatch so preceding card can be processed
		state.setCardToMatch(new NumberCard("1", Card.BLUE));
		
		// Process a blue skip card first
		Card firstCard = new SkipCard(Card.SKIP, Card.BLUE);
		state.processCardPlayed(firstCard);
		
		// then process a red skip card
		Card secondCard = new SkipCard(Card.SKIP, Card.RED);
		state.processCardPlayed(secondCard);
		
		// cardToMatch should be red skip 
		// and cardBeforeSpecial should be blue skip
		assertEquals(true, cardIsEqual(secondCard, state.getCardToMatch()),
				"cardToMatch should be updated when a valid card is played");
		assertEquals(true, cardIsEqual(firstCard, state.getCardBeforeSpecial()),
				"cardBeforeSpecial should be updtaed after a special card is played");
		
		// process another card: blue 5, this should be processed successfully
		Card thirdCard = new NumberCard("5", Card.BLUE);
		state.processCardPlayed(thirdCard);
		assertEquals(true, cardIsEqual(thirdCard, state.getCardToMatch()),
				"card matching cardBeforeSpecial should be processed after a "
				+ "special card is played");
	}
}
