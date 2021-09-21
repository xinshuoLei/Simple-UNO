package unoGUI;

import java.util.List;

import unoCard.Card;
import unoGameLogic.GameState;
import unoGameLogic.Player;

public class InGameModel {
	
	/**
	 * enum for status of the card played
	 * SUCCESS: card is successfully played
	 * SKIP: current turn of player is skipped due to penalty
	 * FAIL: card played is invalid
	 */
	public enum playStatus {
		SUCCESS,
		SKIP,
		FAIL
	}
	
	/**
	 * Corresponding game state
	 */
	private GameState state;
	
	/**
	 * The index of the selected card in player's stack
	 * used for human player
	 */
	private int cardSelectionIndex;
	
	/**
	 * Card selected by human player
	 */
	private Card cardSelected;
	
	
	/**
	 * The selected color for wild card
	 * used for human player
	 */
	private String colorSelection;

	
	/**
	 * Constructor for InGmaeModel 
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
	
	/**
	 * Play a card selected by a human player 
	 * @return a playStatus
	 */
	public playStatus playCardHuman() {
		int drawPenalty = getPenalty();
		Card selection = getStack().get(cardSelectionIndex);
		boolean processStatus = state.processCardPlayed(selection);
		if (!processStatus && drawPenalty != 0) {
			// if there is a draw penalty and card played cannot be processed
			// the player's turn is skipped
			return playStatus.SKIP;
		} else if (!processStatus) {
			return playStatus.FAIL;
		}
		return playStatus.SUCCESS;
	}
	
	/**
	 * Function that check if the game has a winner
	 * @return name of the winner. null if there is no winner yet
	 */
	public String checkWinner() {
		List<Player> allPlayers = state.getAllPlayers();
		int winnerIndex = state.checkWinner();
		// if there is no winner yet, return null
		if (winnerIndex == -1) {
			return null;
		}
		return allPlayers.get(winnerIndex).getName();
	}
	
	/**
	 * Deal with AI player
	 * Let AI select a card and color
	 * @return the card AI played
	 */
	public String aiPlayerTurn() {
		int currentPlayer = state.getCurrentPlayer();
		Player player = state.getAllPlayers().get(currentPlayer);
		player.updateValidCard(getCardToMatch(), getCardBeforeSpecial());
		player.pickCard();
		player.pickColorForWild();
		cardSelected = player.attemptPlayCard();
		state.processCardPlayed(cardSelected);
		if (cardSelected != null) {
			return cardSelected.getCardInfo();
		}
		return null;
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

	public int getCardSelection() {
		return cardSelectionIndex;
	}

	public String getColorSelection() {
		return colorSelection;
	}

	public void setCardSelection(int cardSelection) {
		this.cardSelectionIndex = cardSelection;
		cardSelected = getStack().get(cardSelectionIndex);
	}

	public void setColorSelection(String colorSelection) {
		this.colorSelection = colorSelection;
		int currentPlayer = state.getCurrentPlayer();
		Player player = state.getAllPlayers().get(currentPlayer);
		player.setColorToUse(colorSelection);
	}

	public Card getCardSelected() {
		return cardSelected;
	}
	
	
}
