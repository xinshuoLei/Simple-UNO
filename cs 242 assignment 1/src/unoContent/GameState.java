package unoContent;

import java.util.*; 


/**
 * The class that includes functions and variables related to a game state
 */
public class GameState {
	
	/**
	 * Draw pile for the uno game. 
	 * index 0 indicates the top card of the pile, i.e. the first card to be drawn
	 */
	private List<Card> drawPile = new ArrayList<Card>();
	
	/**
	 * Discard pile. index 0 indicates the top card of the pile, i.e. the card to match
	 */
	private List<Card> discardPile = new ArrayList<Card>();
	
	/**
	 * List of all player names
	 */
	private List<Player> allPlayers = new ArrayList<Player>();
	
	
	/**
	 * Each number corresponds to the draw penalty for a player in allPlayers
	 * the draw penalty of the current player will be checked in processCardPlayed
	 * Non-zero value indicates the number of cards the player need to draw
	 */
	private List<Integer> drawPenalty = new ArrayList<Integer>();
	
	/**
	 * Array of all possible colors of uno card
	 */
	private String[] allColors = {"yellow", "red", "green", "blue"};
	
	/**
	 * The card to match in each turn. updated in processCardPlayed and initializePlayerStack()
	 */
	private Card cardToMatch = null;
	
	/**
	 * The player that needs to play a card. represented by the player's index in allPlayer's list
	 */
	private int currentPlayer = 0;
	
	
	/**
	 * Constructor of the GameState class
	 * @param playerNames List of player names
	 */
	public GameState(List<String> playerNames) {
		
		initializeDrawPile();
		
		Collections.shuffle(drawPile);
		
		// initialize the list allPlayers and drawPenalty
		if (playerNames != null) {
			for (int z = 0; z < playerNames.size(); z++) {
				allPlayers.add(new Player(playerNames. get(z)));	
				drawPenalty.add(0);
			}
		}
	}

	/**
	 * Function that initialize the seven initial card in each player's stack
	 */
	public void initializePlayerStack() {
		// Give each player 7 cards to start with
		for (int drawItr = 0; drawItr < 7; drawItr++) {
			for (int n = 0; n < allPlayers.size(); n++) {
				Player currPlayer = allPlayers.get(n);
				currPlayer.drawCard(new ArrayList<>(drawPile), 1, false, null);
				// update the draw pile after a card is drawn
				drawPile.remove(0);
			}
		}
		
		// after each player draw 7 cards 
		// the top card on the drawPile is moved to the discardPile and 
		// becomes the first card to match
		cardToMatch = drawPile.get(0);
		drawPile.remove(0);
		discardPile.add(0, cardToMatch);
	}
	
	
	/**
	 * Function that check if the game should end, 
	 * i.e. a player has 0 cards in hand
	 * @return true if the game should end
	 */
	public boolean shouldEndGame() {
		for (Player onePlayer : allPlayers) {
			if (onePlayer.getStack().size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Function that reuse the discard pile as draw pile when draw pile is empty
	 */
	public void ReuseDiscardPile() {
		// check if draw pile is empty
		// this should be down in the main game loop,
		// but since we don't have it this week, just check it here
		if (drawPile.size() == 0) {
			List<Card> discardPileCopy = new ArrayList<Card>(discardPile);
			
			// set top card aside
			discardPileCopy.remove(0);
			
			// shuffle discard pile and make it the new draw pile
			Collections.shuffle(discardPileCopy);
		    drawPile = discardPileCopy;
		    
		    // update discard pile to only include the top card
		    while (discardPile.size() != 1) {
		    	discardPile.remove(discardPile.size() - 1);
		    }
		}
	}
	
	
	/**
	 * Called in the main loop in each turn after the call to Player.playCard()
	 * @param played the card played by the player. returned by Player.playCard()
	 * @return if the card is processed successfully
	 */
	public boolean processCardPlayed(Card played) {
		
		Player currentPlayer_ = allPlayers.get(currentPlayer);

		// check if the player has a non-zero penalty
		 boolean applyPenalty = checkPenalty(played);
			if (applyPenalty) {
				// apply penalty and skip current player's turn
				currentPlayer_.drawCard(new ArrayList<>(drawPile), 
									drawPenalty.get(currentPlayer), false, null);
				for (int i = 0; i < drawPenalty.get(currentPlayer); i++) {
					drawPile.remove(0);
				}
				incrementCurrentPlayer();
				return false;
			}
		
		// player has no card that matches cardToMatch, 
		// so draw from stack
		if (played == null) {
			Card drawedPlayed = currentPlayer_.drawCard(new ArrayList<>(drawPile), 
													1, true, cardToMatch);
			drawPile.remove(0);
			// the card just drawn by the player is not valid to play
			if (drawedPlayed == null) {
				// player skip this turn, finish processing 
				incrementCurrentPlayer();
				return false;
			} else {
				// player played the card drawn, so process this card
				return processCardPlayed(drawedPlayed);
			}
		}
		
		String playedSymbol = played.getSymbol();
		
		// when the player plays an invalid card, end processing the card
		boolean isValid = GameState.checkCardValidity(cardToMatch, played);
		if (!isValid) {
			System.out.println("This card is invalid. Pick another card to play");
			return false;
		}
		
		if (playedSymbol.equals("wild")) {
			processWildCard("wild");
		} else if (playedSymbol.equals("wild draw four")) {
			// process wild feature of the card first
			processWildCard("wild draw four");
			// record the penalty of drawing four cards for next player
			int penalty = 4;
			stackPenalty(penalty);
		} else {
			// played a non-wild card, update cardToMatch
			processNonWildCard(played);
		}
		
		// update discard pile and player's pile
		discardPile.add(0, played);
		currentPlayer_.removeCardFromStack(played);
			
		// next player's turn
		incrementCurrentPlayer();
		return true;
	}
	
	/**
	 * Function that checks if the player has a non-zero penalty
	 * If so, check if the penalty should be played
	 * @param the card played
	 * @return true if a penalty should be applied
	 */
		
	private boolean checkPenalty(Card played) {
		boolean applyPenalty = false;
		if (drawPenalty.get(currentPlayer) != 0) {
			if (played == null) {
				// if player has no card to play, apply penalty
				applyPenalty = true;
			} else if (cardToMatch.getSymbol().equals("draw two") && 
						!played.getSymbol().equals("draw two")) {
				// if last card is draw two
				// execute penalty if the player doesn't play a draw two card
				applyPenalty = true;
			} else if (cardToMatch.getSymbol().equals("wild draw four") && 
					!played.getSymbol().equals("wild draw four")) {
				// if last card is draw two
				// execute penalty if the player doesn't play a draw two card
				applyPenalty = true;
			}
		}
		return applyPenalty;
	}

	/**
	 * Function that process a non-wild card
	 * @param played the card to process
	 */
	private void processNonWildCard(Card played) {
		String playedSymbol = played.getSymbol();
		cardToMatch = played;
		if (playedSymbol.equals("skip")) {
			incrementCurrentPlayer();
		} else if (playedSymbol.equals("draw two")) {
			// record the penalty of drawing two cards for next player
			int penalty = 2;
			stackPenalty(penalty);
		} else if (playedSymbol.equals("reverse")) {
			Collections.reverse(allPlayers);
			Collections.reverse(drawPenalty);
		}
	}

	/**
	 * Function that stack the draw penalty of the current player 
	 * to the next player
	 * @param penalty penalty that should be added to next player
	 */
	private void stackPenalty(int penalty) {
		if (currentPlayer == allPlayers.size() - 1) {
			// if the currentPlayer is the last player in allPlayers
			// next player will be the first player in allPlayers
			drawPenalty.set(0, penalty + drawPenalty.get(currentPlayer));
		} else {
			drawPenalty.set(currentPlayer + 1, 
							penalty + drawPenalty.get(currentPlayer));
		}
		// remove penalty for current player
		drawPenalty.set(currentPlayer, 0);
	}

	/**
	 * increment the variable currentPlayer to indicate it it next player's turn
	 */
	private void incrementCurrentPlayer() {
		if (currentPlayer == allPlayers.size() - 1) {
			// if the currentPlayer is the last player in allPlayers
			// next player will be the first player in allPlayers
			currentPlayer = 0;
		} else {
			currentPlayer += 1;
		}
	}
	
	/**
	 * Function that process a wild card. 
	 * Specifically, ask the player to choose a color
	 * @param symbol the symbol of the wild card
	 */
	private void processWildCard(String symbol) {
		while(true) {
			System.out.println("plase choose the next color to be matched");
			System.out.println
			("enter 0 for yellow, 1 for red, 2 for green, or 3 for blue");
			// ask for user keyboard input
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			// if choice is valid, change color to match to a card with 
			// no symbol and the selected color and break out the loop
			if (choice == 1 || choice == 2 || choice == 3 || choice == 0) {
				// set cardToMatch to account for 
				// the fact last played card is wild draw four
				// and the color player selected
				cardToMatch = new WildCard(symbol, allColors[choice]);
				scan.close();
				break;
			}
			// for invalid input, ask the user to try again
		}
	}

	/**
	 * Function that initialize the drawPile with 108 cards in the uno game
	 */
	private void initializeDrawPile() {
		
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j < 4; j++) {
				drawPile.add(new NumberCard(String.valueOf(i), allColors[j]));
				if (i != 0) {
					// two sets of 1-9
					drawPile.add(new NumberCard(String.valueOf(i), allColors[j]));
				}
			}
		}
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 2; y++) {
				// two reverse card, two skip cards 
				// and two draw two cards for each color
				drawPile.add(new ReverseCard("reverse", allColors[x]));
				drawPile.add(new SkipCard("skip", allColors[x]));
				drawPile.add(new DrawTwoCard("draw two", allColors[x]));
			}
			// four wild card and four wild draw four cards
			drawPile.add(new WildCard("wild", null));
			drawPile.add(new WildCard("wild draw four", null));
		}
	
	}
	
	/**
	 * Check if the card played is valid by comparing color and symbol
	 * @param cardToMatch the card to match
	 * @param cardPlayed the card played by the player
	 * @return return if the card is valid
	 */
	public static boolean checkCardValidity(Card cardToMatch, Card cardPlayed) {
		// wild cards are always valid
		if (Card.isWild(cardPlayed)) {
			return true;
		}
		// cardPlayed need to have the same symbol or color
		boolean colorMatch = (cardPlayed.getColor() == cardToMatch.getColor());
		boolean symbolMatch = (cardPlayed.getSymbol() == cardToMatch.getSymbol());
		return colorMatch || symbolMatch;
	}

	
	/**
	 * Functions below are getters and setters
	 */ 
	
	public List<Card> getDrawPile() {
		return drawPile;
	}


	public List<Card> getDiscardPile() {
		return discardPile;
	}


	public List<Player> getAllPlayers() {
		return allPlayers;
	}

	public Card getCardToMatch() {
		return cardToMatch;
	}

	public void setCardToMatch(Card cardToMatch) {
		this.cardToMatch = cardToMatch;
	}

	public void setDrawPile(List<Card> drawPile) {
		this.drawPile = drawPile;
	}

	public void setDiscardPile(List<Card> discardPile) {
		this.discardPile = discardPile;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<Integer> getDrawPenalty() {
		return drawPenalty;
	}

	

}
