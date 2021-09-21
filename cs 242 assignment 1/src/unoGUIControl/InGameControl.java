package unoGUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import unoCard.Card;
import unoGUIModel.InGameModel;
import unoGUIModel.InGameModel.playStatus;
import unoGUIView.EndView;
import unoGUIView.InGameView;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import java.util.List;

import unoGameLogic.GameState;
import unoGameLogic.Player;

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
		
		view.getStackHiddenLabel().setVisible(false);
		
		// set up listeners
		setupHideRevealListener();
		setupSkipListener();
		setupCardSelectionListener();
		setupColorSelectionListener();
		setupPlayButtonListener();
		setupDrawPileListenter();
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
				// hide stack so that next player cannot see
				hideStack();
				// show a confirming dialog
				JOptionPane optionPane = new JOptionPane();
				int result = JOptionPane.showConfirmDialog(optionPane,
						"Click yes to confirm switching to next player");
				int confirm = 0;
				if (result == confirm) {
					model.nextPlayer();
					updatePanel();
					checkAIPlayer();
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
				// -1 because first selection is blank
				model.setCardSelection(selection - 1);
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
				// update colorSelection in model
				model.setColorSelection(selection);
			}
		};
		view.addColorSelectionListener(listener);
	}
	
	/**
	 * Set up a listener for draw pile
	 * When draw pile is clicked, current player draw a card
	 */
	private void setupDrawPileListenter() {
		MouseListener listener = new MouseAdapter() {
			 @Override
             public void mouseClicked(MouseEvent e) {
				 if (model.getCanDrawCard()) {
					 // if the player is still allowed to draw a card
					 // draw a card
					 Card cardDrawn = model.drawCard();
	                 view.displayCardDrawn(cardDrawn);
				 } else {
					 // display a message showing the player can't draw
					 JOptionPane optionPaneFail = new JOptionPane();
						JOptionPane.showMessageDialog(optionPaneFail, "You "
								+ "can only draw one card in each round");
				 }
             }
		};
		view.addDrawPileListeneer(listener);
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
						successfulPlayCard();
						break;
						
					case FAIL:
						// display a dialog indicating card selected is invalid
						JOptionPane optionPaneFail = new JOptionPane();
						JOptionPane.showMessageDialog(optionPaneFail, "The card selected is "
								+ "invalid. Please pick another one");
						break;
						
					case SKIP:
						// display a dialog confirming switching to next player
						JOptionPane optionPaneSkip = new JOptionPane();
						int resultSkip = JOptionPane.showConfirmDialog(optionPaneSkip,
								"You have a draw penalty." + 
								"The penalty has been applied and your turn is skipped." +
								"click yes to cofirm switching to next player");
						int confirmSkip = 0;
						if (resultSkip == confirmSkip) {
							updatePanel();
							checkAIPlayer();
						}
						break;
						
					default:
				}
			}
		};
		view.addplayButtonListener(listener);
	}
	
	/**
	 * Function that deal with the situation that a card is 
	 * successfully played
	 */
	private void successfulPlayCard() {
		// hide stack so that next player cannot see
		hideStack();
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
			checkAIPlayer();
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
	}
	
	/**
	 * Check if current player is AI. If current player is AI, 
	 * call aiPlayerTurn() in model
	 */
	private void checkAIPlayer() {
		if (!model.getAIType().equals(Player.NOT_AI)) {
			// hide AI stack
			hideStack();
			view.getStackHiddenLabel().setVisible(true);
			String cardPlayed = model.aiPlayerTurn();
			JOptionPane optionPane = new JOptionPane();
			String message = "AI finished turn. click yes to switch to next player";
			if (cardPlayed != null) {
				message = "AI finished turn." +
						"\nAI played " + cardPlayed +
						"\nclick yes to switch to next player";
			}
			// pop up window showing AI finished turn
			int result = JOptionPane.showConfirmDialog(optionPane,message);
			int confirm = 0;
			if (result == confirm) {
				model.nextPlayer();
				updatePanel();
				// check if next player is still AI
				checkAIPlayer();
			}
		}
		view.getStackHiddenLabel().setVisible(false);
	}

	private void hideStack() {
		List<JLabel> stackJLabels = view.getStackJLabels();
		for (JLabel oneLabel : stackJLabels) {
			// set visibility to the opposite of current visibility
			oneLabel.setVisible(false);
		}
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
		JPanel panel = view.getInGamePanel();
		// remove all stack card images
		for (JLabel oneLabel : stackJLabels) {
			panel.remove(oneLabel);
			panel.validate();
		}
		// remove all components that need to be updated
		for (JLabel oneLabel : view.getUpdatedJLabel()) {
			// set visibility to false
			oneLabel.setVisible(false);
			panel.remove(oneLabel);
			panel.validate();
		}
		panel.remove(view.getCardSelection());
	}
}
