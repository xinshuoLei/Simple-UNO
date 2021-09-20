package unoGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidAlgorithmParameterException;

import unoCard.Card;
import unoGUI.InGameModel.playStatus;

import javax.naming.InsufficientResourcesException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.net.httpserver.Authenticator.Success;

import java.util.List;

import unoGameLogic.GameState;

public class InGameControl {
	
	/**
	 * corresponding InGameModel
	 */
	private InGameModel model;
	
	/**
	 * corresponding InGameView
	 */
	private InGameView view;
	/**
	 * constructor for InGameConrtol
	 * @param numHumanPlayers number of human players in a game
	 * @param numAI number of AI in a game
	 * @param aiType type of AI in a game
	 */
	public InGameControl(int numHumanPlayer, int numAI, String aiType) {
		model = new InGameModel(numHumanPlayer, numAI, aiType);
		GameState state = model.getState();
		System.out.println(state.getAllPlayers().size());
		
		// get all information needed to construct view
		Card cardToMatch = model.getCardToMatch();
		Card cardBeforeSpecial = model.getCardBeforeSpecial();
		List<Card> stack = model.getStack();
		String currentPlayer = model.getCurrentPlayer();
		int drawPenalty = model.getPenalty();
		int playerNum = model.getPlayerNum();
		String playerAI = model.getAIType();
		
		// construct view
		view = new InGameView(cardToMatch, cardBeforeSpecial, stack, currentPlayer,
				drawPenalty, playerNum, playerAI);
		
		// set up listeners
		setupHideRevealListener();
		setupSkipListener();
		setupCardSelectionListener();
		setupColorSelectionListener();
		setupPlayButtonListener();
	}
	
	/**
	 * Function that set up actionListener for hide/reveal button
	 */
	private void setupHideRevealListener() {
		ActionListener hideRevealListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<JLabel> stackJLabels = view.getStackJLabels();
				boolean visibility = stackJLabels.get(0).isVisible();
				for (JLabel oneLabel : stackJLabels) {
					// set visibility to the opposite of current visibility
					oneLabel.setVisible(!visibility);
				}
			}
		};
		view.addHideRevealListener(hideRevealListener);
	}
	
	/**
	 * Function that set up actionListener for skip button
	 */
	private void setupSkipListener() {
		ActionListener skipListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// show a confirming dialog
				JOptionPane optionPane = new JOptionPane();
				int result = JOptionPane.showConfirmDialog(optionPane,
						"Click yes to confirm switching to next play");
				int confirm = 0;
				if (result == confirm) {
					model.nextPlayer();
					updatePanel();
				}
			}
		};
		view.addSkipListener(skipListener);
	}
	
	/**
	 * Set up a listener for card selections
	 * When a card is selected, update cardSelection in model
	 */
	private void setupCardSelectionListener() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> dropdown = (JComboBox<String>) e.getSource();
				int selection = (int) dropdown.getSelectedIndex();
				System.out.println(selection);
				model.setCardSelection(selection);
			}
		};
		view.addCardSelectionListener(listener);
	}
	
	/**
	 * Set up a listener for color selections
	 * When a card is color is selected, update colorSelection in model
	 */
	private void setupColorSelectionListener() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> dropdown = (JComboBox<String>) e.getSource();
				String selection = (String) dropdown.getSelectedItem();
				System.out.println(selection);
				model.setColorSelection(selection);
			}
		};
		view.addColorSelectionListener(listener);
	}
	
	/**
	 * Set up a listener for card selections
	 * When play button is clicked, call playCardHuman in model
	 */
	private void setupPlayButtonListener() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playStatus status = model.playCardHuman();
				switch(status) {
					case SUCCESS: 
						// show a confirming dialog for switching player
						JOptionPane optionPaneSuccess = new JOptionPane();
						int result = JOptionPane.showConfirmDialog(optionPaneSuccess,
								"Successully played " + 
								model.getCardSelected().getCardInfo() + 
								"\nClick yes to confirm switching to next player");
						int confirm = 0;
						if (result == confirm) {
							model.nextPlayer();
							updatePanel();
							// check gameEnding condition
							String winner = model.checkWinner();
							// if there is a winner, create end view
							// and close the current window
							if (winner != null) {
								JFrame inGameFrame = view.getFrame();
								// close startFrame
								inGameFrame.setVisible(false);
								inGameFrame.dispose();
								new EndView(winner);
							}
						}
						break;
						
					case FAIL:
						JOptionPane optionPaneFail = new JOptionPane();
						JOptionPane.showMessageDialog(optionPaneFail, "The card selected is "
								+ "invalid. Please pick another one");
						break;
						
					case SKIP:
						JOptionPane optionPaneSkip = new JOptionPane();
						int resultSkip = JOptionPane.showConfirmDialog(optionPaneSkip,
								"You have a draw penalty." + 
								"The penalty has been applied and your turn is skipped." +
								"click yes to cofirm switching to next player");
						int confirmSkip = 0;
						if (resultSkip == confirmSkip) {
							updatePanel();
						}
						
					default:
				}
			}
		};
		view.addplayButtonListener(listener);
	}
	
	
	/**
	 * Update panel in InGameView to display info related to next player
	 */
	private void updatePanel() {
		// remove previous info
		removeCurrentComponent();
		
		JPanel panel = view.getInGamePanel();
		// get new info
		Card cardToMatch = model.getCardToMatch();
		Card cardBeforeSpecial = model.getCardBeforeSpecial();
		List<Card> stack = model.getStack();
		String currentPlayer = model.getCurrentPlayer();
		int drawPenalty = model.getPenalty();
		String playerAI = model.getAIType();
		
		// display new info
		view.displayCardBeforeSpecial(cardBeforeSpecial);
		view.displayCardToMatch(cardToMatch);
		view.displayStack(stack);
		view.displayStateInfo(currentPlayer, drawPenalty, playerAI);
		view.initializeGameArea();
		view.addCardSelection(stack);
		panel.repaint();
		
		// set up listener
		setupCardSelectionListener();
	}
	
	/**
	 * Function that remove components related to current player
	 */
	private void removeCurrentComponent() {
		List<JLabel> stackJLabels = view.getStackJLabels();
		boolean visibility = stackJLabels.get(0).isVisible();
		JPanel panel = view.getInGamePanel();
		for (JLabel oneLabel : stackJLabels) {
			// set visibility to the opposite of current visibility
			oneLabel.setVisible(!visibility);
			panel.remove(oneLabel);
			panel.validate();
		}
		for (JLabel oneLabel : view.getUpdatedJLabel()) {
			// set visibility to the opposite of current visibility
			oneLabel.setVisible(!visibility);
			panel.remove(oneLabel);
			panel.validate();
		}
		panel.remove(view.getCardSelection());
	}
}
