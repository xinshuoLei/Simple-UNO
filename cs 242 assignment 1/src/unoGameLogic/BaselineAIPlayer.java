package unoGameLogic;

import java.util.Map;

import unoCard.Card;

/**
 * the class for baseline AI playes
 */
public class BaselineAIPlayer extends Player{
	public BaselineAIPlayer(int setIndex) {
		super(setIndex);
		setAiType(BASELINE_AI);
	}
	
	/**
	 * Function that picks a card to play, i.e. set cardToPlay
	 * For baseline AI, a random valid card is picked. 
	 * When there are no valid card to play, the function set cardToPlay
	 * to null.
	 */
	public void pickCard() {
		int numValidCard = getValidCards().size();
		// if numValidCard is 0, there is no valid card to play
		if (numValidCard == 0) {
			setCardToPlay(null);
			return;
		}
		// generate a random number between [0, num_validCard)
		double randomIndexDouble = Math.random() * numValidCard;
		int randomIndex = (int) randomIndexDouble;
		// set cardToPlay to the random cardPicked
		Card cardPicked = getValidCards().get(randomIndex);
		setCardToPlay(cardPicked);
	}
	
	/**
	 * Function that pick a color for the wild card played
	 * For baseline AI, a random color is picked
	 * @return color picked
	 */
	public void pickColorForWild() {
		String[] colors = {Card.YELLOW, Card.BLUE, Card.RED, Card.GREEN};
		// generate a number between [0, 4)
		double randomIndexDouble = Math.random() * colors.length;
		int randomIndex = (int) randomIndexDouble;
		setColorToUse(colors[randomIndex]);
	}
}
