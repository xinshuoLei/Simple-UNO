package unoGameLogic;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import unoCard.Card;

/**
 * The class for strategic players
 */
public class StrategicAIPlayer extends Player {
	/**
	 * Constructor for strategic AI player
	 * @param setIndex index of the player
	 */
	public StrategicAIPlayer(int setIndex) {
		super(setIndex);
	}
	
	/**
	 * Function that picks a card to play, i.e. set cardToPlay
	 * For strategic AI, a valid card with the most popular color is picked
	 * For example, if the valid card a player can play include 
	 * a blue 0, a red 0, a blue reverse, then a blue card is played
	 */
	public void pickCard() {
		updateCardToNumber(true);
		Map<String, Integer> colorToNumber = getColorToNumber();
		// find the most popular color
		String mostPopularColor = findMostPopularColor(colorToNumber);
		if (mostPopularColor == null) {
			setCardToPlay(null);
		}
		// set cardToPlay to a card with mostPopularColor
		List<Card> validCards = getValidCards();
		for (Card oneCard : validCards) {
			String color = oneCard.getColor();
			if (color == null) {
				color = oneCard.getSymbol();
			}
			if (color.equals(mostPopularColor)) {
				setCardToPlay(oneCard);
				return;
			}
		}
	}

	private String findMostPopularColor(Map<String, Integer> colorToNumber) {
		int maxNumber = Collections.max(colorToNumber.values());
		// if maxNumber is 0, there is no valid card to play
		if (maxNumber == 0) {
			return null;
		}
		String mostPopularColor = null;
		for (Entry<String, Integer> entry : colorToNumber.entrySet()) {
	        if (entry.getValue().equals(maxNumber)) {
	            mostPopularColor = entry.getKey();
	        }
	    }
		return mostPopularColor;
	}
	
	/**
	 * Function that pick a color for the wild card played
	 * For strategic AI, the most popular color is picked
	 * For example, a player has 3 red cards, 1 yellow card,
	 * then the color red is picked
	 * @return color picked
	 */
	public void pickColorForWild() {
		updateCardToNumber(false);
		Map<String, Integer> colorToNumber = getColorToNumber();
		String mostPopularColor = findMostPopularColor(colorToNumber);
		setColorToUse(mostPopularColor);
	}
}
