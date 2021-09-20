package unoGUI;

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
		view = new InGameView(state);
	}
}
