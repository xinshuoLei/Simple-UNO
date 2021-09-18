package unoGUI;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


import unoCard.Card;
import unoGameLogic.GameState;
import unoGameLogic.Player;

/**
 * class for in game GUI
 */
public class InGame extends GUI {
	
	/**
	 * constant for font size
	 */
	private final float FONT_SIZE = 15;
	
	/**
	 * constant for window width
	 */
	private final int WINDOW_WIDTH = 1000;
	
	/**
	 * constant for window height
	 */
	private final int WINDOW_HEIGHT = 800;
	
	/**
	 * corresponing GameState instance
	 */
	private GameState state = null;
	
	/**
	 * constant for button width
	 */
	private final int BUTTON_WIDTH = 170;
	
	/**
	 * constant for button height
	 */
	private final int BUTTON_HEIGHT = 30;
	
	/**
	 * constant for x-coordinate of buttons
	 */
	private final int BUTTON_X = 750;
	
	/**
	 * constant for text input height
	 */
	private final int INPUT_HEIGHT = 30;
	
	/**
	 * constant for card width
	 */
	private final int CARD_WIDTH = 50;
	
	/**
	 * constant for card height
	 */
	private final int CARD_HEIGHT = 80;
	
	/**
	 * the image url suffix for draw two card
	 */
	private final String DRAW2_URL = "draw2";
	
	/**
	 * the image url suffix for wild draw four card
	 */
	private final String WILD_DRAW4_URL = "wilddraw4";
	
	/**
	 * the image url suffix for wild card
	 */
	private final String WILD_URL = "wildchange";
	
	 /**
	 * Constructor of the StartView class
	 * @param setState the game state for the current GUI
	 */
	public InGame(GameState setState) {
		
		state = setState;
		
		// TODO Auto-generated constructor stub
		JFrame inGame = new JFrame("In Game");
		inGame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		JPanel inGamePanel = initializePanel(WINDOW_WIDTH, 
				WINDOW_HEIGHT);

		displayDrawPile(inGamePanel);
		
		addNonPlayButton(inGamePanel);
		addPlayInteraction(inGamePanel);
		
		displayCardToMatch(inGamePanel); 
		displayStateInfo(inGamePanel);
		
		displayStack(inGamePanel);

		initializeGameArea(inGamePanel);
		
		inGame.setContentPane(inGamePanel);
		inGame.setVisible(true);
		inGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * Initialize the game area by displaying a white image
	 * @param panel the JPanel for in game GUI
	 */
	private void initializeGameArea(JPanel panel) {
		String whiteImageUrl = "https://www.macmillandictionary.com/external/"
				+ "slideshow/full/White_full.png";
		int area_height = 350;
		displayImageFromUrl(panel, WINDOW_WIDTH, area_height, 
				0, 0, whiteImageUrl);
	}
	
	/**
	 * Display an image for draw pile and add a text prompt
	 * @param panel the JPanel for in game GUI
	 */
	private void displayDrawPile(JPanel panel) {
		String cardBackUrl = "https://s.catch.com.au/images/product/0002/2181/"
				+ "5983faa7563cb163730529.jpg";
		int cardBackSize= 150;
		int cardLocationX = 100;
		int cardLocationY = 80;
		displayImageFromUrl(panel, cardBackSize, cardBackSize, cardLocationX, 
				cardLocationY, cardBackUrl);
		
		// display text indicating it's draw pile
		String drawPilePrompt = "draw pile";
		int promptX = 125;
		int promptY = 270;
		displayText(panel, drawPilePrompt, FONT_SIZE, promptX, promptY);
	}
	
	/**
	 * Add buttons not related to playing card
	 * including skip button and hide/reveal button
	 * @param panel the JPanel for in game GUI
	 */
	
	private void addNonPlayButton(JPanel panel) {		
		String skip = "skip";
		int skipY = 650;
		addButton(panel, skip, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_X,
				skipY);
		
		String hideReveal = "hide/reveal cards";
		int hideRevealY = 700;
		addButton(panel, hideReveal, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_X,
				hideRevealY);
	}
	
	/**
	 * Add interactions related to play card
	 * including play card button, play wild card button, 
	 * input color box for wild card 
	 * @param panel the JPanel for in game GUI
	 */
	
	private void addPlayInteraction(JPanel panel) {
		String play = "play card";
		int playY = 550;
		addButton(panel, play, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_X,
				playY);
		
		String playWild = "play wild card";
		int playWildY = 500;
		addButton(panel, playWild, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_X,
				playWildY);
		
		// input box for color when player wants to play wild card
		// input box
		JTextField colorInput = new JTextField(10);
		panel.add(colorInput);
		colorInput.setSize(BUTTON_WIDTH,INPUT_HEIGHT);
		colorInput.setLocation(BUTTON_X,450);
		
		// display instruction
		String wildInstruction = "To play a wild card, enter color choice in "
				+ "the input box above play wild card button, then click play wild card";
		int instructcionY = 370;
		displayTextInMiddle(panel, wildInstruction, FONT_SIZE, WINDOW_WIDTH, 
				instructcionY);
	}
	
	
	/**
	 * Construct the url of image for the card
	 * website for card image: http://unocardinfo.victorhomedia.com/ 
	 * @param card card to be displayed
	 * @return the url for the image of the card
	 */
	private String constructCardUrl(Card card) {
		String urlPrefix = "http://unocardinfo.victorhomedia.com/graphics/uno_card-";
		String urlSuffix = ".png";
		String symbol = card.getSymbol();
		String color = card.getColor();
		String symbolInUrl = "";
		
		switch(symbol) {
			case Card.DRAW2:
				symbolInUrl = color + DRAW2_URL;
				break;
			case Card.WILD:
				symbolInUrl = WILD_URL;
				break;
			case Card.WILD_DRAW4:
				symbolInUrl = WILD_DRAW4_URL;
				break;
			default:
				symbolInUrl = color + symbol;
				break;
		}
		
		String url = urlPrefix + symbolInUrl + urlSuffix;
		return url;
	}
	
	/**
	 * Display the card to match, i.e. last card in discard pile
	 * @param panel the JPanel for in game GUI
	 */
	private void displayCardToMatch(JPanel panel) {
		Card cardToMatch = state.getCardToMatch();
		String cardUrl = constructCardUrl(cardToMatch);
		int height = 150;
		int width = 100;
		int cardToMatchX = 370;
		int cardToMatchY = 80;
		displayImageFromUrl(panel, width, height, cardToMatchX, 
				cardToMatchY, cardUrl);
		
		// display a prompt indicating it is the card to match
		String prompt = "top card in discard pile";
		int promptX = 330;
		int promptY = 250;
		displayText(panel, prompt, FONT_SIZE, promptX, promptY);
		
		// display color to match for the case where cardToMatch is wild card
		// check against null to prevent first card to match is wild
		if (cardToMatch.getColor() != null) {
			String colorToMatch = "color to match:   " + cardToMatch.getColor();
			int colorToMatchX = 330;
			int colorToMatchY = 290;
			displayText(panel, colorToMatch, FONT_SIZE, colorToMatchX, 
					colorToMatchY);
		}
	}
	
	/**
	 * Display state info, including current player and draw penalty
	 * @param panel the JPanel for in game GUI
	 */
	private void displayStateInfo(JPanel panel) {
		// get current player's name
		List<Player> allPlayer = state.getAllPlayers();
		Player currentPlayer = allPlayer.get(state.getCurrentPlayer());
		String currentPlayerPromopt = "current player:   " + currentPlayer.getName();
		// display name
		int currentPlayerX = 650;
		int currentPlayerY = 80;
		displayText(panel, currentPlayerPromopt, FONT_SIZE, 
				currentPlayerX, currentPlayerY);
		
		// get currentPlayer's drawPenalty
		List<Integer> drawPenalty = state.getDrawPenalty();
		int penalty = drawPenalty.get(state.getCurrentPlayer());
		String penaltyPrompt = "draw penalty for current player:   " + penalty;
		int penaltyX = 650;
		int penaltyY = 120;
		displayText(panel, penaltyPrompt, FONT_SIZE, penaltyX, 
				penaltyY);
	}
	
	/**
	 * Display current player's stack
	 * every row contains a maximum of 7 cards
	 * @param panel the JPanel for in game GUI
	 */
	private void displayStack(JPanel panel) {
		int maxCardInRow = 7;
		int firstCardX = 100;
		int xMargin = 80;
		int yMargin = 100;
		int cardY = 330;
		int cardX = 100;
		
		// get player's stack
		List<Player> allPlayer = state.getAllPlayers();
		Player currentPlayer = allPlayer.get(state.getCurrentPlayer());
		List<Card> stack = currentPlayer.getStack();
		
		for (int i = 0; i < stack.size(); i++) {
			if (i % maxCardInRow == 0) {
				// reach maximum number of cards in a row
				// should create a new row
				cardY += yMargin;
				cardX = firstCardX;
			}
			String url = constructCardUrl(stack.get(i));
			displayImageFromUrl(panel, CARD_WIDTH, CARD_HEIGHT, 
					cardX, cardY, url);
			// add margin to x so next card can be placed in right position
			cardX += xMargin;
		}
		
	}
}
