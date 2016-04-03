package Thue.Init;

import Thue.GameLogic.AbstractFree;
import Thue.GameLogic.OverlapFree;
import Thue.GameLogic.SquareFree;
import Thue.GameConfig.ConfigRetriever;

public class Main {
	private static final String SQUARE = "Square";
	private static final String OVERLAP = "Overlap";

	public static void main (String[] args) {
		startGame();
	}

	private static void startGame() {
		AbstractFree t = getGameType();

		if(t != null) {
			t.startGame(ConfigRetriever.getGameMode());
		}
	}

	private static AbstractFree getGameType() {
		String gameType = ConfigRetriever.getGameType();
		if(gameType.equals(SQUARE)) {
			return new SquareFree(ConfigRetriever.getNestingLevels());
		}
		if(gameType.equals(OVERLAP)) {
			return new OverlapFree(ConfigRetriever.getNestingLevels());
		}

		return null;
	}
}
