package unoGUI;

import java.util.List;

import unoCard.Card;
import unoGameLogic.GameState;
import unoGameLogic.Player;

public class InGameModel {
	
	/**
	 * corresponding game state
	 */
	private GameState state;
	
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
	
	/**
	 * change current player to next player
	 */
	public void nextPlayer() {
		state.incrementCurrentPlayer();
	}

	public GameState getState() {
		return state;
	}
	
	public Card getCardToMatch() {
		return state.getCardToMatch();
	}
	
	public Card getCardBeforeSpecial() {
		return state.getCardBeforeSpecial();
	}
	
	public String getCurrentPlayer() {
		int currentPlayer = state.getCurrentPlayer();
		Player player = state.getAllPlayers().get(currentPlayer);
		return player.getName();
	}
	
	public int getPenalty() {
		int currentPlayer = state.getCurrentPlayer();
		return state.getDrawPenalty().get(currentPlayer);
	}
	
	public String getAIType() {
		int currentPlayer = state.getCurrentPlayer();
		Player player = state.getAllPlayers().get(currentPlayer);
		return player.getAiType();
	}
	
	public List<Card> getStack() {
		int currentPlayer = state.getCurrentPlayer();
		Player player = state.getAllPlayers().get(currentPlayer);
		return player.getStack();
	}
	
	public int getPlayerNum() {
		return state.getAllPlayers().size();
	}
	
	
}
