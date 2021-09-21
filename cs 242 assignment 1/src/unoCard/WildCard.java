package unoCard;

/**
 * class for wild cards and wild draw four cards
 */
public class WildCard extends Card {
	
	public WildCard(String setType, String setColor) {
		// color of wild cards is null when it is not played by a player
		// If it is played by a player, then the color is the color chosen by the player
		super(setType, setColor);
	}
}
