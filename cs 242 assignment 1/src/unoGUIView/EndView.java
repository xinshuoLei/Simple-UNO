package unoGUIView;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class that construct the GUI for end game view
 */
public class EndView extends GUI {
	
	/**
	 * constant for font size
	 */
	private final float FONT_SIZE = 20;
	
	/**
	 * constant for window width
	 */
	private final int END_WINDOW_WIDTH = 1000;
	
	/**
	 * constant for window height
	 */
	private final int END_WINDOW_HEIGHT = 800;
	
	/**
	 * constant for image height
	 */
	private final int IMAGE_WIDTH = 300;
	
	/**
	 * constant for image height
	 */
	private final int IMAGE_HEIGHT = 300;
	
	/**
	 * winner of the uno game
	 */
	private String winner;
	
	 /**
	 * Constructor of the EndView class
	 * @param setWinner winner of the game
	 */
	
	public EndView(String setWinner) {
		winner = setWinner;
		JFrame endView = new JFrame("End");
		endView.setSize(END_WINDOW_WIDTH,END_WINDOW_HEIGHT);
		JPanel endPanel = initializePanel(END_WINDOW_WIDTH, 
				END_WINDOW_HEIGHT);
		
		// display winner prompt
		displayWinner(endPanel);
		
		// display a trophy image
		displayTrophy(endPanel);
		
		endView.setContentPane(endPanel);
		endView.setVisible(true);
		endView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Display a trophy image from url
	 * @param endPanel JPanel for end GUI
	 */
	private void displayTrophy(JPanel endPanel) {
		String trophyUrl = 
		"https://media.istockphoto.com/vectors/vector-flat-golden-trophy-vector-"
		+ "id1176397624?k=20&m=1176397624&s=612x612&w=0&h=yICH7de39SwB1sDP4-kBHFS"
		+ "8bJz4srdu_HOrBC9KvzY=";
		
		int imageX = (END_WINDOW_WIDTH - IMAGE_WIDTH) / 2;
		int imageY = 150;
		displayImageFromUrl(endPanel, IMAGE_WIDTH, IMAGE_HEIGHT, 
				imageX, imageY, trophyUrl);
	}
	
	/**
	 * Display current game's winner
	 * @param endPanel JPanel for end GUI
	 */
	private void displayWinner(JPanel endPanel) {
		String winnerPrompt = "The winner is " + winner + "!" ;
		int promptY = 520;
		displayTextInMiddle(endPanel, winnerPrompt, FONT_SIZE, 
				END_WINDOW_WIDTH, promptY);
	}
}
