package unoGUIView;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import unoGameLogic.Player;

/**
 * Class for the start view of the uno game
 * reference: 
 * https://wiki.illinois.edu/wiki/display/cs242/Graphical+User+Interface+Example
 */
public class StartView extends GUI {
	
	/**
	 * constant for font size
	 */
	private final float FONT_SIZE = 15;
	
	/**
	 * constant for window width
	 */
	private final int START_WINDOW_WIDTH = 1000;
	
	/**
	 * constant for window height
	 */
	private final int START_WINDOW_HEIGHT = 800;
	
	/**
	 * constant for button width
	 */
	private final int BUTTON_WIDTH = 150;
	
	/**
	 * constant for button height
	 */
	private final int BUTTON_HEIGHT = 50;
	
	/**
	 * constant for image height
	 */
	private final int IMAGE_WIDTH = 300;
	
	/**
	 * constant for image height
	 */
	private final int IMAGE_HEIGHT = 260;
	
	/**
	 * constant for spinner height
	 */
	private final int INPUT_WIDTH = 50;
	
	/**
	 * constant for spinner height
	 */
	private final int INPUT_HEIGHT = 40;
	
	/**
	 * constant for maximum number of players
	 */
	private final int MAXIMUM_PLAYER = 15;
	
	/**
	 * start button
	 */
	private JButton startButton;
	
	/**
	 * JSpinner for number of human players
	 */
	private JSpinner numHumanPlayer;
	
	/**
	 * JSpinner for number of ai players
	 */
	private JSpinner numAIPlayer;
	
	/**
	 * JComboxBox for AI type selection
	 */
	private JComboBox aiType;
	
	/**
	 * JFrame for startView
	 */
	JFrame frame;
	
	/**
	 * Constructor of the StartView class
	 */
	public StartView() {
		frame = new JFrame("UNO");
		frame.setSize(START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
		JPanel startPanel = initializePanel(START_WINDOW_WIDTH,
				START_WINDOW_HEIGHT);
		
		// add start game button to startPanel
		addStartButton(startPanel);
		
		// add uno logo via url
		displayUnoLogo(startPanel);
		
		// add spinner for player number input
		initializePlayerNum(startPanel);
		
		addAISelection(startPanel);
		
		frame.setContentPane(startPanel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Display uno logo from url
	 * @param startPanel JPanel for start GUI
	 */
	private void displayUnoLogo(JPanel startPanel) {
		int imageX = (START_WINDOW_WIDTH - IMAGE_WIDTH) / 2;
	    int imageY = 80;
	    String logoUrl = "https://i.dlpng.com/static/png/6905409_preview.png";
	    displayImageFromUrl(startPanel, IMAGE_WIDTH, IMAGE_HEIGHT, 
	    		imageX, imageY, logoUrl);
	}

	
	/**
	 * Add start button to the panel
	 * @param startPanel JPanel for start GUI
	 */
	private void addStartButton(JPanel startPanel) {
		String buttonText = "start game";
		int buttonX = (START_WINDOW_WIDTH - BUTTON_WIDTH) / 2;
		int buttonY = 620;
		startButton = addButton(startPanel, buttonText, BUTTON_WIDTH, BUTTON_HEIGHT, 
				buttonX, buttonY);
	}
	
	 
	 /**
	  * initialize the input box for player number
	  * @param startPanel JPanel of the start window
	  * reference: https://www.geeksforgeeks.org/java-swing-jtextfield/
	  */
	 
	 private void initializePlayerNum(JPanel startPanel) {
		 // text prompt for number of human players
		 String numHumanPromt = "Please select the number of human players";
		 int humanPromptY = 400;
		 displayText(startPanel, numHumanPromt, FONT_SIZE, 270, humanPromptY);
		 // spinner for selecting number of human players
		 SpinnerNumberModel humanPlayer = new SpinnerNumberModel(0, 0, MAXIMUM_PLAYER, 1);
		 numHumanPlayer = addSpinner(startPanel, humanPlayer, INPUT_WIDTH, INPUT_HEIGHT, 670, 
				 humanPromptY - 10);
		 
		 // text prompt for number of human players
		 String numAIPromt = "Please select the number of AI players";
		 int aiPromptY = 460;
		 displayText(startPanel, numAIPromt, FONT_SIZE, 270, aiPromptY);
		 // spinner for selecting number of human players
		 SpinnerNumberModel aiPlayer = new SpinnerNumberModel(0, 0, MAXIMUM_PLAYER, 1);
		 numAIPlayer = addSpinner(startPanel, aiPlayer, INPUT_WIDTH, INPUT_HEIGHT, 670, 
				aiPromptY - 10);		 
	 }
	 
	 /**
	  * add the drop down menu for AI type selection
	  * @param startPanel JPanel of the start window
	  */
	 private void addAISelection(JPanel startPanel) {
		 String[] aiTypes = {Player.NOT_AI, Player.BASELINE_AI, Player.STRATEGIC_AI};
		 // text prompt
		 String aiTypePrompt = "Please select the type of AI players";
		 int aiTypePromptY = 530;
		 displayText(startPanel, aiTypePrompt, FONT_SIZE, 270, aiTypePromptY);
		 aiType = addDropDown(startPanel, aiTypes, 100 , INPUT_HEIGHT, 620, 
				 aiTypePromptY - 10);
 	 }
	 
	 /**
	  * Function that add a listener for start button
	  * @param startButtonListener actionListener for start button
	  */
	 public void addStartButtonListener(ActionListener startButtonListener) {
		 startButton.addActionListener(startButtonListener);
	 }
	 
	 /**
	  * Function that add a listener for ai type drop down menu
	  * @param aiTypeListener actionListener for ai types menu
	  */
	 public void addAITypeListener(ActionListener aiTypeListener) {
		 aiType.addActionListener(aiTypeListener);
	 }
	 
	 /**
	  * Function that add a listener for number of human player JSpinner
	  * @param humanNumListener actionListener for JSpinner numHumanPlayers
	  */
	 public void addHumanNumListener(ChangeListener humanNumListener) {
		 numHumanPlayer.addChangeListener(humanNumListener);
	 }
	 /**
	  * Function that add a listener for number of AI player JSpinner
	  * @param aiNumListener actionListener for JSpinner numAIPlayers
	  */
	 public void addAINumListener(ChangeListener aiNumListener) {
		 numAIPlayer.addChangeListener(aiNumListener);
	 }

	 public JFrame getFrame() {
		return frame;
	 }
}
