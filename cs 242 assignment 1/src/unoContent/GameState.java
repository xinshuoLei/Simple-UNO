package unoContent;

import java.util.*; 

// Note: This implementation of UNO does NOT support stacking skip cards


public class GameState {
	
	// draw pile for the uno game. index 0 indicates the top card of the pile, i.e. the first 
	// card to be drawn
	private List<Card> drawPile = new ArrayList<Card>();
	
	// discard pile. index 0 indicates the top card of the pile, i.e. the card to match
	private List<Card> discardPile = new ArrayList<Card>();
	
	private List<Player> allPlayers = new ArrayList<Player>();
	
	// each number corresponds to the draw penalty for a player in allPlayers
	// initial value for each player is 0
	// the draw penalty of the current player will be checked in processCardPlayed
	// Specifically, 
	// 1. if a player has a non-zero penalty and plays a draw two card or
	// wild draw four card, that player avoid the penalty and the penalty is stacked 
	// to the next player
	// 2. if a player has a non-zero penalty and plays a card other than the two types
	// mentioned in 1, then the player has to skip this round, draw cards, and the card that player 
	// played will not be processed
	private List<Integer> drawPenalty = new ArrayList<Integer>();
	
	private String[] allColors = {"yellow", "red", "green", "blue"};
	
	// the card to match in each turn. updated in processCardPlayed and initializePlayerStack()
	private Card cardToMatch = null;
	
	// the player that needs to play a card. represented by the player's index in allPlayer's list
	private int currentPlayer = 0;
	
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

	public void initializePlayerStack() {
		// Give each player 7 cards to start with
		for (int drawItr = 0; drawItr < 7; drawItr++) {
			for (int n = 0; n < allPlayers.size(); n++) {
				Player currPlayer = allPlayers.get(n);
				currPlayer.drawCard(drawPile, 1);
				// update the draw pile after a card is drawn
				drawPile.remove(0);
			}
		}
		// after each player draw 7 cards, the top card on the drawPile is moved to the discardPile and 
		// becomes the first card to match
		cardToMatch = drawPile.get(0);
		drawPile.remove(0);
		discardPile.add(0, cardToMatch);
	}
	
	// called in the main loop in each turn after the call to Player.playCard()
	// @param played: the card played by the player. returned by Player.playCard()
	public void processCardPlayed(Card played) {
		
		String playedSymbol = played.getSymbol();
		
		// check if the player has a non-zero penalty
		if (drawPenalty.get(currentPlayer) != 0) {
			if (! (playedSymbol.equals("draw two") || playedSymbol.equals("wild draw four")) ) {
				// execute penalty if the player doesn't play a draw card
				Player currentPlayer_ = allPlayers.get(currentPlayer);
				// draw cards
				currentPlayer_.drawCard(drawPile, drawPenalty.get(currentPlayer));
				// skip turn
				if (currentPlayer == allPlayers.size() - 1) {
					// if the currentPlayer is the last player in allPlayers
					// next player will be the first player in allPlayers
					currentPlayer = 0;
				} else {
					currentPlayer += 1;
				}
			}
		}
		
		// when the player plays an invalid card, end processing the card
		boolean isValid = Utilities.checkCardValidity(cardToMatch, played);
		if (!isValid) {
			System.out.println("This card is invalid. Pick another card to play");
			return;
		}
		
		if (playedSymbol.equals("wild")) {
			processWildCard();
		} else if (playedSymbol.equals("wild draw four")) {
			// process wild feature of the card first
			processWildCard();
			// record the penalty of drawing four cards for next player
			if (currentPlayer == allPlayers.size() - 1) {
				// if the currentPlayer is the last player in allPlayers
				// next player will be the first player in allPlayers
				drawPenalty.set(0, 4 + drawPenalty.get(currentPlayer));
			} else {
				drawPenalty.set(currentPlayer + 1, 4 + drawPenalty.get(currentPlayer));
			}
		} else {
			// played a non-wild card, update cardToMatch
			cardToMatch = played;
			if (playedSymbol.equals("skip")) {
				// skip the next player. 
				if (currentPlayer == allPlayers.size() - 1) {
					// if the currentPlayer is the last player in allPlayers
					// next player will be the first player in allPlayers
					currentPlayer = 0;
				} else {
					currentPlayer += 1;
				}
			} else if (playedSymbol.equals("draw two")) {
				// record the penalty of drawing two cards for next player
				if (currentPlayer == allPlayers.size() - 1) {
					// if the currentPlayer is the last player in allPlayers
					// next player will be the first player in allPlayers
					drawPenalty.set(0, 2 + drawPenalty.get(currentPlayer));
				} else {
					drawPenalty.set(currentPlayer + 1, 2 + drawPenalty.get(currentPlayer));
				}
			} else if (playedSymbol.equals("reverse")) {
				Collections.reverse(allPlayers);
				Collections.reverse(drawPenalty);
			}
		}
		
		// next player's turn
		if (currentPlayer == allPlayers.size() - 1) {
			// if the currentPlayer is the last player in allPlayers
			// next player will be the first player in allPlayers
			currentPlayer = 0;
		} else {
			currentPlayer += 1;
		}
		
		// update discard pile and player's pile
	}
	
	// for wild card, ask the current player to set the color
	private void processWildCard() {
		Boolean inputIsValid = false;
		while(!inputIsValid) {
			System.out.println("plase choose the next color to be matched");
			System.out.println("enter 0 for yellow, 1 for red, 2 for green, or 3 for blue");
			// ask for user keyboard input
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			// if choice is valid, change color to match to a card with no symbol and 
			// the selected color and break out the loop
			if (choice == 1 || choice == 2 || choice == 3 || choice == 0) {
				cardToMatch = new NonWildCard(null, allColors[choice]);
				inputIsValid = true;
			}
			// for invalid input, ask the user to try again
			System.out.println("invalid input, please try again");
		}
	}

	
	// initialize the drawPile with 108 cards
	private void initializeDrawPile() {
		
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j < 4; j++) {
				drawPile.add(new NonWildCard(String.valueOf(i), allColors[j]));
				if (i != 0) {
					// two sets of 1-9
					drawPile.add(new NonWildCard(String.valueOf(i), allColors[j]));
				}
			}
		}
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 2; y++) {
				// two reverse card, two skip cards and two draw two cards for each color
				drawPile.add(new NonWildCard("reverse", allColors[x]));
				drawPile.add(new NonWildCard("skip", allColors[x]));
				drawPile.add(new NonWildCard("draw two", allColors[x]));
			}
			// four wild card and four wild draw four cards
			drawPile.add(new WildCard("wild"));
			drawPile.add(new WildCard("wild draw four"));
		}
	
	}

	
	// below are getters used by unit tests
	
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


}
