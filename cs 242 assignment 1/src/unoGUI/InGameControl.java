package unoGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import unoCard.Card;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		view = new InGameView(cardToMatch, cardBeforeSpecial, stack, currentPlayer,
				drawPenalty, playerNum, playerAI);
		
		setupHideRevealListener();
		setupSkipListener();
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
				// remove previous info
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
				
				// get new info
				model.nextPlayer();
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
			}
		};
		view.addSkipListener(skipListener);
	}
}
