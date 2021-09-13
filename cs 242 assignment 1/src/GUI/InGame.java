package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InGame {
	 /**
	 * Constructor of the StartView class
	 */
	public InGame() {
		// TODO Auto-generated constructor stub
		JFrame inGame = new JFrame("In Game");
		inGame.setSize(500,500);
		JPanel myPanel = initializePanel();
		inGame.setContentPane(myPanel);
		inGame.setVisible(true);
		inGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	 private JPanel initializePanel() {
	        JPanel myPanel = new JPanel();
	        myPanel.setPreferredSize(new Dimension(500,500));
	        myPanel.setLayout(new BorderLayout());
	        return myPanel;
	 }
}
