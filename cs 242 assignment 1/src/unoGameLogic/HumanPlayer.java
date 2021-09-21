package unoGameLogic;

/**
 * The class for human players
 */
public class HumanPlayer extends Player {
	/**
	 * Constructor for human player
	 * @param setIndex index of the player
	 */
	public HumanPlayer(int setIndex) {
		super(setIndex);
		setAiType(NOT_AI);
	}
	
	/**
	 * Function that pick a card
	 */
	public void pickCard() {
		// just place holder
		// card selection for human player is done in inGameControl
	}
	
	/**
	 * Function that pick a color
	 */
	public void pickColorForWild() {
		// just place holder
		// color selection for human player is done in inGameControl
	}
}
