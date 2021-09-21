package unoGUI;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unoCard.Card;
import unoCard.DrawTwoCard;
import unoCard.NumberCard;
import unoGameLogic.GameState;
import unoGameLogic.Player;

/**
 * Main class
 * used for GUI and main game loop
 */
public class Main {

	public static void main(String[] args){
		
		/**
		// run different GUI based on argument
		if (args[0].equals("start")) {
			
			new StartView();
			
		} else if (args[0].equals("in_game")) {
			
			GameState state = new GameState(3, 0, null);
			state.initializePlayerStack();
			
			// print out the card to match for manual test
			System.out.println("top card in the discard pile is: ");
			state.getCardToMatch().printCard();
			// formatting
			System.out.println("");
			
			// print out other state info for manual test
			int currentPlayerIndex = state.getCurrentPlayer();
			Player player = state.getAllPlayers().get(currentPlayerIndex);
			System.out.println("current player is: " + player.getName());
			List<Integer> drawPenalty = state.getDrawPenalty();
			System.out.println("draw penalty for current player is: " 
					+ drawPenalty.get(currentPlayerIndex) + "\n");
			
			// make current player draw three card to test display stack
			Player currentPlayer = state.getAllPlayers().get(currentPlayerIndex);
			for (int i = 0; i < 3; i++) {
				currentPlayer.drawCard(new ArrayList<>(state.getDrawPile()), 1, 
						false, null, null);
				// remove card drawn from draw pile
				state.getDrawPile().remove(0);
			}
			
			// print out the stack for manual test 
			System.out.println("current player's stack is: ");
			for (Card oneCard : currentPlayer.getStack()) {
				oneCard.printCard();
			}
			
			// display in game GUI
			new InGame(state);
			
		} else if (args[0].equals("end")) {
			// Create a new game state for testing purpose
			int numPlayers = 3;
			GameState state = new GameState(numPlayers, 0, null);
			state.initializePlayerStack();
			
			// pick a random player
			double randomPlayerDouble = Math.random() * numPlayers;
			int randomPlayer = (int) randomPlayerDouble;
			Player player = state.getAllPlayers().get(randomPlayer);
			System.out.println("winner is " + Player.NAME_PREFIX + randomPlayer);
			
			// empty player' stack
			player.setStack(new ArrayList<Card>());
			
			// display winner, should be the player we picked earlier
			new EndView(Player.NAME_PREFIX + state.checkWinner());
		}
		*/
		new StartControl();
		/**
		GameState state = new GameState(0,2, Player.BASELINE_AI);
		state.initializePlayerStack();
		state.setCardBeforeSpecial(new NumberCard("3", Card.BLUE));
		new InGameView(state);
		*/
	}
}
