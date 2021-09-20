package unoGUI;
/**
 * Model for the start view
 */
public class StartModel {
	/**
	 * number of AI players in a game
	 */
	private int numAI = 0;
	
	/**
	 * number of human players in a game
	 */
	private int numHumanPlayers = 0;
	
	/**
	 * type of AI in a game
	 */
	private String aiType = null;
	
	public int getNumAI() {
		return numAI;
	}
	
	public int getNumHumanPlayers() {
		return numHumanPlayers;
	}
	
	public String getAiType() {
		return aiType;
	}
	
	public void setNumAI(int numAI) {
		this.numAI = numAI;
	}
	
	public void setNumHumanPlayers(int numHumanPlayers) {
		this.numHumanPlayers = numHumanPlayers;
	}
	
	public void setAiType(String aiType) {
		this.aiType = aiType;
	}
}
