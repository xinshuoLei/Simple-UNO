package unoGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import unoCard.Card;
import unoCard.DrawTwoCard;
import unoGameLogic.GameState;
import unoGameLogic.Player;

public class Main {

	public static void main(String[] args){
		
		/**
		 * A list of player names used for testing GUI
		 */
		List<String> testPlayerList1 = new ArrayList<>(Arrays.asList("Player 1", 
				"Player2", "Player3"));
		
		if (args[0].equals("start")) {
			
			new StartView();
			
		} else if (args[0].equals("in_game")) {
			
			GameState state = new GameState(new ArrayList<>(testPlayerList1));
			state.initializePlayerStack();
			
			// print out the card to match for manual test
			System.out.println("top card in the discard pile is: ");
			state.getCardToMatch().printCard();
			
			// make current player draw three card to test display stack
			int currentPlayerIndex = state.getCurrentPlayer();
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
			GameState state = new GameState(new ArrayList<>(testPlayerList1));
			state.initializePlayerStack();
			
			// pick a random player
			double randomPlayerDouble = Math.random() * testPlayerList1.size();
			int randomPlayer = (int) randomPlayerDouble;
			Player player = state.getAllPlayers().get(randomPlayer);
			System.out.println("winner is " + testPlayerList1.get(randomPlayer));
			
			// empty player' stack
			player.setStack(new ArrayList<Card>());
			
			// display winner, should be the player we picked earlier
			new EndView(testPlayerList1.get(state.checkWinner()));
		}
		
	}
}
