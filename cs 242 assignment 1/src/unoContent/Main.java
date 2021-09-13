package unoContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import GUI.EndView;
import GUI.InGame;
import GUI.StartView;

public class Main {

	public static void main(String[] args){
		
		/**
		 * A list of player names used for testing GUI
		 */
		List<String> testPlayerList1 = new ArrayList<>(Arrays.asList("Player 1", "Player2", "Player3"));
		
		if (args[0].equals("start")) {
			try {
				new StartView();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (args[0].equals("in_game")) {
			new InGame();
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
