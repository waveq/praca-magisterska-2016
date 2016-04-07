package thue.init;

import thue.gameConfig.ConfigRetriever;
import thue.gameConfig.ResultWriter;
import thue.gameLogic.AbstractFree;
import thue.gameLogic.OverlapFree;
import thue.gameLogic.SquareFree;

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

		ResultWriter.closeStream();
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
