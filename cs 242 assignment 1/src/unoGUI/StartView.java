package unoGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class for the start view of the uno game
 * reference: 
 * https://wiki.illinois.edu/wiki/display/cs242/Graphical+User+Interface+Example
 */
public class StartView {
	
	/**
	 * constant for font size
	 */
	private float FONT_SIZE = 20;
	
	/**
	 * constant for window width
	 */
	private int START_WINDOW_WIDTH = 1000;
	
	/**
	 * constant for window height
	 */
	private int START_WINDOW_HEIGHT = 800;
	
	/**
	 * constant for button width
	 */
	private int BUTTON_WIDTH = 150;
	
	/**
	 * constant for button height
	 */
	private int BUTTON_HEIGHT = 50;
	
	/**
	 * constant for image height
	 */
	private int IMAGE_WIDTH = 300;
	
	/**
	 * constant for image height
	 */
	private int IMAGE_HEIGHT = 260;
	
	/**
	 * constant for text height
	 */
	private int TEXT_WIDTH = 100;
	
	/**
	 * constant for text height
	 */
	private int TEXT_HEIGHT = 30;
	
	
	/**
	 * Constructor of the StartView class
	 * @throws IOException
	 */
	public StartView() throws IOException {
		JFrame start = new JFrame("UNO");
		start.setSize(START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
		JPanel startPanel = Utilities.initializePanel(START_WINDOW_WIDTH,
				START_WINDOW_HEIGHT);
		
		// add start game button to startPanel
		String buttonText = "start game";
		int buttonX = (START_WINDOW_WIDTH - BUTTON_WIDTH) / 2;
		int buttonY = 550;
		Utilities.addButton(startPanel, buttonText, BUTTON_WIDTH, BUTTON_HEIGHT, 
				buttonX, buttonY);
		
		// add uno logo via url
		int imageX = (START_WINDOW_WIDTH - IMAGE_WIDTH) / 2;
	    int imageY = 100;
	    String logoUrl = "https://i.dlpng.com/static/png/6905409_preview.png";
		Utilities.displayImageFromUrl(startPanel, IMAGE_WIDTH, IMAGE_HEIGHT, 
				imageX, imageY, logoUrl);
		
		// add input prompt and input box
		initializeInput(startPanel);
		
		start.setContentPane(startPanel);
        start.setVisible(true);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		 playerNum.setSize(TEXT_WIDTH,TEXT_HEIGHT);
		 playerNum.setLocation((START_WINDOW_WIDTH - TEXT_WIDTH) / 2,460);
		 
		 String inputPromt = "Please enter the number of players";
		 Utilities.displayText(startPanel, inputPromt, FONT_SIZE, START_WINDOW_WIDTH, 400);
	 }


}
