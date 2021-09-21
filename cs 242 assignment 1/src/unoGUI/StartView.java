package unoGUI;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class for the start view of the uno game
 * reference: 
 * https://wiki.illinois.edu/wiki/display/cs242/Graphical+User+Interface+Example
 */
public class StartView extends GUI {
	
	/**
	 * constant for font size
	 */
	private final float FONT_SIZE = 20;
	
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
	 * constant for text height
	 */
	private final int INPUT_WIDTH = 100;
	
	/**
	 * constant for text height
	 */
	private final int INPUT_HEIGHT = 30;
	
	
	/**
	 * Constructor of the StartView class
	 */
	public StartView() {
		JFrame start = new JFrame("UNO");
		start.setSize(START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
		JPanel startPanel = initializePanel(START_WINDOW_WIDTH,
				START_WINDOW_HEIGHT);
		
		// add start game button to startPanel
		addStartButton(startPanel);
		
		// add uno logo via url
		displayUnoLogo(startPanel);
		
		// add input prompt and input box
		initializeInput(startPanel);
		
		start.setContentPane(startPanel);
		start.setVisible(true);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Display uno logo from url
	 * @param startPanel JPanel for start GUI
	 */
	private void displayUnoLogo(JPanel startPanel) {
		int imageX = (START_WINDOW_WIDTH - IMAGE_WIDTH) / 2;
	    int imageY = 100;
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
		int buttonY = 550;
		addButton(startPanel, buttonText, BUTTON_WIDTH, BUTTON_HEIGHT, 
				buttonX, buttonY);
	}
	
	 
	 /**
	  * initialize the input box for player number
	  * @param startPanel JPanel of the start window
	  * reference: https://www.geeksforgeeks.org/java-swing-jtextfield/
	  */
	 
	 private void initializeInput(JPanel startPanel) {
		 // input box
		 JTextField playerNum = new JTextField(10);
		 startPanel.add(playerNum);
		 playerNum.setSize(INPUT_WIDTH,INPUT_HEIGHT);
		 playerNum.setLocation((START_WINDOW_WIDTH - INPUT_WIDTH) / 2,460);
		 
		 String inputPromt = "Please enter the number of players";
		 displayTextInMiddle(startPanel, inputPromt, FONT_SIZE, START_WINDOW_WIDTH, 400);
	 }


}
