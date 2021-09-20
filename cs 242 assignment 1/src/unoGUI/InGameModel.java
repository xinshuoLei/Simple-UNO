package unoGUI;

import unoGameLogic.GameState;

public class InGameModel {
	
	/**
	 * corresponding game state
	 */
	GameState state;
	
	/**
	 * constructor for InGmaeModel 
	 * @param numHumanPlayers number of human players in a game
	 * @param numAI number of AI in a game
	 * @param aiType type of AI in a game
	 */
	public InGameModel(int numHumanPlayers, int numAI, String aiType) {
		state = new GameState(numHumanPlayers, numAI, aiType);
		state.initializePlayerStack();
	}

	public GameState getState() {
		return state;
	}
}
