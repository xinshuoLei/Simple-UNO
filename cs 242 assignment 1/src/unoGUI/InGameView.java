package unoGUI;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

import unoCard.Card;
import unoGameLogic.GameState;
import unoGameLogic.Player;

/**
 * class for in game GUI
 */
public class InGameView extends GUI {
	
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
	 * JFrame for InGameView
	 */
	private JFrame frame;
	
	/**
	 * JLabel for the draw pile
	 */
	private JLabel drawPile;
	
	/**
	 * Skip button
	 */
	private JButton skipButton;
	
	/**
	 * hide/reveal button
	 */
	private JButton hideRevealButton;
	
	/**
	 * play card button
	 */
	private JButton playButton;
	
	/**
	 * play wild card button
	 */
	private JButton playWildButton;
	
	/**
	 * JLabel for cardToMatch
	 */
	private JLabel cardToMatch;
	
	/**
	 * JComboBox for card selection
	 */
	private JComboBox cardSelection;
	
	/**
	 * JComboBox for color selection
	 */
	private JComboBox colorSelection;
	
	
	/**
	 * JLabels that need to be updated
	 */
	private List<JLabel> updatedJLabel = new ArrayList<JLabel>();
	
	/**
	 * Main JPanel
	 */
	private JPanel inGamePanel;
	
	/**
	 * List that contain all JLabels for cards in the stack
	 */
	private List<JLabel> stackJLabels = new ArrayList<>();
	
	 /**
	  * Constructor of the InGameView class
	  * @param cardToMatch card to match
	  * @param cafdBeforeSpecial card before special
	  * @param stack stack of current player
	  * @param currentPlayer name of current player
	  * @param drawPenalty draw penalty of current player
	  */
	public InGameView(Card cardToMatch, Card cardBeforeSpecial, List<Card> stack,
			String currentPlayer, int drawPenalty, int playerNum, String playerAI) {
		
		frame = new JFrame("In Game");
		frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		inGamePanel = initializePanel(WINDOW_WIDTH, 
				WINDOW_HEIGHT);

		displayDrawPile();
		displayPlayerNum(playerNum);
		
		addNonPlayButton();
		addPlayButtonAndInstruction();
		
		displayCardToMatch(cardToMatch);
		displayCardBeforeSpecial(cardBeforeSpecial);
		displayStateInfo(currentPlayer, drawPenalty, playerAI);
		
		displayStack(stack);
		addCardSelection(stack);
		addColorSelection();

		initializeGameArea();
		
		frame.setContentPane(inGamePanel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	/**
	 * Initialize the game area by displaying a white image
	 */
	public void initializeGameArea() {
		String whiteImageUrl = "https://www.macmillandictionary.com/external/"
				+ "slideshow/full/White_full.png";
		int area_height = 350;
		JLabel area = displayImageFromUrl(inGamePanel, WINDOW_WIDTH, area_height, 
				0, 0, whiteImageUrl);
		updatedJLabel.add(area);
	}
	
	/**
	 * Display an image for draw pile and add a text prompt
	 * @param panel the JPanel for in game GUI
	 */
	private void displayDrawPile() {
		String cardBackUrl = "https://s.catch.com.au/images/product/0002/2181/"
				+ "5983faa7563cb163730529.jpg";
		int cardBackSize= 150;
		int cardLocationX = 100;
		int cardLocationY = 80;
		drawPile = displayImageFromUrl(inGamePanel, cardBackSize, cardBackSize, cardLocationX, 
				cardLocationY, cardBackUrl);
		
		// display text indicating it's draw pile
		String drawPilePrompt = "draw pile";
		int promptX = 125;
		int promptY = 270;
		displayText(inGamePanel, drawPilePrompt, FONT_SIZE, promptX, promptY);
	}
	
	/**
	 * Add buttons not related to playing card
	 * including skip button and hide/reveal button
	 */
	
	private void addNonPlayButton() {		
		String skip = "skip";
		int skipY = 650;
		skipButton = addButton(inGamePanel, skip, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_X,
				skipY);
		
		String hideReveal = "hide/reveal cards";
		int hideRevealY = 700;
		hideRevealButton = addButton(inGamePanel, hideReveal, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_X,
				hideRevealY);
	}
	
	/**
	 * Add interactions related to play card
	 * including play card button, play wild card button, 
	 * input color box for wild card 
	 */
	
	private void addPlayButtonAndInstruction() {
		String play = "play card";
		int playY = 580;
		playButton = addButton(inGamePanel, play, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_X,
				playY);
		
		// display instruction
		String wildInstruction = "To play a wild card, select a color first"
				+ " then select a card and click play card";
		int instructcionY = 370;
		displayTextInMiddle(inGamePanel, wildInstruction, FONT_SIZE, WINDOW_WIDTH, 
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
	 * @param cardToMatch card to match
	 */
	public void displayCardToMatch(Card cardToMatch) {
		String cardUrl = constructCardUrl(cardToMatch);
		int height = 150;
		int width = 100;
		int cardToMatchX = 370;
		int cardToMatchY = 80;
		JLabel cardToMatchLabel = displayImageFromUrl(inGamePanel, width, height, 
				cardToMatchX, cardToMatchY, cardUrl);
		updatedJLabel.add(cardToMatchLabel);
		
		// display a prompt indicating it is the card to match
		String prompt = "top card in discard pile";
		int promptX = 330;
		int promptY = 250;
		displayText(inGamePanel, prompt, FONT_SIZE, promptX, promptY);
		
		// display color to match for the case where cardToMatch is wild card
		// check against null to prevent first card to match is wild
		if (cardToMatch.getColor() != null) {
			String colorToMatch = "color to match:   " + cardToMatch.getColor();
			int colorToMatchX = 330;
			int colorToMatchY = 290;
			JLabel colorLabel = displayText(inGamePanel, colorToMatch, FONT_SIZE, colorToMatchX, 
					colorToMatchY);
			updatedJLabel.add(colorLabel);
		}
	}
	
	/**
	 * Display state info, including current player and draw penalty
	 * @param currentPlayer current player 
	 * @param drawPenalty draw penalty of the current player
	 */
	public void displayStateInfo(String currentPlayer, int penalty,
			String playerAI) {
		String currentPlayerPromopt = null;
		if (playerAI.equals(Player.NOT_AI)) {
			currentPlayerPromopt = "current player:   " + currentPlayer;
		} else {
			currentPlayerPromopt = "current player:   " + currentPlayer 
				+ " (" + playerAI + " AI)";
		}
		// display name
		int currentPlayerX = 650;
		int currentPlayerY = 120;
		JLabel currentPlayerLabel = displayText(inGamePanel, currentPlayerPromopt, FONT_SIZE, 
				currentPlayerX, currentPlayerY);
		updatedJLabel.add(currentPlayerLabel);
		
		// get currentPlayer's drawPenalty
		String penaltyPrompt = "draw penalty for current player:   " + penalty;
		int penaltyX = 650;
		int penaltyY = 160;
		JLabel penaltyLabel = displayText(inGamePanel, penaltyPrompt, FONT_SIZE, penaltyX, 
				penaltyY);
		updatedJLabel.add(penaltyLabel);
		
	}

	/**
	 * Function that display total number of players
	 * @param playerNum number of players
	 */
	private void displayPlayerNum(int playerNum) {
		// display total number of players
		String numPlayerPrompt = "number of players:   " + playerNum;
		displayText(inGamePanel, numPlayerPrompt, FONT_SIZE, 650, 
				80);
	}
	
	/**
	 * Display current player's stack
	 * every row contains a maximum of 7 cards
	 * @param stack stack of current player
	 */
	public void displayStack(List<Card> stack) {
		int maxCardInRow = 7;
		int firstCardX = 100;
		int xMargin = 80;
		int yMargin = 100;
		int cardY = 330;
		int cardX = 100;
		
		for (int i = 0; i < stack.size(); i++) {
			if (i % maxCardInRow == 0) {
				// reach maximum number of cards in a row
				// should create a new row
				cardY += yMargin;
				cardX = firstCardX;
			}
			String url = constructCardUrl(stack.get(i));
			JLabel card = displayImageFromUrl(inGamePanel, CARD_WIDTH, CARD_HEIGHT, 
					cardX, cardY, url);
			stackJLabels.add(card);
			// add margin to x so next card can be placed in right position
			cardX += xMargin;
		}
		
	}
	/**
	 * Display cardBeforeSpecial
	 * @param cardBeforeSpecial card before special
	 */
	public void displayCardBeforeSpecial(Card cardBeforeSpecial) {
		// display a prompt
		String prompt = "card before special: ";
		displayText(inGamePanel, prompt, FONT_SIZE, 650, 200);
		// display card before special
		// display a message null if cardBeforeSpecial is null
		if (cardBeforeSpecial == null) {
			String none = "None";
			JLabel textLabel = displayText(inGamePanel, none, FONT_SIZE, 820, 200);
			updatedJLabel.add(textLabel);
		} else {
			String url = constructCardUrl(cardBeforeSpecial);
			JLabel label = displayImageFromUrl(inGamePanel, 70, 110, 
					840, 200, url);
			updatedJLabel.add(label);
		}
	}
	
	/**
	 * Add a drop down menu for player to select the card to play
	 * @param stack stack of the current player
	 */
	public void addCardSelection(List<Card> stack) {
		// display instruction
		String prompt = "select card to play:";
		displayText(inGamePanel, prompt, FONT_SIZE, BUTTON_X, 500);
		String[] cardInfos = new String[stack.size() + 1];
		// default option is empty, forcing user to select
		String emptyStr = "";
		cardInfos[0] = emptyStr;
		for (int i = 1; i < stack.size(); i++) {
			cardInfos[i] = stack.get(i).getCardInfo();
		}
		cardSelection = addDropDown(inGamePanel, cardInfos, BUTTON_WIDTH, 
				BUTTON_HEIGHT, BUTTON_X, 530);
	}
	
	/**
	 * Add a drop down menu for player to select color when playing wild card
	 */
	public void addColorSelection() {
		// display instruction
		String prompt = "select color:";
		displayText(inGamePanel, prompt, FONT_SIZE, BUTTON_X, 420);
		String na = "N/A";
		String[] colors = {na, Card.YELLOW, Card.GREEN, Card.RED, Card.BLUE};
		colorSelection = addDropDown(inGamePanel, colors, BUTTON_WIDTH, BUTTON_HEIGHT, 
				BUTTON_X, 450);
	}
	
	/**
	 * Add a actionListener for the skip button
	 * @param listener the listener to add 
	 */
	public void addSkipListener(ActionListener listener) {
		skipButton.addActionListener(listener);
	}
	
	/**
	 * Add a actionListener for the hidE/Reveal button
	 * @param listener the listener to add 
	 */
	public void addHideRevealListener(ActionListener listener) {
		hideRevealButton.addActionListener(listener);
	}

	
	/**
	 * Add a listener for card selection
	 * @param listener
	 */
	public void addCardSelectionListener(ActionListener listener) {
		cardSelection.addActionListener(listener);
	}
	
	/**
	 * Add a listener for color selection
	 * @param listener
	 */
	public void addColorSelectionListener(ActionListener listener) {
		colorSelection.addActionListener(listener);
	}
	
	/**
	 * Add a listener for the play button
	 * @param listener
	 */
	public void addplayButtonListener(ActionListener listener) {
		playButton.addActionListener(listener);
	}


	public List<JLabel> getStackJLabels() {
		return stackJLabels;
	}


	public List<JLabel> getUpdatedJLabel() {
		return updatedJLabel;
	}


	public JPanel getInGamePanel() {
		return inGamePanel;
	}


	public void setInGamePanel(JPanel inGamePanel) {
		this.inGamePanel = inGamePanel;
	}


	public JComboBox getCardSelection() {
		return cardSelection;
	}


	public JFrame getFrame() {
		return frame;
	}
}
